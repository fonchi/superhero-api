# Superhero API - W2M Challenge

This is an implementation of a **[W2M technical interview challenge](Challenge.pdf)** when it
requires developing an RESTful API to management superheroes.

## Technology Stack

This API was built using the following technologies:

- **Java 11** programming language
- **Spring Boot 2** application framework
    - Spring Security
    - Spring Data with JPA
- **Maven** dependency management tool
- **Docker** containerized platform
- **GIT** source version control tool
- **Flyway** database migration tool
- **Lombok** library
- **H2** on memory database

## Architecture

The API was built based on hexagonal architecture using Domain Driven Design approach (DDD)
which poses a segregation between domain, application and infrastructure logic though
ports and adapters in order to isolate domain logic from external factors. This project take
advantage of the java polymorphism capacity for separate those layers using interfaces and
implementations.

![alt text](hexagonal.png)
<div style="text-align: right"><i>hexagonal concept illustration image</i></div>

### Project Structure

The project structure follows the hexagonal way and has three main packages:
- **application**: contains application layer logic, in this case using http rest communication.
- **domain**: contains domain business layer logic, and the idea is that it doesn't have application
  or infrastructure implementations and technologies.
- **infrastructure**: contains anything that the application needs to work such as database
  implementation, spring configuration, etc.

The structure is:
```
superhero
├── application
│   ├── dto
│   └── rest
├── domain
│   ├── model
│   ├── repository
│   └── service
└── infrastructure
    ├── repository
    │   └── sql
    ├── exception
    ├── logging
    └── security
```

## Developing Methodologies

This project was developed using **TDD** software development process.

The branching model used for the version control was **GitFlow**.

## Project Setup Guide

### To Get Repository
Clone repository in your local environment using the following command:
```
git clone https://github.com/fonchi/superhero-api.git
cd superhero-api
```

### To Run on Docker Environment
First install [docker desktop](https://www.docker.com/) and after that execute following commands:
```
docker build -t superhero-api .
docker run -p 8080:8080 superhero-api
```

### To Run on Local Environment
First you need to have installed Java 11 and Maven 3 on your machine. After that you can execute
following commands:
```
mvn clean install
mvn spring-boot:run
```

## API Endpoints

- [GET /superheroes?name={name}](#get-superheroes)
- [GET /superheroes/{id}](#get-superhero)
- [PATCH /superheroes/{id}](#update-superhero)
- [DELETE /superheroes/{id}](#delete-superhero)

### GET Superheroes

*Endpoint to get list superheroes filtering by name query param*

Authentication: *Basic Auth*
```
Username: user
Password: password
```

Response Status:
```
HTTP 200-OK
```

Response Body:
```
{
    "total": 3,
    "superheroes": [
        {
            "id": 1,
            "name": "Spiderman",
            "creation_date": "2021-12-16T00:00:00Z",
            "update_date": "2021-12-17T00:00:00Z"
        },
        {
            "id": 2,
            "name": "Superman",
            "creation_date": "2021-12-16T00:00:00Z",
            "update_date": "2021-12-17T00:00:00Z"
        },
        {
            "id": 3,
            "name": "Batman",
            "creation_date": "2021-12-16T00:00:00Z",
            "update_date": "2021-12-17T00:00:00Z"
        }
    ]
}
```

CURL:
```
curl --location --request GET 'localhost:8080/superheroes?name=man' \
--header 'Authorization: Basic dXNlcjpwYXNzd29yZA==' \
--header 'Cookie: JSESSIONID=AB9E9DE6A4149E25DA7EE2E866926AF7'
```

### GET Superhero

*Endpoint to get a superhero by resource id*

Authentication: *Basic Auth*
```
Username: user
Password: password
```

Response Status:
```
HTTP 200-OK
```

Response Body:
```
{
    "id": 1,
    "name": "Spiderman",
    "creation_date": "2021-12-16T00:00:00Z",
    "update_date": "2021-12-17T00:00:00Z"
}
```

CURL:
```
curl --location --request GET 'localhost:8080/superheroes/1' \
--header 'Authorization: Basic dXNlcjpwYXNzd29yZA==' \
--header 'Cookie: JSESSIONID=AB9E9DE6A4149E25DA7EE2E866926AF7'
```

### PATCH Superhero

*Endpoint to update a name of superhero by resource id*

Authentication: *Basic Auth*
```
Username: admin
Password: password
```

Headers:
```
Content-Type: application/json
```

Request Body:
```
{
    "name": "Wolverine"
}
```

Response Status:
```
HTTP 200-OK
```

Response Body:
```
{
    "id": 1,
    "name": "Wolverine",
    "creation_date": "2021-12-16T00:00:00Z",
    "update_date": "2021-12-21T20:54:53.860444Z"
}
```

CURL:
```
curl --location --request PATCH 'localhost:8080/superheroes/1' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=AB9E9DE6A4149E25DA7EE2E866926AF7' \
--data-raw '{
    "name": "Wolverine"
}'
```

### DELETE Superhero

*Endpoint to remove a superhero by resource id*

Authentication: *Basic Auth*
```
Username: admin
Password: password
```

Response Status:
```
HTTP 200-OK
```

Response Body:
```
{
    "id": 1,
    "name": "Wolverine",
    "creation_date": "2021-12-16T00:00:00Z",
    "update_date": "2021-12-21T20:54:53.860444Z"
}
```

CURL:
```
curl --location --request DELETE 'localhost:8080/superheroes/1' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--header 'Cookie: JSESSIONID=AB9E9DE6A4149E25DA7EE2E866926AF7'
```

### Error Response

If the superhero doesn't exists with the provided id, you can get and error result like this:

Response body:

```
{
    "status": 404,
    "error": "Resource not found",
    "message": "Superhero not found by id = 1"
}
```

## Final Comments

### Possible Additional Features

The project was thought very simple based on challenge requirements, but It can be improvement
with some additional features like:
- Complete domain model with additional attributes like: skills, appereance, characteristics,
relationships, biography, occupation, etc.
- POST endpoint for superhero creation
- Change deletion endpoint to make a logic removing superheros based a deletion flag; it
  depends on project requirements.
- Add more filters on GET superheroes endpoint to be able to filter result based on more 
attributes like creation_date by datetime range, and others more with having a 
complete domain. We can use jpa Specification for searching by additional criteria making a 
predicates. We also can add page and size query params to get a pagination result to avoid
sql performance problems based on spring data Pageable.
- Add authentication endpoints like login and logout and use JWT for improve security layer
using a robust way based on user token.
- Add request and responses logging to audit requests using AOP.
- Add lock service in order to lock resources and avoid concurrent problems when two or more 
request try to modify the same resource in a race condition, unlocking resource at the end of 
the method.
- Add publish service to produce news message when a superhero is created, updated or deleted
using a producer through topic queue to communicate it with others API in a microservices 
architecture based on event sourcing pattern.
To make it we can use some tools like Apache Kafka, RabbitMQ, Amazon SQS, etc.
- Add metrics service to record a metrics of creation, update and deletion superheroes and data of
requests using external services like New Relic and DataDog.
