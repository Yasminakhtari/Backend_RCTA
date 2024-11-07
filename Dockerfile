# Stage 1: Build the application with Maven
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the entire project into the Docker image
COPY . .

# Build the project with Maven, skipping tests for faster build times
RUN mvn clean package -DskipTests

# Stage 2: Run the application using OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/Tennis-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8082

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
