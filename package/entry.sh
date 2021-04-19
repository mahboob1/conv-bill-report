#!/bin/sh

if [ -f /etc/secrets/application.properties ]; then
  JAVA_OPTS="${JAVA_OPTS} -Dspring.config.location=/etc/secrets/application.properties"
fi

if [ -n "${APP_ENV}" ]; then
  JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=${APP_ENV}"
fi

# Java parameters for java8u192+
JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions \
  -XX:+UseG1GC \
  -XX:+UseStringDeduplication \
  -XX:+UseCGroupMemoryLimitForHeap \
  -XX:MinRAMPercentage=50.0 \
  -XX:MaxRAMPercentage=80.0 \
  -XshowSettings:vm"

# use app dir for tmp dir
JAVA_OPTS="${JAVA_OPTS} -Djava.io.tmpdir=/app/tmp"

# Is contrast enabled, yes or no
contrastassess_enabled=yes

# ENV for contrast assessment
contrastassess_env=qal
contrastassess_jar="/app/contrast/javaagent/contrast.jar"
if [ "${contrastassess_enabled}" = "yes" ] && [ "${APP_ENV}" = "${contrastassess_env}" ]; then
  JAVA_OPTS="${JAVA_OPTS} -javaagent:${contrastassess_jar}"
  JAVA_OPTS="${JAVA_OPTS} -Dcontrast.dir=/app/contrast/javaagent"
  JAVA_OPTS="${JAVA_OPTS} -Dcontrast.application.code=8073845824510750151"
  JAVA_OPTS="${JAVA_OPTS} -Dcontrast.standalone.appname=Intuit.billingcomm.billing.qbdtconvbillhist"
  JAVA_OPTS="${JAVA_OPTS} -Dcontrast.application.name=Intuit.billingcomm.billing.qbdtconvbillhist"
  JAVA_OPTS="${JAVA_OPTS} -Dcontrast.inspect.allclasses=false -Dcontrast.process.codesources=false"
  JAVA_OPTS="${JAVA_OPTS} -Dcontrast.inventory.libraries=false"
fi

jacocoagent_enabled=yes

# ENV for contrast assessment
jacocoagent_env=qal
jacocoagent_jar="/app/jacoco-agent/jacoco-agent-runtime.jar"
if [ "${jacocoagent_enabled}" = "yes" ] && [ "${APP_ENV}" = "${jacocoagent_env}" ]; then
  JAVA_OPTS="$JAVA_OPTS -javaagent:${jacocoagent_jar}=output=tcpserver,port=6300,address=*,inclnolocationclasses=true,dumponexit=false"
fi

# Is appdynamics enabled, yes or no
appdynamics_enabled=no

appdynamics_jar="/app/appdynamics/javaagent.jar"
if [ "${appdynamics_enabled}" = "yes" ] && [ -r ${appdynamics_jar} ] && [ -f /etc/secrets/appd-account-access-key ]; then
    export APPDYNAMICS_CONTROLLER_PORT=443
    export APPDYNAMICS_CONTROLLER_SSL_ENABLED=true

    export APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY=`cat /etc/secrets/appd-account-access-key`

    JAVA_OPTS="$JAVA_OPTS -javaagent:${appdynamics_jar}"
    JAVA_OPTS="$JAVA_OPTS -Dappdynamics.agent.applicationName=${L1}-${L2}-${APP_NAME}-${APP_ENV}"
    JAVA_OPTS="$JAVA_OPTS -Dappdynamics.agent.tierName=${APPDYNAMICS_AGENT_TIER_NAME}"
    JAVA_OPTS="$JAVA_OPTS -Dappdynamics.agent.nodeName=${APPDYNAMICS_AGENT_TIER_NAME}_${HOSTNAME}"
fi

#When sidecar is injected, wait for sidecar to come up
if [[ "$MESH_ENABLED" == "true" ]]; then
until (echo >/dev/tcp/localhost/$MESH_SIDECAR_PORT) &>/dev/null ; do echo Waiting for Sidecar; sleep 3 ; done ; echo Sidecar available;
fi

exec java $JAVA_OPTS -jar /app/conv-bill-hist-app.jar