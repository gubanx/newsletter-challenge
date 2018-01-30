# Newsletter example with microservices

This is a simple REST API application that manages user subscriptions to a
newsletter with a microservice approach.

## How to build

From de root of each service run:

    $ mvn spring-boot:run
    
The order must be:
- subscription-service
- email-service
- event-service

---

## How to test API REST

* Add a subscription. The header Location contains the URL of the created subscription.

    $ curl -v -X POST -H "Content-Type: application/json" http://localhost:8080/subscriptions -d '{"email":"fabrejm@gmail.com","first_name":"JM Fabre","gender":"MALE","date_of_birth":"1977-06-24","consent":true}'


* Get all subscriptions

    $ curl -v http://localhost:8080/subscriptions

* Get a subscription

    $ curl -v http://localhost:8080/subscriptions/{id}

* Delete a subscription

    $ curl -v -X DELETE http://localhost:8080/subscriptions/{id}

---

## Docker

You can create docker images. From de root of each service (the order doesn't matter) run:

    $ mvn clean package fabric8:build -Pdocker
    
You can start docker containers one-by-one with docker or with docker-compose. From the root of the project run:

    $ docker-compose up -d
    
---

## To-do list

- [x] Authenticate access to REST api with OAuth
- [ ] Use https
- [ ] Use redis cache for reducing data base access.
- [ ] Comunicate event with a queu (RabbitMQ).
- [ ] More unit and integration tests.
- [ ] CI/CD with travis.
- [ ] Docker containers orchestration with Kubernetes.

