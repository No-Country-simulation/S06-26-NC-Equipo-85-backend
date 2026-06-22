# Especificación Técnica de Endpoints y Modelo de Datos (Actualizado)

Este documento contiene el diseño de la API RESTful estructurado por fases para el proyecto, adaptado al nuevo modelo de datos relacional que incorpora las siguientes entidades: `User`, `Profile`, `Mentorship_sessions`, `Mod_checkins`, `Profile_skills`, `Skill`, `Experience_skill`, `Experience`, `Job`, `Job_skills`, `Course`, y `Course_skills`.

---

## 📑 Contrato de Endpoints por Fases

### 🔒 Fase 1 — Autenticación, Usuarios y Perfiles

_Basado en las entidades: `User` y `Profile`._

| Método   | Ruta                    | Descripción                                                                           | Request Body / Query Params                                                                                                                                                      |
| :------- | :---------------------- | :------------------------------------------------------------------------------------ | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **POST** | `/api/v1/auth/register` | Registro de nuevo usuario y credenciales iniciales.                                   | `{ "email": "...", "password": "...", "role": "..." }`                                                                                                                           |
| **POST** | `/api/v1/auth/login`    | Autenticación de usuario. Devuelve Access Token (JWT) + Refresh Token.                | `{ "email": "...", "password": "..." }`                                                                                                                                          |
| **POST** | `/api/v1/auth/refresh`  | Renueva un Access Token expirado utilizando el Refresh Token válido.                  | `{ "refresh_token": "..." }`                                                                                                                                                     |
| **GET**  | `/api/v1/profile`       | Obtiene la información completa del perfil del usuario autenticado.                   | _Ninguno (Identificado mediante JWT)_                                                                                                                                            |
| **PUT**  | `/api/v1/profile`       | Actualiza datos personales y profesionales del perfil (incluye ubicación y contacto). | `{ "name": "...", "birth_date": "...", "gender": "...", "education": "...", "location": { "country": "...", "state": "...", "city": "..." }, "contact": { "whatsapp": "..." } }` |

---

### 🧠 Fase 2 — Motor de Matching e Itinerarios Profesionales

_Basado en las entidades: `Profile`, `Profile_skills`, `Skill`, `Job`, `Job_skills`, `Course`, y `Course_skills`._

| Método   | Ruta                       | Descripción                                                                                                           | Request Body / Query Params                                                                                            |
| :------- | :------------------------- | :-------------------------------------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------- |
| **POST** | `/api/v1/orientar`         | **[Core Endpoint]** Calcula el gap técnico, genera la trayectoria recomendada de cursos y busca vacantes compatibles. | `{ "usuario_id": 1, "perfil": "...", "nivel": "...", "region": "...", "idioma": "...", "lat": -17.39, "lng": -66.15 }` |
| **GET**  | `/api/v1/skills`           | Recupera el catálogo global de habilidades técnicas y competencias del sistema.                                       | _Ninguno_                                                                                                              |
| **GET**  | `/api/v1/courses`          | Lista las formaciones y cursos disponibles. Permite filtrar para mitigar brechas específicas.                         | _Query Params opcionales:_ `?skill_id=12&level=INTERMEDIATE`                                                           |
| **GET**  | `/api/v1/jobs/compatibles` | Devuelve las vacantes que superan el umbral porcentual mínimo de coincidencia de habilidades.                         | _Query Params opcionales:_ `?min_match=50`                                                                             |

#### 📥 Detalle de Estructura de Datos para `/api/v1/orientar`

- **Response Body (`200 OK`):**
  Archivo generado exitosamente.

```json
{
  "gap_porcentual": 35.5,
  "gap_items": [
    { "id": 4, "name": "PostgreSQL", "level": "Required" },
    { "id": 8, "name": "Docker", "level": "Required" }
  ],
  "trayectoria_sugerida": [
    {
      "course_id": 102,
      "title": "Introducción a Bases de Datos con PostgreSQL",
      "provider": "ONE (Oracle & Alura)",
      "skills_contribuidos": ["PostgreSQL"]
    }
  ],
  "vacantes_compatibles": [
    {
      "job_id": 45,
      "company": "Tech Solutions",
      "title": "Junior Backend Developer",
      "match_rate": 64.5
    }
  ],
  "confianza": 92.0
}
```

### 🧠 Fase 3 — Salud Mental (Check-ins), Mentorías y Experiencias

\_Basado en las entidades: `Mod_checkins`, `Mentorship_sessions`, `Experience` y `Experience_skill`

| Método   | Ruta                                        | Descripción                                                                                                   | Request Body / Query Params                                                |
| :------- | :------------------------------------------ | :------------------------------------------------------------------------------------------------------------ | :------------------------------------------------------------------------- |
| **POST** | `/api/v1/salud`                             | **[Core Endpoint]** Registra el check-in emocional. Si la nota es crítica, activa el protocolo de derivación. | `{ "usuario_id": 1, "humor": "😊", "nota_semanal": 3, "contexto": "..." }` |
| **POST** | `/api/v1/salud/stream`                      | Canal asíncrono Server-Sent Events (SSE) para la respuesta empática fluida del Agente IA.                     | `{ "checkin_id": 120 } `                                                   |
| **GET**  | `/api/v1/salud/historial`                   | Recupera la serie histórica de check-ins del usuario para analíticas visuales de bienestar.                   | Ninguno (Usa el contexto del token)                                        |
| **GET**  | `/api/v1/mentorships/slots`                 | Lista las sesiones de mentoría y espacios de tiempo disponibles para reserva médica/técnica.                  | _Query Params opcionales:_ `?area=tech&status=AVAILABLE`                   |
| **POST** | `/api/v1/mentorships/sessions/{id}/agendar` | Reserva una sesión de mentoría específica aplicando control de concurrencia optimista.                        | `{ "usuario_id": 1 }`                                                      |
| **GET**  | `/api/v1/experiencias`                      | Devuelve el feed de testimonios estructurantes en video y trayectorias de éxito.                              | _Query Params opcionales:_ `?skill_id=5` `                                 |

#### 📥 Detalle de Estructura de Datos para `/api/v1/salud`

- **Logica Critica**
  Si nota_semanal < 4, el sistema fuerza de manera síncrona y obligatoria derivar_cvv: true y alerta: true.

- **Response Body (`200 OK`):**

```json
{
  "mensaje": "Hemos recibido tu check-in. Tu bienestar es nuestra prioridad.",
  "accion_sugerida": "Te sugerimos tomar un descanso de 15 minutos y escuchar este podcast de relajación.",
  "derivar_cvv": true,
  "nota_actual": 3,
  "alerta": true
}
```

### 🧠 Fase 4 — Geolocalización (PostGIS Vísent CDRView) y Notificaciones

Basado en consultas espaciales sobre la ubicación del perfil cruzada con mapas de cobertura.

| Método   | Ruta                              | Descripción                                                                                                  | Request Body / Query Params                                         |
| :------- | :-------------------------------- | :----------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------ |
| **GET**  | `/api/v1/geo/eventos-cercanos`    | Retorna eventos y ofertas de empleo cercanas (ST_DWithin). Activa sugerir_offline si detecta baja cobertura. | _Query Params requeridos:_ `?lat=-17.39&lng=-66.15&radio=5000`      |
| **POST** | `/api/v1/notifications/subscribe` | Registra el endpoint de suscripción del navegador para el envío de notificaciones push (VAPID).              | `{ "endpoint": "...", "keys": { "p256dh": "...", "auth": "..." } }` |
