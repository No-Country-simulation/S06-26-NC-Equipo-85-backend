# S06-26-NC-Equipo-85 Backend — API de App BiT

API de backend para App BiT, un proyecto de simulación de No Country centrado en ayudar a grupos subrepresentados mediante orientación personalizada, educación, empleabilidad, mentoría, experiencias estructuradas y apoyo a la salud mental.

Fase actual: configuración de la base/backend.

---

## Índice

- Resumen del proyecto
- Estado actual
- Tecnologías utilizadas
- Estructura del proyecto
- Estructuración de carpetas
- Instrucciones de configuración local
- Instrucciones de Docker
- Comandos de Maven
- Documentación de la API
- Endpoints de la API
- Variables de entorno
- Directrices de arquitectura
- Hoja de ruta del backend
- Integración del frontend
- Flujo de trabajo de Git
- Notas del equipo
- Descripción del desafío

---

## Resumen del proyecto

App BiT es un MVP diseñado para proporcionar orientación integral a personas de grupos subrepresentados que enfrentan barreras relacionadas con el empleo, la educación y la salud mental.

El backend se encarga de:

- Autenticación y autorización
- Gestión de perfiles de usuario
- Motor de orientación
- Búsqueda de empleo
- Cursos y rutas de aprendizaje
- Seguimiento de la salud mental
- Programación de mentorías
- Experiencias estructuradas
- Recomendaciones basadas en IA

---

## Estado actual

Fase actual: Fundación

Ya configurado:

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

Planificado:

- Autenticación
- Motor de orientación
- Integración de IA
- Módulo de salud mental
- Módulo de mentoría
- Cursos y rutas de aprendizaje

---

## Tecnologías utilizadas

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

---

## Estructura del proyecto

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

## Estructuración de carpetas

Estructura principal del repositorio, organizada por paquete y funcionalidad (feature-based):

