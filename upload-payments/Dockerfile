FROM openjdk:8-alpine
ARG JAR_FILE=./target/upload-payments*.jar

EXPOSE 8084

COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-Dserver.port=8084", "-jar", "/home/application.jar"]