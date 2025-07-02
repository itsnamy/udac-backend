# Base image with Java
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the Spring Boot project
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the built JAR file
CMD ["java", "-jar", "target/*.jar"]
