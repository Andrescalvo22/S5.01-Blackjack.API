## 📄 Blackjack API — Project Overview

This project is a Blackjack game API built with Spring Boot, Reactive MongoDB, and MySQL.
It allows players to start games, draw cards, stand, and track their global ranking.
The application demonstrates a complete backend system with persistence in two databases:

MongoDB (Reactive) → Stores active games

MySQL (JPA) → Stores player ranking and statistics

The project also includes Dockerization for full deployment and portability.

---

## 🚀 Features

Start a Blackjack game for a player

Draw cards (Hit) or finish the turn (Stand)

Automatic dealer behavior

Game resolution (win/loss/tie)

Persistent player statistics and ranking

REST API with Swagger UI

Fully Dockerized (Level 2 requirement)

---

## 💻 Technologies Used

-Java 17

-Spring Boot 3 (WebFlux, JPA, Spring Data MongoDB Reactive)

-MySQL 8

-MongoDB 6

-Maven

-Lombok

-Docker & Docker Compose

-Swagger / OpenAPI

-Netty Web Server

---

## ▶️ Running the Project Locally (Without Docker)

1️⃣ Requirements

-JDK 17

-Maven

-Local MySQL and MongoDB running

2️⃣ Run the backend
 ```bash
mvn clean install
mvn spring-boot:run
```
Open Swagger UI:
```bash
http://localhost:8080/swagger-ui.html
```

---

## 🐳 Running With Docker (Level 2)
1️⃣ Build the image
```bash
docker build -t blackjack-app .
```
2️⃣ Start containers
```bash
docker-compose up --build
```
Application will be available on:
```bash
http://localhost:8080/swagger-ui.html
```
3️⃣ Push to Docker Hub
```bash
docker tag blackjack-app yourname/blackjack-app:latest
docker push yourname/blackjack-app:latest
```
4️⃣ Run pulled image
```bash
docker run -p 8081:8080 yourname/blackjack-app:latest
```

---

## 🧩 UML Diagram

The class diagram of the system:

![UML Diagram](docs/uml_blackjack.svg)

---

## 📦 Project Structure
```
src/main/java/com/blackjack
├── controller      → REST endpoints
├── service         → Business logic
├── repository      → Mongo + SQL persistence
├── model           → Game, Deck, Card, Player...
├── dto             → Request/Response DTOs
└── sql             → JPA Entities (MySQL)
```
