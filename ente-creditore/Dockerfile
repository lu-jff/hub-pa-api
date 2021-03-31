FROM openjdk:8-alpine
ARG JAR_FILE=./target/ente-creditore*.jar

EXPOSE 8082

COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-Dserver.port=8082", "-jar", "/home/application.jar"]