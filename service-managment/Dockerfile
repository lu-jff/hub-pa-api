FROM  adoptopenjdk/openjdk11:jdk-11.0.9.1_1-alpine
ARG JAR_FILE=./target/service-manag*.jar

EXPOSE 8083

COPY ${JAR_FILE} /home/application.jar
ENTRYPOINT ["java", "-Dserver.port=8083", "-jar", "/home/application.jar"]