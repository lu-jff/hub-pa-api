FROM openjdk:8-alpine
ARG JAR_FILE=./target/microservice*.jar

EXPOSE 8081

COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-Dserver.port=8081", "-jar", "/home/application.jar"]