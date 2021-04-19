FROM docker.artifactory.a.intuit.com/billingcomm-orderprocess/conv-bill-hist/service/conv-bill-hist:cocoon AS cocoon
FROM docker.intuit.com/oicp/standard/maven/amzn-maven-corretto8:2.0.0 AS build
COPY --from=cocoon /root/.m2 /root/.m2

# USER root needed for build, since CPD does not allow root user for gold images.
# intermediate containers will be discarded at final stage, runtime image will be executed with non root user
USER root

# The following ARG and 2 LABEL are used by Jenkinsfile command
# to identify this intermediate container, for extraction of
# code coverage and other reported values.
ARG build
ARG NEXUS_PROXY=http://nexus.intuit.com/nexus
LABEL build=${build}
LABEL image=build
ARG MVN_SETTINGS=settings.xml
COPY package /usr/src/package
COPY project.properties /usr/src/project.properties
COPY app/pom.xml /usr/src/app/pom.xml
COPY pom.xml /usr/src/pom.xml
COPY ${MVN_SETTINGS} /usr/src/settings.xml
COPY app /usr/src/app
ENV NEXUS_PROXY_URL=${NEXUS_PROXY}
RUN mvn -f /usr/src/pom.xml -s /usr/src/settings.xml clean install
# chmod in build layer before copy to final to avoid extra layer(s) in final image
RUN chmod 644 /usr/src/app/target/conv-bill-hist-app.jar /usr/src/package/target/build.params.json /usr/src/package/entry.sh

RUN cp -r /root/.m2 /usr/src/app/.m2
RUN chmod -R 755 /usr/src/app/.m2
RUN chown appuser:appuser /usr/src/app/.m2 -R 

FROM docker.intuit.com/oicp/standard/corretto-8.x/amzn2-corretto:8
ARG DOCKER_TAGS=latest
# ARG JIRA_PROJECT=https://jira.intuit.com/projects/<CHANGE_ME>
ARG DOCKER_IMAGE_NAME=docker.artifactory.a.intuit.com/billingcomm-orderprocess/conv-bill-hist/service/conv-bill-hist:${DOCKER_TAGS}
ARG SERVICE_LINK=https://devportal.intuit.com/app/dp/resource/8073845824510750151

LABEL maintainer=firstname_lastname@intuit.com \
      app=conv-bill-hist \
      app-scope=runtime \
      build=${build}

# Switch to root for installation and some other operations
USER root
RUN install -d -m 0755 -o appuser -g appuser /app/tmp

COPY --from=build --chown=appuser:appuser /usr/src/app/target/conv-bill-hist-app.jar /usr/src/package/entry.sh /app/
COPY --from=build --chown=appuser:appuser /usr/src/package/target/build.params.json /build.params.json

RUN curl -o /app/contrast/javaagent/contrast.jar https://artifact.intuit.com/artifactory/generic-local/dev/security/ssdlc/contrast/java/latest/contrast.jar \
    && curl -o /app/jacoco-agent/jacoco-agent-runtime.jar --create-dirs https://artifact.intuit.com/artifactory/generic-local/dev/patterns/ap/team/jacoco-agent-runtime-0.8.2.jar \
    && chown -R appuser:appuser /app/contrast /app/jacoco-agent

# Remove unnecessary tools
RUN ["/home/appuser/post_harden.sh"]

USER appuser
CMD ["/bin/sh", "/app/entry.sh"]
