# appbit-api

Backend API for App BiT built with Java 21 and Spring Boot.

## Technologies Used

- Java 21
- Spring Boot 3.3.x
- Maven
- PostgreSQL 16
- Flyway Migration
- Spring Data JPA
- Spring Web
- Spring Security
- Jakarta Validation
- Lombok
- OpenAPI / Swagger
- Docker
- Docker Compose

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

## Local Setup Instructions

1. Install Java 21, Maven, and PostgreSQL 16.
2. Create a PostgreSQL database named `appbit`.
3. Configure environment variables or use the defaults from `application-dev.yml`.
4. Start the application from IntelliJ IDEA using the `dev` profile.

## Docker Instructions

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`.

## Maven Commands

```bash
mvn clean compile
mvn test
mvn spring-boot:run
```

## API Documentation Location

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON is available at:

```text
http://localhost:8080/v3/api-docs
```

## Environment Variables

| Variable | Description | Default |
| --- | --- | --- |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `DB_HOST` | PostgreSQL host | `localhost` |
| `DB_PORT` | PostgreSQL port | `5432` |
| `DB_NAME` | PostgreSQL database name | `appbit` |
| `DB_USERNAME` | PostgreSQL username | `appbit` |
| `DB_PASSWORD` | PostgreSQL password | `appbit` |
| `CORS_ALLOWED_ORIGINS` | Comma-separated allowed origins | `http://localhost:3000,http://localhost:5173` |

## Challenge Description

TODO: Paste the official App BiT challenge statement here.
