#!/bin/bash

cd ..
cd payments && cp .env.sample .env && cd ..
cd service-managment && cp .env.sample .env && cd ..

mvn clean install
docker-compose -f docker-compose-payments.yml up --build