# App BiT

## Plataforma Inteligente de Orientación Profesional y Desarrollo Tecnológico

---

# Descripción del Proyecto

**App BiT** es una plataforma web desarrollada como parte del programa de simulación laboral **No Country S06-26**, cuyo propósito es brindar apoyo integral a personas pertenecientes a grupos subrepresentados dentro del sector tecnológico.

La aplicación acompaña al usuario durante su proceso de crecimiento profesional, desde el registro inicial hasta la búsqueda de oportunidades laborales, ofreciendo herramientas que facilitan la adquisición de conocimientos, el desarrollo de habilidades técnicas, la orientación profesional personalizada, el acceso a mentorías y el seguimiento del bienestar emocional.

Uno de los principales diferenciadores del proyecto es la incorporación de **Inteligencia Artificial mediante Google Gemini**, utilizada para generar respuestas empáticas y apoyar el proceso de orientación del usuario.

La plataforma fue desarrollada siguiendo una arquitectura moderna basada en un **Frontend desacoplado del Backend**, comunicándose mediante una API REST segura protegida mediante autenticación JWT.

---

# Objetivos del Proyecto

El desarrollo de App BiT busca resolver diferentes problemáticas presentes durante el proceso de inserción laboral en tecnología, proporcionando una plataforma centralizada que permita:

- Facilitar la orientación profesional mediante recomendaciones personalizadas.
- Reducir la brecha entre las habilidades actuales del usuario y las requeridas por el mercado laboral.
- Centralizar cursos de formación tecnológica.
- Mostrar oportunidades laborales compatibles con el perfil del usuario.
- Facilitar el acceso a mentorías con profesionales.
- Compartir experiencias estructuradas que sirvan como guía para otros usuarios.
- Realizar seguimiento del estado emocional mediante check-ins de salud mental.
- Incorporar Inteligencia Artificial para mejorar la experiencia del usuario mediante respuestas personalizadas.

---

# Arquitectura General

La solución está dividida en cuatro componentes principales.

```text
                    Usuario
                       │
                       ▼
        ┌────────────────────────────┐
        │        Frontend            │
        │ Next.js + React + TS       │
        └─────────────┬──────────────┘
                      │ REST API
                      ▼
        ┌────────────────────────────┐
        │         Backend            │
        │ Spring Boot + Java         │
        └─────────────┬──────────────┘
                      │
          ┌───────────┴───────────┐
          ▼                       ▼
 PostgreSQL                 Google Gemini AI
 Base de Datos            Respuestas Empáticas
```

Cada componente cumple una responsabilidad específica:

## Frontend

Corresponde a toda la interfaz gráfica utilizada por el usuario.

Se encarga de:

- Registro e inicio de sesión.
- Navegación por la plataforma.
- Visualización de cursos.
- Consulta de empleos.
- Gestión del perfil.
- Reserva de mentorías.
- Registro de experiencias.
- Check-ins de salud mental.
- Consumo de la API REST.

Toda la lógica de presentación se encuentra desarrollada utilizando **Next.js**, **React** y **TypeScript**, implementando una arquitectura organizada por funcionalidades (Feature Based Architecture).

---

## Backend

Representa el núcleo del sistema.

Es responsable de:

- Gestión de usuarios.
- Autenticación y autorización mediante JWT.
- Gestión de perfiles.
- Administración de cursos.
- Administración de habilidades.
- Gestión de empleos.
- Gestión de mentorías.
- Gestión de experiencias.
- Registro de check-ins de salud mental.
- Integración con Google Gemini.
- Persistencia de la información en PostgreSQL.
- Exposición de la API REST consumida por el Frontend.

El Backend fue desarrollado utilizando **Spring Boot**, siguiendo una arquitectura modular basada en paquetes por funcionalidad.

---

## Base de Datos

Toda la información del sistema se almacena en una base de datos PostgreSQL.

Entre los principales datos almacenados se encuentran:

