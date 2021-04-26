FROM  adoptopenjdk/openjdk11:jdk-11.0.9.1_1-alpine
ARG JAR_FILE=./target/qworker*.jar

EXPOSE 8086

COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-Dserver.port=8086", "-jar", "/home/application.jar"]