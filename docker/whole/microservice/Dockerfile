FROM openjdk:8-alpine
ARG JAR_FILE=hub-pa-api*.jar
COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-jar", "/home/application.jar"]