- Usuarios
- Perfiles
- Habilidades
- Cursos
- Empleos
- Mentorías
- Experiencias
- Check-ins de salud mental
- Tokens de autenticación

Las modificaciones del esquema de base de datos son administradas mediante **Flyway**, permitiendo mantener un historial de migraciones versionado.

---

## Inteligencia Artificial

El proyecto incorpora integración con **Google Gemini** para proporcionar funcionalidades inteligentes dentro de la plataforma.

Actualmente la IA es utilizada para:

- Generar respuestas empáticas a partir de los check-ins de salud mental.
- Apoyar el motor de orientación profesional.
- Mejorar la experiencia del usuario mediante respuestas contextualizadas.

La comunicación con Gemini se realiza desde el Backend, manteniendo protegidas las credenciales de acceso y evitando que el Frontend interactúe directamente con el servicio de IA.

---

# Funcionalidades Principales

La plataforma se encuentra dividida en diferentes módulos funcionales.

## Autenticación

Permite:

- Registro de nuevos usuarios.
- Inicio de sesión.
- Renovación automática de tokens.
- Autenticación mediante JWT.
- Gestión de roles.

---

## Perfil de Usuario

Cada usuario puede registrar información relacionada con:

- Datos personales.
- Tecnologías conocidas.
- Habilidades.
- Nivel profesional.
- Objetivos de aprendizaje.

Esta información es utilizada posteriormente por el motor de orientación para generar recomendaciones personalizadas.

---

## Cursos

El sistema ofrece un catálogo de cursos relacionados con tecnología.

Cada curso contiene información relevante para facilitar el aprendizaje del usuario y complementar su ruta profesional.

---

## Empleabilidad

El módulo de empleos permite visualizar ofertas laborales compatibles con el perfil del usuario.

Las vacantes pueden filtrarse según el porcentaje de coincidencia entre las habilidades requeridas por la empresa y las habilidades registradas por el usuario.

---

## Mentorías

Los mentores pueden publicar sesiones disponibles.

Los usuarios pueden:

- Consultar mentorías.
- Reservar sesiones.
- Cancelarlas.
- Finalizarlas.

Este módulo facilita el intercambio de conocimientos entre profesionales y nuevos talentos.

---

## Experiencias

Los mentores pueden publicar experiencias estructuradas relacionadas con su trayectoria profesional.

Estas experiencias sirven como material de apoyo para otros usuarios interesados en recorrer un camino profesional similar.

---

## Salud Mental

La plataforma incorpora un sistema de seguimiento emocional mediante check-ins periódicos.

Cada registro puede ser analizado utilizando Inteligencia Artificial para generar respuestas empáticas orientadas al bienestar del usuario.

---

## Motor de Orientación

A partir del perfil del usuario, el sistema es capaz de recomendar:

- Cursos.
- Habilidades por desarrollar.
- Empleos compatibles.
- Recursos adicionales.

El objetivo es construir una ruta personalizada de crecimiento profesional.

---

# Tecnologías Utilizadas

El proyecto fue desarrollado utilizando tecnologías modernas ampliamente utilizadas en la industria del desarrollo de software. La arquitectura está dividida en dos grandes componentes: Frontend y Backend, permitiendo que cada uno evolucione de forma independiente.

---

## Frontend

El Frontend está construido sobre **Next.js 16**, utilizando React y TypeScript para desarrollar una interfaz moderna, escalable y altamente mantenible.

Las principales tecnologías utilizadas son:

- Next.js 16 (App Router)
- React 19
- TypeScript
- Tailwind CSS v4
- Turborepo
- pnpm Workspaces
- TanStack Query
- Zustand
- React Hook Form
- Zod
- Next Intl
- Storybook
- Sonner
- Docker

Estas tecnologías permiten construir una aplicación rápida, modular y preparada para escalar.

---

## Backend

El Backend fue desarrollado utilizando el ecosistema Spring.

Las principales tecnologías utilizadas son:

