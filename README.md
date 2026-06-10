# S06-26-NC-Equipo-85 Backend — App BiT API

Backend API for App BiT, a No Country simulation project focused on helping underrepresented groups through personalized orientation, education, employability, mentoring, structured experiences and mental health support.

Current phase: foundation / backend setup.

---

## Table of Contents

* Project Overview
* Current Status
* Technologies Used
* Project Structure
* Local Setup Instructions
* Docker Instructions
* Maven Commands
* API Documentation
* Environment Variables
* Architecture Guidelines
* Backend Roadmap
* Frontend Integration
* Git Workflow
* Team Notes
* Challenge Description

---

## Project Overview

App BiT is an MVP designed to provide integrated guidance for people from underrepresented groups who face barriers related to employment, education and mental health.

The backend is responsible for:

* Authentication and authorization
* User profile management
* Orientation engine
* Employability matching
* Courses and learning paths
* Mental health check-ins
* Mentorship scheduling
* Structured experiences
* AI-powered recommendations

---

## Current Status

Current phase: Foundation.

Already configured:

* Java 21
* Spring Boot 3.3.x
* Maven
* PostgreSQL 16
* Flyway Migration
* Spring Data JPA
* Spring Web
* Spring Security
* Jakarta Validation
* Lombok
* OpenAPI / Swagger
* Docker
* Docker Compose

Planned:

* Authentication
* Orientation engine
* AI integration
* Mental health module
* Mentorship module
* Courses and learning paths

---

## Technologies Used

* Java 21
* Spring Boot 3.3.x
* Maven
* PostgreSQL 16
* Flyway Migration
* Spring Data JPA
* Spring Web
* Spring Security
* Jakarta Validation
* Lombok
* OpenAPI / Swagger
* Docker
* Docker Compose

---

## Project Structure

```text
com.appbit
├── config
├── common
├── auth
├── profile
├── orientation
└── health
```

---

## Local Setup Instructions

### Prerequisites

* Java 21
* Maven
* Docker
* Docker Compose
* PostgreSQL 16

### Run locally

1. Clone the repository.
2. Configure environment variables.
3. Start PostgreSQL (Docker recommended).
4. Run Flyway migrations automatically on startup.
5. Start the application from IntelliJ IDEA or Maven.

---

## Docker Instructions

```bash
docker compose up --build
```

The API will be available at:

```text
http://localhost:8080
```

---

## Maven Commands

```bash
mvn clean compile
```

```bash
mvn test
```

```bash
mvn spring-boot:run
```

```bash
mvn clean install
```

---

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

---

## Environment Variables

| Variable               | Description              | Default                                                                                    |
| ---------------------- | ------------------------ | ------------------------------------------------------------------------------------------ |
| SPRING_PROFILES_ACTIVE | Active Spring profile    | dev                                                                                        |
| DB_HOST                | PostgreSQL host          | localhost                                                                                  |
| DB_PORT                | PostgreSQL port          | 5432                                                                                       |
| DB_NAME                | PostgreSQL database name | appbit                                                                                     |
| DB_USERNAME            | PostgreSQL username      | appbit                                                                                     |
| DB_PASSWORD            | PostgreSQL password      | appbit                                                                                     |
| CORS_ALLOWED_ORIGINS   | Allowed frontend origins | [http://localhost:3000,http://localhost:5173](http://localhost:3000,http://localhost:5173) |

Important rules:

* Never commit secrets.
* Never commit production credentials.
* Use environment variables whenever possible.

---

## Architecture Guidelines

Package-by-feature approach.

Features:

* auth
* profile
* orientation
* health

Guidelines:

* Controllers expose REST endpoints.
* Services contain business logic.
* DTOs define API contracts.
* Entities are isolated from API responses.
* Flyway is the source of truth for database migrations.

---

## Backend Roadmap

### Phase 0 — Foundation

* Project setup
* PostgreSQL
* Flyway
* Swagger
* Docker

### Phase 1 — Profile & Auth

* User registration
* Login
* JWT authentication
* Profile management

### Phase 2 — Orientation

* Gap calculation
* Suggested learning paths
* Employability matching

### Phase 3 — Mental Health

* Check-ins
* AI recommendations
* Crisis escalation flow

### Phase 4 — Advanced Features

* Mentorship
* Experiences
* Events
* Notifications

### Phase 5 — QA & Deployment

* Testing
* Monitoring
* Production deployment

---

## Frontend Integration

Frontend repository:

S06-26-NC-Equipo-85

Expected initial endpoints:

| Method | Endpoint       | Purpose                            |
| ------ | -------------- | ---------------------------------- |
| POST   | /auth/register | Create user account                |
| POST   | /auth/login    | Authenticate user                  |
| GET    | /profile       | Get user profile                   |
| PUT    | /profile       | Update user profile                |
| POST   | /orientar      | Get orientation and suggested path |
| POST   | /salud         | Submit mental health check-in      |

Before consuming an endpoint, frontend and backend teams should define:

* URL
* Method
* Request body
* Response contract
* Error contract

---

## Git Workflow

Recommended branch naming:

```text
feature/short-description
fix/short-description
chore/short-description
docs/short-description
```

Examples:

```text
feature/orientation-endpoint
feature/auth-login
fix/security-config
docs/update-readme
```

Commit convention:

```text
type: short description
```

Examples:

```text
feature: create orientation endpoint
chore: configure flyway
docs: update readme
```

---

## Team Notes

This repository contains the backend application only.

Frontend and backend are maintained separately.

Repository:

S06-26-NC-Equipo-85-backend

---

## Challenge Description

TODO: Paste the official App BiT challenge statement here.

---

## Maintainers

No Country Simulation — S06-26-NC-Equipo-85

Backend Team

* Gabriel
* Backend contributors to be added

---

## License

This project is part of a No Country simulation and is intended for educational and portfolio purposes.

License status: pending.
