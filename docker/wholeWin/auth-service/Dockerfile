FROM openjdk:8-alpine
ARG JAR_FILE=auth-service*.jar
COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-jar", "/home/application.jar"]