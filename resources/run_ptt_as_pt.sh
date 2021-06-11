#!/bin/bash

cd payments && cp .env.sample .env && cd ..
cd service-managment && cp .env.sample .env && cd ..

mvn clean package
docker-compose -f docker-compose-payments.yml up --build