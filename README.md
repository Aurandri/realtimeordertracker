# Realtime Order Tracker

A containerized real-time order tracking system using:

* Spring Boot (Backend + Frontend)
* PostgreSQL
* Apache Kafka

Frontend is served directly by the Spring Boot application for simplicity.

---

## 🚀 Quick Start

### 1. Clone

```bash
git clone https://github.com/Aurandri/realtimeordertracker
```
```bash
cd realtimeordertracker
```

### 2. Setup env
Serves to duplicate the environment template file into an active configuration file.
```bash
cp .env.example .env
```

### 3. Run

```bash
docker compose up --build
```

---

## 🌐 Access

* App: http://localhost:8081
* Kafka UI: http://localhost:8080
* PostgreSQL: localhost:5432
* Kafka: localhost:9092

---

## 🧩 Architecture

Services communicate via Docker network:

* backend → postgres
* backend → kafka
* backend → kafka-ui (using provectuslabs/kafka-ui:latest to see kafka messages via UI)

Example:

```
jdbc:postgresql://postgres:5432/realtimeordertrackerdb
```

---

## ⚙️ Config

All configs via `.env`:

* DB: POSTGRES_*
* Kafka: KAFKA_*
* App: BACKEND_PORT, APP_PROFILE, DDL_AUTO

---

## 🔄 Commands

```bash
# stop
docker compose down

# reset DB
docker compose down -v

# logs
docker compose logs -f
```

---

## 📦 Requirements

* Docker
* Docker Compose

---

## ✅ Run in One Command

```bash
docker compose up
```
