\set user `echo "$POSTGRES_USER"`


CREATE DATABASE updpay;
GRANT ALL PRIVILEGES ON DATABASE updpay TO :user;