- Java 21
- Spring Boot 3.3.x
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL 16
- Flyway
- Maven
- Docker
- OpenAPI
- Swagger
- Google Gemini API
- JWT (JSON Web Token)

El Backend sigue una arquitectura modular basada en funcionalidades, facilitando el mantenimiento y la incorporación de nuevos módulos.

---

## Base de Datos

Toda la información del sistema es almacenada utilizando PostgreSQL.

Las migraciones son administradas mediante Flyway, permitiendo mantener el historial completo de cambios en la estructura de la base de datos.

---

## Herramientas Complementarias

Durante el desarrollo también se utilizaron diversas herramientas de apoyo:

- Git
- GitHub
- Docker Compose
- IntelliJ IDEA
- Visual Studio Code
- Postman
- Swagger UI

---

# Arquitectura del Frontend

El Frontend tiene como objetivo proporcionar una experiencia de usuario moderna, intuitiva y responsive.

Se encuentra desarrollado siguiendo una arquitectura basada en funcionalidades (Feature Based Architecture), donde cada módulo de la aplicación posee sus propios componentes, lógica y servicios.

Esta organización permite que el código sea más sencillo de mantener, reutilizar y extender.

La comunicación con el Backend se realiza exclusivamente mediante una API REST.

---

## Responsabilidades del Frontend

Entre las principales responsabilidades se encuentran:

- Mostrar la interfaz al usuario.
- Validar formularios.
- Gestionar el inicio de sesión.
- Almacenar temporalmente la sesión del usuario.
- Consumir la API del Backend.
- Mostrar información obtenida desde la base de datos.
- Administrar la navegación de la aplicación.
- Internacionalización de la plataforma.
- Gestión del estado de la interfaz.

El Frontend no contiene lógica de negocio crítica, ya que toda la información importante es procesada por el Backend.

---

# Estructura del Frontend

El proyecto Frontend se encuentra organizado como un monorepositorio utilizando Turborepo.

```text
Frontend/
│
├── apps/
│   └── web/
│       ├── public/
│       ├── src/
│       │   ├── app/
│       │   ├── components/
│       │   ├── features/
│       │   ├── services/
│       │   ├── store/
│       │   ├── lib/
│       │   ├── i18n/
│       │   └── middleware.ts
│       │
│       ├── Dockerfile
│       └── .env.example
│
├── packages/
│   ├── config/
│   ├── env/
│   └── ui/
│
├── scripts/
├── specs/
├── openspec/
├── docker-compose.yml
├── turbo.json
└── pnpm-workspace.yaml
```

---

# Comunicación con el Backend

Toda la comunicación entre Frontend y Backend se realiza mediante una API REST.

Cada solicitud pasa por un cliente HTTP centralizado que se encarga de:

- Adjuntar automáticamente el token JWT.
- Renovar la sesión cuando el token expira.
- Gestionar errores.
- Normalizar respuestas.
- Controlar tiempos de espera.

De esta manera todos los módulos utilizan el mismo mecanismo de comunicación y mantienen un comportamiento uniforme.

---

# Arquitectura del Backend

El Backend constituye el núcleo de la plataforma App BiT y concentra toda la lógica de negocio del sistema. Su principal responsabilidad es procesar las solicitudes realizadas por el Frontend, aplicar las reglas de negocio correspondientes, administrar la información almacenada en la base de datos y exponer una API REST segura para la comunicación entre los diferentes componentes de la aplicación.

La arquitectura fue desarrollada siguiendo un enfoque **Feature-Based**, donde cada módulo funcional posee sus propios controladores, servicios, modelos, repositorios y contratos de datos. Esta organización permite que cada funcionalidad evolucione de forma independiente y facilita el mantenimiento del proyecto.

---

## Responsabilidades del Backend

El Backend es responsable de:

