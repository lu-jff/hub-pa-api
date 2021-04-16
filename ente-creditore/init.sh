#!/bin/bash
set -e

echo "Starting SSH ..."
service ssh start

java -Dserver.port=80 -jar /home/application.jar