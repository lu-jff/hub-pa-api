#!/bin/bash

curl -X POST -H "Content-Type: application/json" -d @service.json http://localhost:8083/service-management/service
curl -X POST -H "Content-Type: application/json" -d @payment.json http://localhost:8085/payments/create

