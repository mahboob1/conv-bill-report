// The library version is controled from the Jenkins configuration
// To force a version add after lib '@' followed by the version.
@Library(value = 'msaas-shared-lib', changelog = false) _

node {
    // setup the global static configuration
    config = setupMsaasPipeline('msaas-config.yaml')
}

pipeline {

    agent {
        kubernetes {
            label "${config.pod_label}"
            yamlFile 'KubernetesPods.yaml'
        }
    }

    post {
        always {
            sendMetrics(config)
        }
        fixed {
            emailext(
                subject: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' ${currentBuild.result}", 
                body: """
                        Job Status for '${env.JOB_NAME} [${env.BUILD_NUMBER}]': ${currentBuild.result}\n\nCheck console output at ${env.BUILD_URL}
                """, 
                to: 'some_email@intuit.com'
            )
        }
        unsuccessful {
            emailext(
                subject: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' ${currentBuild.result}", 
                body: """
                        Job Status for '${env.JOB_NAME} [${env.BUILD_NUMBER}]': ${currentBuild.result}\n\nCheck console output at ${env.BUILD_URL}
                """, 
                to: 'some_email@intuit.com'
            )
        }
    }

    stages {
        stage('BUILD:') {
            when {anyOf {branch 'master'; changeRequest()}}
            stages {
                stage('Docker Multi Stage Build') {
                    steps {
                        container('podman') {
                            script {
                                cocoonInitUsingPodman(config)
                                podmanBuild("--build-arg=NEXUS_PROXY=${env.NEXUS_PROXY_URL} --rm=false -t ${config.image_full_name} .")
                                String image = podmanFindImage([image: 'build', build: env.BUILD_URL])
                                podmanMount(image, {steps,mount -> 
                                        steps.sh(label: 'copy outputs to workspace', script: "cp -r ${mount}/usr/src/app/target ${env.WORKSPACE}/target")
                                        steps.sh(label: 'cocoon copy m2 repo to shared', script: "cp -r ${mount}/usr/src/app/.m2/repository /var/run/shared-m2")
                                    })
                                podmanSave(config.image_full_name, 'image')
                            }
                        }
                    }
                }
                stage('Code Scans') {
                    parallel {
                        stage('Code Analysis') {
                            when {expression {return config.SonarQubeAnalysis}}
                            steps {
                                container('podman') {
                                    script {
                                        String image = podmanFindImage([image: 'build', build: env.BUILD_URL])
                                        podmanMount(image, {steps,mount -> 
                                                steps.sh(label: 'copy bundle to workspace', script: "cp -R ${mount}/usr/src \${WORKSPACE}/bundle")
                                            })
                                        podmanBuild("-f Dockerfile.sonar --build-arg=\"sonar=${config.SonarQubeEnforce} --build-arg=\"NEXUS_PROXY=${env.NEXUS_PROXY_URL}\" .\"")
                                    }
                                }
                            }
                        }
                        stage('checkmarx') {
                            steps {
                                checkmarx(config)
                            }
                        }
                        stage('Nexus IQ Server Scan') {
                            steps {
                                nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${config.asset_id}", iqStage: 'build'
                            }
                        }
                    }
                }
                stage('Publish') {
                    post {
                        always {
                            sendBuildMetrics(config)
                        }
                    }
                    parallel {
                        stage('Cocoon Publish') {
                            when {expression {return config.enableCocoon}}
                            steps {
                                container('podman') {
                                    cocoonPushUsingPodman(config)
                                }
                            }
                        }
                        stage('Report Coverage & Unit Test Results') {
                            steps {
                                junit '**/surefire-reports/**/*.xml'
                                jacoco()
                                codeCov(config)
                            }
                        }
                        stage('CPD2 Certification & Publish') {
                            steps {
                                container('cpd2') {
                                    intuitCPD2(config, "-i ${config.image_full_name} --buildfile Dockerfile --loadimage image.tar.gz")
                                }
                                sh(label: 'delete container export', script: 'rm -rf image.tar.gz')
                            }
                        }
                    }
                }
                // jira transitioning
                stage('Transition Jira Tickets') {
                    steps {
                        script {
                            if (env.BRANCH_NAME != 'master' && changeRequest()) {
                                transitionJiraTickets(config, 'Ready for Review')
                            } else if (env.BRANCH_NAME == 'master') {
                                transitionJiraTickets(config, 'Closed')
                            }
                        }
                    }
                }
            }
        }
        stage('PR:') {
            when {changeRequest()}
            post {
                success {
                    transitionJiraTickets(config, 'Ready for Review')
                }
            }
            stages {
                stage('Component Test Setup') {
                    parallel {
                        stage('Mock downstream setup') {
                            steps {
                                container('karate') {
                                    //set up mock server for the dependencies
                                    sh label: 'Set Up Mock Dependencies', script: 'java -jar /opt/karate/karate.jar -m app/src/test/java/api-mock.feature -p 9000 &'
                                }
                            }
                        }
                        stage('App Server check') {
                            steps {
                                sh label: 'Check Application Server', script: 'curl https://localhost/health/full -k -4 --retry 100 --retry-delay 20 --retry-connrefused'
                            }
                        }
                        stage('Mock Server check') {
                            steps {
                                sh label: 'Check Mock Server', script: 'curl http://localhost:9000 -k -4 --retry 100 --retry-delay 20 --retry-connrefused'
                            }
                        }
                        stage('App Server Startup') {
                            steps {
                                container('test') {
                                    sh label: 'Start Reference App Server', script: 'java -jar -Dspring.profiles.active=local -Dserver.port=443 ${WORKSPACE}/target/conv-bill-hist-app.jar &'
                                }
                            }
                        }
                    }
                }
                stage('Component Functional/Perf Tests') {
                    parallel {
                        stage('Functional Tests') {
                            post {
                                success {
                                    githubNotify context: 'Component Functional Tests', credentialsId: 'github-svc-sbseg-ci', gitApiUrl: 'https://github.intuit.com/api/v3/', description: 'Tests Passed', status: 'SUCCESS', targetUrl: env.JOB_URL + '/' + env.BUILDNUMBER + '/Component_20Functional_20Test_20Results'
                                }
                                failure {
                                    githubNotify context: 'Component Functional Tests', credentialsId: 'github-svc-sbseg-ci', gitApiUrl: 'https://github.intuit.com/api/v3/', description: 'Tests are failing', status: 'FAILURE', targetUrl: env.RUN_DISPLAY_URL
                                }
                            }
                            steps {
                                script {
                                    try {
                                        container('test') {
                                            sh label: 'Run Component Tests', script: 'mvn -s settings.xml -f app/pom.xml test -Dmaven.repo.local=/var/run/shared-m2/repository -Pkarate -Dkarate.env=mock -Dkarate.mock.port=9000 -Dkarate.server.port=443'
                                        }
                                    }finally {
                                        publishHTML target: [
                                                allowMissing: false, 
                                                alwaysLinkToLastBuild: false, 
                                                keepAll: true, 
                                                reportDir: 'app/target/cucumber-html-reports', 
                                                reportFiles: 'overview-features.html', 
                                                reportName: 'Component Functional Test Results']
                                    
                                    }
                                }
                            }
                        }
                        stage('Perf Tests') {
                            post {
                                success {
                                    githubNotify context: 'Component Perf Tests', credentialsId: 'github-svc-sbseg-ci', gitApiUrl: 'https://github.intuit.com/api/v3/', description: 'Tests Passed', status: 'SUCCESS', targetUrl: env.JOB_URL + '/' + env.BUILDNUMBER + '/Component_20Perf_20Test_20Results'
                                }
                                failure {
                                    githubNotify context: 'Component Perf Tests', credentialsId: 'github-svc-sbseg-ci', gitApiUrl: 'https://github.intuit.com/api/v3/', description: 'Tests are failing', status: 'FAILURE', targetUrl: env.RUN_DISPLAY_URL
                                }
                            }
                            steps {
                                script {
                                    try {
                                        container('test') {
                                            sh label: 'Run Perf Tests', script: 'mvn -s settings.xml -f app/pom.xml gatling:test -Dmaven.repo.local=/var/run/shared-m2/repository -Pperf -Dkarate.env=mock -Dkarate.mock.port=9000 -Dkarate.server.port=443'
                                        }
                                    }finally {
                                        publishHTML target: [
                                                allowMissing: false, 
                                                alwaysLinkToLastBuild: false, 
                                                keepAll: true, 
                                                reportDir: 'app/target/gatling', 
                                                reportFiles: '**/index.html', 
                                                reportName: 'Component Perf Test Results']
                                    
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        stage('qal-usw2-eks') {
            when {
                beforeOptions true
                allOf {branch 'master'; not {changeRequest()}}
            }
            post {
                always {
                    sendDeployMetrics(config, [boxsetVersion: "${config.image_full_name}", envName: 'qal-usw2-eks'])
                }
            }
            options {
                lock(resource: getEnv(config, 'qal-usw2-eks').namespace, inversePrecedence: true)
                timeout(time: 22, unit: 'MINUTES')
            }
            stages {
                stage('Scorecard Check') {
                    when {expression {return config.enableScorecardReadinessCheck}}
                    steps {
                        scorecardPreprodReadiness(config, 'qal-usw2-eks')
                    }
                }
                stage('Deploy') {
                    steps {
                        container('cdtools') {
                            // This has to be the first action in the first sub-stage
                            milestone(ordinal: 10, label: 'Deploy-qal-usw2-eks-milestone')
                            gitOpsDeploy(config, 'qal-usw2-eks', config.image_full_name)
                        }
                    }
                }
//                 stage('Test') {
//                     steps {
//                         retry(5) {
//                             script {
//                                 try {
//                                     container('test') {
//                                         sh label: 'Run Karate Tests', script: "mvn -s settings.xml -f app/pom.xml verify -Dmaven.repo.local=/var/run/shared-m2/repository -Pkarate-remote -Dkarate.env=qal-usw2-eks -DkubernetesServiceName=${config.kubernetesServiceName} -DjacocoServiceEndpoint=${config['environments']['qal-usw2-eks']['jacoco_endpoint']}"
//                                     }
//                                 }finally {
//                                     publishHTML target: [
//                                             allowMissing: true,
//                                             alwaysLinkToLastBuild: false,
//                                             keepAll: true,
//                                             reportDir: 'app/target/cucumber-html-reports',
//                                             reportFiles: 'overview-features.html',
//                                             reportName: 'Integration test results']
//
//                                     publishHTML target: [
//                                             allowMissing: true,
//                                             alwaysLinkToLastBuild: false,
//                                             keepAll: true,
//                                             reportDir: 'app/target/test-results/coverage/jacoco/',
//                                             reportFiles: 'index.html',
//                                             reportName: 'Integration test coverage']
//
//                                 }
//                             }
//                         }
//                     }
//                 }
//                 stage('Transition Jira Tickets') {
//                     when {expression {return config.enableJiraTransition}}
//                     steps {
//                         transitionJiraTickets(config, 'Deployed to PreProd')
//                     }
//                 }
            }
        }
        stage('e2e-usw2-eks') {
            when {
                beforeOptions true
                allOf {branch 'master'; not {changeRequest()}}
            }
            post {
                always {
                    sendDeployMetrics(config, [boxsetVersion: "${config.image_full_name}", envName: 'e2e-usw2-eks'])
                }
            }
            options {
                lock(resource: getEnv(config, 'e2e-usw2-eks').namespace, inversePrecedence: true)
                timeout(time: 22, unit: 'MINUTES')
            }
            stages {
                stage('Scorecard Check') {
                    when {expression {return config.enableScorecardReadinessCheck}}
                    steps {
                        scorecardPreprodReadiness(config, 'e2e-usw2-eks')
                    }
                }
                stage('Deploy') {
                    steps {
                        container('cdtools') {
                            // This has to be the first action in the first sub-stage
                            milestone(ordinal: 20, label: 'Deploy-e2e-usw2-eks-milestone')
                            gitOpsDeploy(config, 'e2e-usw2-eks', config.image_full_name)
                        }
                    }
                }
//                 stage('Test') {
//                     steps {
//                         script {
//                             try {
//                                 container('test') {
//                                     sh label: 'Run Karate Tests', script: "mvn -s settings.xml -f app/pom.xml verify -Dmaven.repo.local=/var/run/shared-m2/repository -Pkarate -Dkarate.env=${getEnvName(config, 'e2e-usw2-eks')}"
//                                 }
//                             }finally {
//                                 publishHTML target: [
//                                         allowMissing: true,
//                                         alwaysLinkToLastBuild: false,
//                                         keepAll: true,
//                                         reportDir: 'app/target/cucumber-html-reports',
//                                         reportFiles: 'overview-features.html',
//                                         reportName: 'Integration test results']
//
//                             }
//                         }
//                     }
//                 }
//                 stage('Transition Jira Tickets') {
//                     when {expression {return config.enableJiraTransition}}
//                     steps {
//                         transitionJiraTickets(config, 'Deployed to PreProd')
//                     }
//                 }
            }
        }
        stage('Goto-Stage Approval') {
            when {
                allOf {
                    branch 'master'
                    not {changeRequest()}
                    not {expression {return config.preprodOnly}}
                }
            }
            options {
                timeout(time: 1, unit: 'DAYS')
            }
            steps {
                container('jnlp') {
                    scorecardPreprodReadiness(config, 'stg-usw2-eks')
                }
                // Milestone is here, cause no milestone allowed in parallel
                milestone(ordinal: 30, label: 'Deploy-stg-usw2-eks-milestone')
            }
        }
        stage('stg-usw2-eks') {
            when {
                beforeOptions true
                allOf {
                    branch 'master'
                    not {changeRequest()}
                    not {expression {return config.preprodOnly}}
                }
            }
            post {
                always {
                    sendDeployMetrics(config, [boxsetVersion: "${config.image_full_name}", envName: 'stg-usw2-eks'])
                }
            }
            options {
                lock(resource: getEnv(config, 'stg-usw2-eks').namespace, inversePrecedence: true)
                timeout(time: 22, unit: 'MINUTES')
            }
            stages {
                stage('Deploy') {
                    steps {
                        container('cdtools') {
                            //This has to be the first action in the first sub-stage.
                            gitOpsDeploy(config, 'stg-usw2-eks', config.image_full_name)
                        }
                    }
                }
//                 stage('Test') {
//                     steps {
//                         retry(5) {
//                             script {
//                                 try {
//                                     container('test') {
//                                         sh label: 'Run Karate Tests', script: "mvn -s settings.xml -f app/pom.xml verify -Dmaven.repo.local=/var/run/shared-m2/repository -Pkarate -Dkarate.env=${getEnvName(config, 'stg-usw2-eks')}"
//                                     }
//                                 }finally {
//                                     publishHTML target: [
//                                             allowMissing: true,
//                                             alwaysLinkToLastBuild: false,
//                                             keepAll: true,
//                                             reportDir: 'app/target/cucumber-html-reports',
//                                             reportFiles: 'overview-features.html',
//                                             reportName: 'Integration test results']
//
//                                 }
//                             }
//                         }
//                     }
//                 }
                stage('Scorecard Check') {
                    when {expression {return config.enableScorecardReadinessCheck}}
                    steps {
                        scorecardProdReadiness(config, 'stg-usw2-eks')
                    }
                }
            }
        }
        stage('Go Live in Prod Approval') {
            when {
                allOf {
                    branch 'master'
                    not {changeRequest()}
                    not {expression {return config.preprodOnly}}
                }
            }
            agent none
            options {
                timeout(time: 1, unit: 'DAYS')
            }
            stages {
                stage('Scorecard Check') {
                    steps {
                        scorecardProdReadiness(config, getEnvName(config, 'primary'))
                    }
                }
            }
        }
        stage('PRD primary') {
            when {
                beforeOptions true
                allOf {
                    branch 'master'
                    not {changeRequest()}
                    not {expression {return config.preprodOnly}}
                }
            }
            post {
                always {
                    sendDeployMetrics(config, [boxsetVersion: "${config.image_full_name}", envName: getEnvName(config, 'primary')])
                }
            }
            options {
                lock(resource: getEnv(config, getEnvName(config, 'primary')).namespace, inversePrecedence: true)
                timeout(time: 22, unit: 'MINUTES')
            }
            stages {
                stage('Create CR') {
                    steps {
                        container('servicenow') {
                            sh label: 'Opens CR', script: 'exit 0'
                            openSnowCR(config, getEnvName(config, 'primary'), config.image_full_name)
                        }
                    }
                }
                stage('Deploy') {
                    steps {
                        container('cdtools') {
                            // This has to be the first action in the first sub-stage.
                            gitOpsDeploy(config, getEnvName(config, 'primary'), config.image_full_name)
                        }
                    }
                }
//                 stage('Test') {
//                     steps {
//                         retry(5) {
//                             script {
//                                 try {
//                                     container('test') {
//                                         sh label: 'Run Karate Tests', script: "mvn -s settings.xml -f app/pom.xml verify -Dmaven.repo.local=/var/run/shared-m2/repository -Pkarate -Dkarate.env=${getEnvName(config, 'primary')}"
//                                     }
//                                 }finally {
//                                     publishHTML target: [
//                                             allowMissing: true,
//                                             alwaysLinkToLastBuild: false,
//                                             keepAll: true,
//                                             reportDir: 'app/target/cucumber-html-reports',
//                                             reportFiles: 'overview-features.html',
//                                             reportName: 'Integration test results']
//
//                                 }
//                             }
//                         }
//                     }
//                 }
                // If any failure, CR remains open and MUST be closed manually with cause.
                stage('Close CR') {
                    steps {
                        container('servicenow') {
                            sh label: 'Close CR', script: 'exit 0'
                            closeSnowCR(config, getEnvName(config, 'primary'))
                        }
                    }
                }
                // jira transitioning
                stage('Transition Jira Tickets') {
                    when {expression {return config.enableJiraTransition}}
                    steps {
                        transitionJiraTickets(config, 'Released')
                    }
                }
            }
        }
    }
}
