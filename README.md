# Incident Management

## Introduction

This service provide incident management capability as a standalone independent microservice. 
It follows the RESTful API design principles and uses Spring Boot as the framework.

It uses Spring Data REST framework for basic simple CRUD operations, 
whereas customize complex APIs are implemented in the controllers.

## Functionalities

### Spring Data REST based pure CRUD APIs.

These set of APIs are usually used in internal automation or testing, should not be exposed to customer directly.

| Method | URL                 | Description                                                |
|--------|---------------------|------------------------------------------------------------|
| GET    | /rest/incident      | Get all incidents with parameters                          |
| POST   | /rest/incident      | Create a new incident                                      |
| GET    | /rest/incident/{id} | Get an incident by id                                      |
| PATCH  | /rest/incident/{id} | Update an incident by id and a set of changed properties   |
| DELETE | /rest/incident/{id} | Delete an incident by id                                   |

### Customized domain driven APIs with RESTful design.

These set of APIs are designed for domain driven business logic with proper validation, it is the one that should be exposed to customer.

| Method | URL                        | Description                                            |
|--------|----------------------------|--------------------------------------------------------|
| GET    | /api/incident/{id}         | Get an incident by id                                  |
| GET    | /api/incident              | Get all incidents with known attributes as parameters  |
| POST   | /api/incident              | Fire a new incident                                    |
| PATCH  | /api/incident:process/{id} | Mark an incident as has been picked up for processing. |
| PATCH  | /api/incident:close/{id}   | Close an incident when it is finished.                 |
| DELETE | /api/incident/{id}         | Delete an incident by id                               |

### Error Handling

#### Example response

```json
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Incident cannot be processing because it is CLOSED",
    "instance": "/api/incident:process/7d2da9f3-d955-4828-9e14-93cfffaa7d36"
}
```

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

## Libraries Used

* [Flyway](https://github.com/flyway/flyway): A database DevOps framework manage database changes along with application change.
* [Lombok](https://projectlombok.org/): A library to reduce boilerplate code in Java to improve code readability and productivity.

## Known Issues

* Spring Data REST doesn't support hibernate annotation `org.hibernate.annotations.SQLDelete` for soft deletion.

## Roadmaps

