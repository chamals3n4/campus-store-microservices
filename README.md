## Campus Store Microservices

I built this to learn microservices architecture with Spring Boot. It covers service-to-service communication, an API gateway, and a self-built JWT auth flow.

This is a learning project, not a production system. I kept the business logic simple on purpose so I could focus on how the services are structured and how they talk to each other.

## Architecture

The system is split into independently deployable services, each owning its own database.

- **Auth Service** (port 8081): user registration and login, password hashing with BCrypt, JWT issuing
- **Product Service** (port 8082): CRUD for products and stock
- **Order Service** (port 8083): creates orders, calls Product Service synchronously to check and reduce stock
- **Notification Service** (port 8084): planned, will consume order events asynchronously via Kafka
- **API Gateway** (port 8080): single entry point, routes requests to the correct service by path

Order Service and Product Service communicate synchronously over REST using OpenFeign. When an order is placed, Order Service calls Product Service directly, waits for the response, and only saves the order if stock is available.

Order Service also publishes an event to Kafka after saving an order. Notification Service consumes that event independently and asynchronously, so it doesn't block the order request, and it still receives events created while it was offline.


## Tech stack

- Java 21, Spring Boot 4
- Spring Web, Spring Data JPA
- Spring Cloud Gateway, Spring Cloud OpenFeign
- PostgreSQL
- JJWT for token generation and validation
- Docker Compose for local infrastructure
- Maven, monorepo structure

## Project structure

```
campus-store/
  auth-service/
  product-service/
  order-service/
  notification-service/
  api-gateway/
  docker-compose.yml
  init-db.sql
```

Each service is its own Maven module with its own dependencies, configuration, and database schema.

## Running locally

Requirements: Java 21, Maven, Docker.

Start the database:

```
docker compose up -d
```

This starts a single Postgres container with three separate databases: `auth_db`, `product_db`, `order_db`.

Run each service (from its own module, or via IntelliJ):

```
./mvnw spring-boot:run
```

Start them in this order: Auth Service, Product Service, Order Service, then API Gateway. Notification Service and Kafka are not required yet since async messaging isn't wired up.

Once running, everything is reachable through the gateway on port 8080. For example:

```
POST http://localhost:8080/auth/register
POST http://localhost:8080/auth/login
GET  http://localhost:8080/products
POST http://localhost:8080/orders
```
