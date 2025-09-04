# Mi Negocio - Sistema de Gestión de Clientes

## Tecnologías
- Java 8+
- Spring Boot 2.7.18
- PostgreSQL
- JUnit 5, Mockito
- Maven

## Configuración

1. Clonar el repositorio
2. Instalar PostgreSQL (recomendado con Docker):
   ```bash
   docker run --name postgres-minegocio -e POSTGRES_DB=minegocio -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:14
