FROM  adoptopenjdk/openjdk11:jdk-11.0.9.1_1-alpine
ARG JAR_FILE=./target/upload-payments*.jar

EXPOSE 8084

COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-Dserver.port=8084", "-jar", "/home/application.jar"]