\set user `echo "$POSTGRES_USER"`


CREATE DATABASE suppor;
GRANT ALL PRIVILEGES ON DATABASE suppor TO :user;