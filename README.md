# HUB PA API

# Stack
* JAVA 8
* Spring Boot
* Spring Web
* Hibernate
* JPA
* Typescript / Express

# How to start the dev environment

The dev environment is based on Docker, so please install it on your machine 
following [this instuctions](https://docs.docker.com/get-docker/) before 
proceeding further.


## Start the dev environment

From the project root

```
> docker-compose up --build
```

To shutdown press `CTRL+C` then the following command

```
> docker-compose down --remove-orphans
```

When all the containers are up and running, the microservices will responde on 
URLs having the following pattern

http://localhost:8000/microservice/path/query-string

As authentication they require the `Authenticaiton` header to be set to the 
value `Bearer sessionToken`, where `sessioToken` is obtained after a 
successfull SPID authentication.

## Test SPID

Point the browser to 

```
http://localhost:8000/auth/login?entityID=xx_testenv2&authLevel=SpidL2
```

and use one of the identities in `auth-spid/conf-testenv/users.json`.

Auth workflow should end with the browser receiving a `sessionToken` in the body.

## Test endpoints

The following `POST`

```
> curl -d '{"firstName": "value", "lastName": "anothervalue"}' \
       -H 'Content-Type: application/json' \
       -H "Authorization: Bearer sessionToken" \
       -X POST \
       http://localhost:8000/microservice/customers
```

should write a customer in the DB and return a `json` like this

```
{"id":666,"firstName":"value","lastName":"anothervalue"}
```

The following `GET`

```
> curl -H "Authorization: Bearer sessionToken" \
       -X GET \
       http://localhost:8080/microservice/customers
```

should return the previous customer in a pageable structure.