#!/bin/sh
mkdir -p /run/openrc && touch /run/openrc/softlevel
rc-update add sshd
openrc
java -Dserver.port=80 -jar /home/application.jar