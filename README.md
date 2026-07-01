# Capstone Project - Backend

REST API built with Spring Boot for managing a scrapyard/auto parts business: users, spare parts, and reservations.

## Technologies

- Java 17
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Security + JWT (authentication and authorization)
- MySQL
- MapStruct (DTO to entity mapping)
- Swagger / OpenAPI (API documentation)
- Docker Compose

## Features

- User registration and authentication using JWT
- Spare parts management (create, read, update, delete)
- Parts reservation system
- Role-based access control

## Prerequisites

- JDK 17 or higher
- Maven 3.8+
- Docker and Docker Compose (to run the database)

## Installation and setup

1. Clone the repository:
   ```bash
   git clone https://github.com/EricCaparroso/capstone-project-backend.git
   cd capstone-project-backend
   ```

2. Start the database with Docker:
   ```bash
   docker compose up -d
   ```

3. Configure the environment variables (or check `application.properties` / `application.yml`) with your database connection details.

4. Build and run the project:
   ```bash
   mvn spring-boot:run
   ```

5. The API will be available at `http://localhost:8080`

## API documentation

Once the project is running, interactive documentation (Swagger UI) is available at:

```
http://localhost:8080/swagger-ui.html
```

## Project structure

```
src/main/java/...
├── config/         # Security, JWT, CORS configuration, etc.
├── controller/     # REST controllers
├── dto/            # Data transfer objects
├── entity/         # JPA entities
├── repository/     # Spring Data JPA repositories
├── service/        # Business logic
└── mapper/         # Mappers (MapStruct)
```

## Related project

This backend is designed to work together with the frontend of the project:
[capstone-project-frontend](https://github.com/EricCaparroso/capstone-project-frontend)

## Author

**Eric Caparroso**
[GitHub](https://github.com/EricCaparroso)
