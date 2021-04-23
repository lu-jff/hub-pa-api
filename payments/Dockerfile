FROM openjdk:8-alpine
ARG JAR_FILE=./target/payments*.jar

EXPOSE 8085

COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-Dserver.port=8085", "-jar", "/home/application.jar"]