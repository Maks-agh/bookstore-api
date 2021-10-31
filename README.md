# Bookstore-api
Application for bookstore, based on Spring Boot, PostgreSQL, Java 11, Maven.

## Lombok
This application uses Lombok (https://projectlombok.org/), for proper usage you should enable annotation processing in IntelliJ settings. 

## PostgreSQL
This application uses PostgreSQL as database with default datasource provided. If needed, you can change it in app/docker configuration.

## Documentation
Documentation is available under `{host}/swagger-ui.html`, e.g. `http://localhost:8080/swagger-ui.html`.

## Postman collection
This repo contains Postman collection in file: `Bookstore.postman_collection.json`, you can import it to use requests

## Running application
You can use application for example by running provided docker-compose or by setting up PostgresSQL and using IDE. 

## Hexagonal Architecture
Design of service follows hexagonal architecture (onion architecture) principles.
It's reflected in way how packaging is done. Each service have packages:

* **boundary** API contract of the service. Classes in `boundary` package may have dependency only to classes from `domain` package.
  * sync API - controller subpackage
* **domain** - business domain. Classes from `domain` package may not have dependencies to classes in `boundary` and `infrastructure` packages.
* **infrastructure** - technical classes not solely related with business logic, i.e. repositories, clients, utils.
  Classes in `infrastructure` package may have dependency only to classes from `domain` package.

To sum it up, it should be possible to extract `boundary`, `domain` and `infrastructure` to separate jars
so that `boundary` has dependency to `domain`, and `infrastructure` has dependency to `domain`.
