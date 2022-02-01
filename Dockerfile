FROM adoptopenjdk/openjdk11:jdk-11.0.12_7-alpine-slim

RUN apk update
RUN apk upgrade

COPY ./build/libs/ipaas-log-app-0.0.1-SNAPSHOT.jar ipaas-log-app.jar

ENTRYPOINT ["java","-jar","/ipaas-log-app.jar"]