- Gestionar el registro e inicio de sesión de usuarios.
- Administrar perfiles de usuario.
- Gestionar habilidades y tecnologías.
- Administrar el catálogo de cursos.
- Gestionar ofertas laborales.
- Administrar mentorías.
- Gestionar experiencias compartidas por mentores.
- Registrar check-ins de salud mental.
- Integrar Google Gemini para respuestas empáticas.
- Gestionar autenticación mediante JWT.
- Validar permisos y roles de usuario.
- Persistir toda la información en PostgreSQL.

---

# Estructura del Backend

```text
Backend/
│
├── src/
│   ├── main/
│   │   ├── java/com/appbit/
│   │   │
│   │   ├── auth/
│   │   ├── common/
│   │   ├── config/
│   │   ├── course/
│   │   ├── experience/
│   │   ├── gemini/
│   │   ├── health/
│   │   ├── job/
│   │   ├── mentorship/
│   │   ├── orientation/
│   │   ├── profile/
│   │   ├── security/
│   │   ├── skill/
│   │   └── user/
│   │
│   └── resources/
│       ├── db/
│       │   └── migration/
│       ├── application.yml
│       ├── application-dev.yml
│       └── application-prod.yml
│
├── src/test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

# Organización de los módulos

Cada módulo del Backend mantiene la misma estructura interna.

```text
Modulo/
│
├── controller/
├── dto/
├── model/
├── repository/
└── service/
```

# Principales Endpoints

Todos los endpoints de la plataforma utilizan el prefijo:

```text
/api/v1
```

Exceptuando los endpoints públicos de autenticación, el resto requiere un token JWT válido.

---

## Autenticación

Permite registrar nuevos usuarios, iniciar sesión y renovar la autenticación.

Endpoints disponibles:

- POST `/api/v1/auth/register`
- POST `/api/v1/auth/login`
- POST `/api/v1/auth/refresh`

---

## Perfil

Permite administrar la información personal del usuario.

Endpoints:

- GET `/api/v1/profile`
- PUT `/api/v1/profile`

---

## Habilidades

Recupera el catálogo de habilidades disponibles.

Endpoint:

- GET `/api/v1/skills`

---

## Cursos

Permite consultar el catálogo de cursos de formación.

Endpoint:

- GET `/api/v1/courses`

---

## Empleabilidad

Gestiona la búsqueda de oportunidades laborales compatibles con el perfil del usuario.

Endpoints:

- GET `/api/v1/jobs/matches`
- GET `/api/v1/jobs/{id}`

---

## Orientación Profesional

Genera recomendaciones personalizadas considerando habilidades, cursos y oportunidades laborales.

Endpoint principal:

- POST `/api/v1/guidance`

---

## Mentorías

Permite administrar sesiones entre mentores y estudiantes.

Principales endpoints:

- Crear sesión
- Listar sesiones
- Consultar detalle
- Reservar sesión
- Cancelar sesión
- Finalizar sesión
- Consultar mis sesiones

---

## Experiencias

Permite compartir experiencias profesionales estructuradas.

Principales endpoints:

- Crear experiencia
- Consultar experiencias
- Obtener detalle
- Actualizar experiencia
- Eliminar experiencia

---

## Salud Mental

Gestiona los registros emocionales del usuario.

Permite:

- Registrar check-ins.
- Consultar historial.
- Obtener detalle.
- Generar respuestas empáticas utilizando IA.

---

# Variables de Entorno

## Frontend

Las principales variables de configuración son:

- NEXT_PUBLIC_API_URL
- NODE_ENV

Estas permiten configurar la dirección del Backend y el entorno de ejecución.

---

## Backend

Las principales variables utilizadas son:

- SPRING_PROFILES_ACTIVE
- DB_HOST
- DB_PORT
- DB_NAME
- DB_USERNAME
- DB_PASSWORD
- CORS_ALLOWED_ORIGINS

Las credenciales sensibles nunca deben almacenarse dentro del repositorio.

---

# Ejecución del Proyecto

## Requisitos

Antes de ejecutar el proyecto es necesario contar con:

- Node.js 22 o superior
- pnpm
- Java 21
- Maven
- PostgreSQL 16
- Docker
- Docker Compose

---

# Ejecución Local del Frontend

Instalar dependencias:

```bash
pnpm install
```

Configurar variables de entorno:

```bash
cp apps/web/.env.example apps/web/.env.local
```

Iniciar el servidor:

```bash
pnpm dev
```

La aplicación estará disponible en:

```text
http://localhost:3000
```

---

# Frontend utilizando Docker

Construir y ejecutar:

```bash
docker compose up
```

o

```bash
pnpm docker:up
```

---

# Ejecución Local del Backend

Compilar el proyecto:

```bash
mvn clean compile
```

Ejecutar pruebas:

```bash
mvn test
```

Iniciar la aplicación:

```bash
mvn spring-boot:run
```

La API estará disponible en:

```text
http://localhost:8080
```

---

# Backend utilizando Docker

Construir y ejecutar:

```bash
docker compose up --build
```

---

# Documentación de la API

La API dispone de documentación automática mediante OpenAPI y Swagger.

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

Esto facilita la consulta y prueba de todos los endpoints disponibles sin necesidad de herramientas externas.

---

# Seguridad

La plataforma implementa diferentes mecanismos para garantizar la protección de la información.

Entre ellos destacan:

- Autenticación mediante JWT.
- Control de acceso basado en roles.
- Validación de datos mediante Jakarta Validation.
- Protección de endpoints privados.
- Gestión centralizada de errores.
- Configuración de CORS.

---

# Roles del Sistema

Actualmente existen dos tipos principales de usuarios.

## Mentee

Puede:

- Completar su perfil.
- Consultar cursos.
- Buscar empleos.
- Reservar mentorías.
- Consultar experiencias.
- Registrar check-ins.

## Mentor

Además de las funcionalidades anteriores, puede:

- Crear mentorías.
- Gestionar sesiones.
- Compartir experiencias.
- Administrar contenido relacionado con mentorías.

---

# Características Técnicas Destacadas

Durante el desarrollo del proyecto se implementaron diversas buenas prácticas de ingeniería de software.

Entre ellas destacan:

- Arquitectura desacoplada entre Frontend y Backend.
- API REST.
- Arquitectura modular por funcionalidades.
- Autenticación basada en JWT.
- Gestión de migraciones mediante Flyway.
- Persistencia con Spring Data JPA.
- Internacionalización del Frontend.
- Componentes reutilizables mediante un Design System propio.
- Estado global administrado con Zustand.
- Gestión de datos remotos mediante TanStack Query.
- Validación de formularios utilizando React Hook Form y Zod.
- Integración con Inteligencia Artificial mediante Google Gemini.
- Contenerización completa utilizando Docker.
- Documentación automática mediante Swagger y OpenAPI.

---

# Estado Actual del Proyecto

Actualmente App BiT cuenta con una arquitectura completamente funcional que integra Frontend, Backend, Base de Datos e Inteligencia Artificial.

Los principales módulos del sistema se encuentran implementados e integrados, permitiendo realizar un flujo completo de uso de la plataforma, desde el registro de un usuario hasta la consulta de cursos, oportunidades laborales, mentorías, experiencias y funcionalidades relacionadas con salud mental.

La arquitectura modular implementada facilita la incorporación de nuevas funcionalidades y garantiza la escalabilidad del proyecto para futuras versiones.

---

# Conclusión

App BiT representa una solución tecnológica orientada a facilitar el crecimiento profesional de personas pertenecientes a grupos subrepresentados en el sector tecnológico.

Mediante la integración de una interfaz moderna, un Backend robusto, una base de datos relacional y servicios de Inteligencia Artificial, la plataforma proporciona una experiencia integral que combina aprendizaje, empleabilidad, mentoría y bienestar emocional en una única aplicación.

El proyecto fue desarrollado aplicando buenas prácticas de arquitectura de software, separación de responsabilidades y tecnologías ampliamente utilizadas en la industria, permitiendo que la solución sea mantenible, escalable y preparada para futuras ampliaciones.
