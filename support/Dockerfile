FROM  adoptopenjdk/openjdk11:jdk-11.0.9.1_1-alpine
ARG JAR_FILE=./target/support*.jar

EXPOSE 8087

COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-Dserver.port=8087", "-jar", "/home/application.jar"]
