FROM openjdk:8-alpine
ARG JAR_FILE=./target/ente-creditore*.jar

EXPOSE 8082

COPY ${JAR_FILE} /home/application.jar
ENV SSH_PASSWD "root:Docker!"
RUN apk --no-cache add openssh openrc
COPY ssh_config /etc/ssh/
RUN rc-update add sshd
RUN openrc
RUN touch /run/openrc/softlevel
RUN /etc/init.d/sshd start

ENTRYPOINT ["java", "-Dserver.port=8082", "-jar", "/home/application.jar"]
