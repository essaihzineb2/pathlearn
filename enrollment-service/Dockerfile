# Stage 1: Build avec Gradle
FROM gradle:8.14-jdk21-alpine AS build

WORKDIR /app

# Copier les fichiers Gradle
COPY build.gradle .
COPY settings.gradle* .
COPY gradle gradle
COPY gradlew* .

# Copier le code source
COPY src ./src

# Builder l'application
RUN gradle clean build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Créer utilisateur non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copier le JAR depuis le stage de build
COPY --from=build /app/build/libs/*.jar app.jar

# Installer curl pour healthchecks
USER root
RUN apk add --no-cache curl
USER spring:spring

# Port exposé
EXPOSE 8081

# Variables d'environnement
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Point d'entrée
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]