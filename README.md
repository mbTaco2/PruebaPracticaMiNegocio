# Mi Negocio - Sistema de Gestión de Clientes
Proyecto técnico para Alquimiasoft

##  Objetivo
Desarrollar un servicio REST con Java 8+, Spring Boot y PostgreSQL para gestionar clientes y sus direcciones (matriz y adicionales), cumpliendo con buenas prácticas de desarrollo.

##  Tecnologías utilizadas
- **Java 8**
- **Spring Boot 2.7.18**
- **PostgreSQL** (ejecutado con Docker)
- **JPA / Hibernate**
- **JUnit 5 + Mockito** (pruebas unitarias)
- **Maven**
- **Lombok** (opcional)
- **Postman** (pruebas de API)

##  Cómo levantar la base de datos con Docker

Ejecuta este comando para iniciar PostgreSQL:

```bash
docker run --name postgres-minegocio \
  -e POSTGRES_DB=minenegocio \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d \
  postgres:14
