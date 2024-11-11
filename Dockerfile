FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . /app
# RUN ls -la /app

RUN apt-get install maven -y
RUN cd app && \
    mvn clean install

RUN ls -la /app/target

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build ./app/target/amparo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]