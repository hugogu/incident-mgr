# Incident Management

## Introduction

This service provide incident management capability as a standalone independent microservice. 
It follows the RESTful API design principles and uses Spring Boot as the framework.

It uses Spring Data REST framework for basic simple CRUD operations, 
whereas customize complex APIs are implemented in the controllers.

## Libraries Used

* [Flyway](https://github.com/flyway/flyway): A database DevOps framework manage database changes along with application change.
* [Lombok](https://projectlombok.org/): A library to reduce boilerplate code in Java to improve code readability and productivity.

## How to run

In your working directory is in the root of the project, do:

1. Build Image. It will produce an image named `incident-mgr` which is used in the `docker-compose.yaml`.
    ```shell
    ./mvnw spring-boot:build-image
    ```
2. Run
    ```shell
    docker-compose up -d
    ```
