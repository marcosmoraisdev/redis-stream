FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
LABEL APPLICATION="poc"

ENV APP_NAME poc-app
ENV APP_HOME /opt/poc/$APP_NAME
ENV JAVA_OPTS ""

WORKDIR $APP_HOME
RUN rm -r $APP_HOME
COPY target/*.jar ${APP_HOME}/${APP_NAME}.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dapp=${APP_NAME} -Xloggc:${APP_HOME}/verbose_gc.log -jar ${APP_NAME}.jar"]