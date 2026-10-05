# Etapa 1: Compilación del proyecto con Maven y JDK 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar descriptor de dependencias y código fuente
COPY pom.xml .
COPY src ./src

# Compilar y empaquetar la aplicación Spring Boot
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final de ejecución ligera con JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiar el artefacto generado
COPY --from=build /app/target/*.jar app.jar

ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
