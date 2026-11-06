# ===================================================================
# Multi-Stage Dockerfile for Aplikasi Mini Bank Tazkia
# ===================================================================

# Stage 1: Build Application
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build

# Pre-fetch dependencies (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy application source code and package executable JAR
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Lightweight Production Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Run as non-root user for security best practices
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy built JAR from builder stage
COPY --from=builder /build/target/*.jar app.jar
RUN chown -R appuser:appgroup /app

USER appuser

# Default Environment Variables (Overridable via docker-compose / env)
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"

EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/login || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

