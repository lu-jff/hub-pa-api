#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE hubpa;
    GRANT ALL PRIVILEGES ON DATABASE hubpa TO "$POSTGRES_USER";
    create table if not exists product
    (
      id  bigint not null constraint product_pkey primary key,
      name  varchar(255) UNIQUE
    );
    CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 1;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE entec;
    GRANT ALL PRIVILEGES ON DATABASE entec TO "$POSTGRES_USER";
EOSQL