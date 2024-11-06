# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
# Replace `target` with the directory where your JAR file is generated after building
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the port that Spring Boot runs on
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
