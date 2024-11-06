# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container (replace with the actual name of your JAR file)
COPY target/Tennis-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that Spring Boot runs on
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
