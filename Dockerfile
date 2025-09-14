# --------- Etapa de construcción ---------
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar pom.xml y configuración de Maven primero (cachea dependencias)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Descargar dependencias sin compilar
RUN mvn dependency:go-offline -B

# Copiar el resto del código fuente
COPY src src

# Compilar y empaquetar (saltando tests)
RUN mvn clean package -DskipTests

# --------- Etapa final (runtime) ---------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar el jar construido desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Puerto de la aplicación
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]