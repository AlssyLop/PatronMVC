# PatronMVC-Web

## Descripcion
Aplicacion web basada en el patron Modelo-Vista-Controlador (MVC) para la gestion (CRUD) de personas, con interfaz web renderizada en servidor y endpoints REST.

## Version
1.0.0

## Stack Tecnologico
- Java 17
- Spring Boot 3.3.4 (Spring MVC, Spring Data JPA, Spring Validation)
- Thymeleaf
- MySQL 8.0
- Apache Maven
- Docker y Docker Compose

## Despliegue y Ejecucion

### Opcion 1: Docker Compose

Levantar base de datos y aplicacion:

```bash
docker compose up --build -d
```

- Aplicacion: http://localhost:8081
- Base de datos: localhost:3307 (BD: `codejavu`, Usuario: `root`, Contrasena: `root`)

Detener los servicios:

```bash
docker compose down
```

### Opcion 2: Local con Maven

Requiere Java 17 y una instancia de MySQL en ejecucion (puerto 3306 por defecto).

1. Variables de entorno (opcionales si se usa la configuracion por defecto):
   - `DB_HOST`: localhost
   - `DB_PORT`: 3306
   - `DB_NAME`: codejavu
   - `DB_USER`: root
   - `DB_PASSWORD`: root

2. Iniciar la aplicacion:

```bash
mvn spring-boot:run
```

- Aplicacion: http://localhost:8080