```text
S06-26_Backend/
├── src/
│   ├── main/
│   │   ├── java/com/appbit/
│   │   │   ├── auth/                  # Registro, login y JWT
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   └── service/
│   │   │   ├── common/                # Elementos compartidos entre módulos
│   │   │   │   ├── enums/
│   │   │   │   └── exception/
│   │   │   ├── config/                # Configuración de Spring (seguridad, CORS, OpenAPI, etc.)
│   │   │   ├── course/                # Catálogo de cursos
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   ├── experience/            # Experiencias estructuradas
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   ├── gemini/                # Integración con IA (Gemini)
│   │   │   │   ├── controller/
│   │   │   │   └── service/
│   │   │   ├── health/                # Check-ins de salud mental
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   ├── job/                   # Ofertas de empleo y matching
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   ├── mentorship/            # Sesiones de mentoría
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   ├── orientation/           # Motor de orientación
│   │   │   │   ├── dto/
│   │   │   │   ├── OrientationController.java
│   │   │   │   └── OrientationService.java
│   │   │   ├── profile/               # Perfil de usuario
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   ├── security/              # Filtros y utilidades de seguridad/JWT
│   │   │   ├── skill/                 # Catálogo de habilidades
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   └── user/                  # Entidad y repositorio de usuario
│   │   │       ├── model/
│   │   │       └── repository/
│   │   └── resources/
│   │       ├── db/migration/          # Scripts de migración de Flyway
│   │       ├── application.yml        # Configuración base
│   │       ├── application-dev.yml    # Configuración del perfil "dev"
│   │       └── application-prod.yml   # Configuración del perfil "prod"
│   └── test/
│       └── java/com/appbit/           # Pruebas unitarias e de integración
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

Cada módulo de negocio (`auth`, `profile`, `course`, `job`, `skill`, `mentorship`, `experience`, `health`, `orientation`, `gemini`) sigue la misma subestructura interna:

- `controller/`: expone los endpoints REST.
- `dto/`: define los contratos de entrada y salida de la API.
- `model/`: entidades JPA, aisladas de los DTO.
- `repository/`: interfaces de acceso a datos (Spring Data JPA).
- `service/`: contiene la lógica de negocio.

---

## Instrucciones de configuración local

### Requisitos previos

- Java 21
- Maven
- Docker
- Docker Compose
- PostgreSQL 16

### Ejecución local

1. Clonar el repositorio.

2. Configurar las variables de entorno.

3. Iniciar PostgreSQL (se recomienda Docker).

4. Ejecutar las migraciones de Flyway automáticamente al iniciar la aplicación.

5. Iniciar la aplicación desde IntelliJ IDEA o Maven.

---

## Instrucciones de Docker

```bash
docker compose up --build
```

La API estará disponible en:

```texto
http://localhost:8080
```

---

## Comandos de Maven

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

## Documentación de la API

Interfaz de usuario de Swagger:

```texto
http://localhost:8080/swagger-ui/index.html
```

JSON de OpenAPI:

```texto
http://localhost:8080/v3/api-docs
```

---

## Endpoints de la API

Todos los endpoints, salvo los de autenticación, requieren un token JWT (`Authorization: Bearer <token>`).

### Autenticación (`/api/v1/auth`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| POST | `/api/v1/auth/register` | Registra un nuevo usuario y devuelve tokens JWT. |
| POST | `/api/v1/auth/login` | Autentica a un usuario y devuelve tokens JWT. |
| POST | `/api/v1/auth/refresh` | Renueva el token de acceso a partir de un refresh token válido. |

### Perfil (`/api/v1/profile`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| GET | `/api/v1/profile` | Obtiene el perfil del usuario autenticado. |
| PUT | `/api/v1/profile` | Crea o actualiza el perfil del usuario autenticado (upsert). |

### Habilidades (`/api/v1/skills`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| GET | `/api/v1/skills` | Recupera el catálogo completo de habilidades técnicas disponibles. |

### Cursos (`/api/v1/courses`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| GET | `/api/v1/courses` | Recupera el catálogo completo de cursos de formación. |

### Empleos (`/api/v1/jobs`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| GET | `/api/v1/jobs/matches?minMatch=` | Lista las ofertas de empleo compatibles con el usuario, filtradas por porcentaje mínimo de coincidencia. |
| GET | `/api/v1/jobs/{id}` | Obtiene el detalle de una oferta de empleo específica. |

### Orientación (`/api`)

Motor de orientación que combina habilidades, cursos y empleos para guiar al usuario.

| Método | Endpoint | Descripción |
| --- | --- | --- |
| POST | `/api/v1/guidance` | Genera una recomendación de orientación personalizada para el usuario. |
| POST | `/api/health` | Evalúa el estado de salud mental a partir de las respuestas enviadas. |
| GET | `/api/jobs/matches?userId=` | Obtiene las vacantes compatibles para un usuario dado. |
| GET | `/api/jobs/{id}` | Obtiene el detalle de una vacante de empleo por su id. |
| GET | `/api/skills` | Lista las habilidades registradas en el motor de orientación. |
| GET | `/api/courses` | Lista los cursos registrados en el motor de orientación. |

### Mentorías (`/api/v1/mentorships`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| POST | `/api/v1/mentorships/sessions` | Crea un espacio de sesión disponible (solo mentores). |
| GET | `/api/v1/mentorships/sessions?status&practice&date` | Lista sesiones con filtros opcionales (por defecto, estado AVAILABLE). |
| GET | `/api/v1/mentorships/sessions/{id}` | Obtiene el detalle de una sesión específica. |
| POST | `/api/v1/mentorships/sessions/{id}/book` | Reserva una sesión disponible (solo mentees). |
| PATCH | `/api/v1/mentorships/sessions/{id}/cancel` | Cancela una sesión (el mentor o el mentee asignado). |
| PATCH | `/api/v1/mentorships/sessions/{id}/complete` | Marca una sesión como completada (solo el mentor de la sesión). |
| GET | `/api/v1/mentorships/my-sessions` | Obtiene las sesiones del usuario autenticado, ya sea como mentor o mentee. |

### Salud mental (`/api/v1/health/checkins`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| POST | `/api/v1/health/checkins` | Registra un nuevo check-in de salud mental. |
| GET | `/api/v1/health/checkins` | Obtiene el historial de check-ins del usuario autenticado. |
| GET | `/api/v1/health/checkins/{id}` | Obtiene el detalle de un check-in específico. |
| POST | `/api/v1/health/checkins/{id}/empathic-response` | Genera una respuesta empática mediante IA para un check-in dado. |

### Experiencias (`/api/v1/experiences`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| POST | `/api/v1/experiences` | Crea una experiencia estructurada (solo mentores). |
| GET | `/api/v1/experiences?skillId&type` | Lista experiencias, con filtros opcionales por habilidad y tipo. |
| GET | `/api/v1/experiences/{id}` | Obtiene el detalle de una experiencia específica. |
| PUT | `/api/v1/experiences/{id}` | Actualiza una experiencia (solo el mentor propietario). |
| DELETE | `/api/v1/experiences/{id}` | Elimina una experiencia (solo el mentor propietario). |

### Pruebas de IA (`/api/test`)

| Método | Endpoint | Descripción |
| --- | --- | --- |
| GET | `/api/test/gemini` | Endpoint de prueba para verificar la integración con la IA de Gemini. |

---

## Variables de entorno

| Variable | Descripción | Predeterminado |

| ---------------------- | ------------------------ | ------------------------------------------------------------------------------------------ |

| SPRING_PROFILES_ACTIVE | Perfil Spring activo | dev |

| DB_HOST | Host de PostgreSQL | localhost |

| DB_PORT | Puerto de PostgreSQL | 5432 |

| DB_NAME | Nombre de la base de datos PostgreSQL | appbit |

| DB_USERNAME | Nombre de usuario de PostgreSQL | appbit |

| DB_PASSWORD | Contraseña de PostgreSQL | appbit |

| CORS_ALLOWED_ORIGINS | Orígenes de frontend permitidos | [http://localhost:3000,http://localhost:5173](http://localhost:3000,http://localhost:5173) |

Reglas importantes:

- Nunca confirmes secretos.

- Nunca confirmes credenciales de producción.

- Usa variables de entorno siempre que sea posible.

--

## Directrices de arquitectura

Enfoque por paquete y funcionalidad.

Características:

- Autenticación
- Perfil
- Orientación
- Estado

Directrices:

- Los controladores exponen puntos finales REST.

- Los servicios contienen lógica de negocio.

- Los DTO definen contratos de API.

- Las entidades están aisladas de las respuestas de la API.

- Flyway es la fuente de información fidedigna para las migraciones de bases de datos.

---

## Hoja de ruta del backend

### Fase 0 — Fundamentos

- Configuración del proyecto
- PostgreSQL
- Flyway
- Swagger
- Docker

### Fase 1 — Perfil y autenticación

- Registro de usuarios
- Inicio de sesión
- Autenticación JWT
- Gestión de perfiles

### Fase 2 — Orientación

- Cálculo de brechas
- Rutas de aprendizaje sugeridas
- Alineación con la empleabilidad

### Fase 3 — Salud mental

- Seguimiento
- Recomendaciones de IA
- Flujo de escalamiento de crisis

### Fase 4 — Funcionalidades avanzadas

- Mentoría
- Experiencias
- Eventos
- Notificaciones
