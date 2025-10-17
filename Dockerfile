FROM ubuntu:latest
LABEL authors="denis"

# Start with a base image containing a Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the Docker image
WORKDIR /app

# Copy the JAR file provided by mvn clean package
COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar app.jar

# Make the app accessible on port:8080 for the host machine
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]