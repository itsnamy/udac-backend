# Stage 1: Build the application with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the built application
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy only the jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "app.jar"]
