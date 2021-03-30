# HUB PA API

# Stack
* JAVA 8
* Spring Boot
* Spring Web
* Hibernate
* JPA

# How to start the dev environment

The dev environment is based on Docker, so please install it on your machine 
following [this instuctions](https://docs.docker.com/get-docker/) before 
proceeding further.

## Use docker only for the DBMS

One way to start the dev environment is to run the PostgreSQL DBMS in a docker 
container, then run the app/microservice as usual.

### Start the DBMS in a docker container

From the root dir of the project

```
> cd docker/db
> docker build -t postgres .
> docker run --name postgres -dp 5432:5432 --env-file env postgres
```

### Run the Spring App ad usual

From the root dir of the project

```
> cd microservice
> ./mvnw spring-boot:run
```

## Use docker for the whole system

```
> docker-compose up --build
```

To shutdown press `CTRL+C` then the following command

```
> docker-compose down
```

## Test SPID

Point the browser to 

```
http://localhost:3000/login?entityID=xx_testenv2&authLevel=SpidL2
```

and use one of the identities in `auth-spid/conf-testenv/users.json`.

Auth workflow should end with the browser receiving a `sessionToken` in the body.

## Test the endpoint

The following `POST`

```
> curl -d '{"firstName": "value", "lastName": "anothervalue"}' -H 'Content-Type: application/json' -X POST http://localhost:8080/customers
```

should write a customer in the DB and return a `json` like this

```
{"id":666,"firstName":"value","lastName":"anothervalue"}
```

The following `GET`

```
> curl -X GET http://localhost:8080/customers
```

should return the previous customer in a pageable structure.