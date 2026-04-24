# Fase 1: Compilación (Build)
# Usamos Maven con Java 21 para que coincida con el pom.xml
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Compilamos saltando los tests para que el despliegue sea más rápido
RUN mvn clean package -DskipTests

# Fase 2: Ejecución (Runtime)
# Usamos una imagen ligera de Java 21 para que el servidor vuele
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto 8080 que es el estándar de Spring Boot
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]