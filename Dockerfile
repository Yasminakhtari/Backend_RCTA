# Build stage
FROM maven:3.8.2-jdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/tennis-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that Spring Boot runs on
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
