# Exchange Rate API

## Description

This is an API that fetches exchange rates from publicly available API (https://exchangerate.host) and use it for conversion calculations.

## Prerequisites

As a prerequisite is a running [Redis](https://redis.io/) database. Redis is used for caching data in order to do fewer calls to the external provider.

In order to install Redis follow one of the following options :

1) Download Redis and install it from the official site :
```sh
https://redis.io/docs/getting-started/installation/ 
```
2) Install Redis Stack using [Docker](https://redis.io/docs/stack/get-started/install/docker/) with the following command :
```sh
docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server:latest
```

## Installation

There are two approaches:

1. Intellij or Eclipse IDE: Open project as Maven project and start main class :
   * com.kyriakos.Application
2. Use maven :
   * Run in project dir: $ mvn clean install
   * Navigate to target dir and run: $ java -jar exchange-<VERSION>.jar

Exchange API is built with Maven. Taken that into account, regular Maven tasks such as clean, compile, test tasks for development and testing can be used.

## Documentation
The API Testing interactive documentation has been created with [Swagger](https://swagger.io/). After starting the application Swagger can be accessed from the following URL :
*  http://localhost:8080/swagger-ui.html

## Endpoints
This API implements the following operations :

| Endpoint | Description |
| ------ | ------ |
| /api/exchange-rate | Get exchange rate from Currency A to Currency B |
| /api/exchange-rates | Get all exchange rates from Currency A |
| /api/exchange-rate-value | Get value conversion from Currency A to Currency B |
| /api/exchange-rates-value | Get value conversion from Currency A to a list of supplied currencies |

#### Example
Request URL : http://localhost:8080/api/exchange-rate

Input for the POST request is the following :
```sh
{
  "from": "EUR",
  "to": "USD"
}
```

The response will be the following :
```sh
{
  "from": "EUR",
  "to": "USD",
  "amount": 1,
  "result": 1.071692,
  "date": "2023-02-19"
}
```

## Tests

The project has been test with the help of the following :

* junit
* MockMvc

The tests are located in :

```sh
\src\test\java\com\kyriakos\
```

### App Structure
The code given is structured as follows :
```
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───kyriakos
│   │   │           ├───config
│   │   │           ├───controllers
│   │   │           ├───exceptions
│   │   │           ├───models
│   │   │           │   ├───external
│   │   │           │   └───internal
│   │   │           ├───repositories
│   │   │           ├───services
│   │   │           └───utils
│   │   └───resources
│   │       └───static
│   │           └───swagger
│   │               └───api
│   └───test
│       └───java
│           └───com
│               └───kyriakos
│                   ├───controllers
│                   ├───redis
│                   ├───services
│                   └───utils
└───target
    ├───classes
    │   ├───com
    │   │   └───kyriakos
    │   │       ├───config
    │   │       ├───controllers
    │   │       ├───exceptions
    │   │       ├───models
    │   │       │   ├───external
    │   │       │   └───internal
    │   │       ├───repositories
    │   │       ├───services
    │   │       └───utils
    │   └───static
    │       └───swagger
    │           └───api
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    └───test-classes
        └───com
            └───kyriakos
                ├───controllers
                ├───redis
                ├───services
                └───utils
```

## Technology stack
This project uses the following technologies :
- Java
- Spring Boot
- Redis
- Swagger
- Maven

## Future work
As a future work the following enhancements could be done :

* Dockerize the project and deploy it among Redis.
* Add more external providers in case some of them are down.
* Add a robust scheduler like [Quartz](http://www.quartz-scheduler.org/) in order to handle better the Redis caching.
* Add more tests.

