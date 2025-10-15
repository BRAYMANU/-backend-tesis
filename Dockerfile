# Imagen de trabajo: Maven + Java 21
FROM maven:3.9.11-eclipse-temurin-21

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo pom.xml y descargamos las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Exponemos el puerto (opcional, coincide con el del docker-compose)
EXPOSE 8080

# Comando de inicio para ejecutar la aplicación
ENTRYPOINT ["mvn", "spring-boot:run"]