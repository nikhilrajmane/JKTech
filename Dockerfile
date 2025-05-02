# Use OpenJDK 8 base image
FROM openjdk:8-jdk-alpine

# Set application JAR filename (replace if different)
ARG JAR_FILE=target/JKTech-0.0.1-SNAPSHOT.jar

# Copy the JAR to the container
COPY ${JAR_FILE} app.jar

# Expose port (default Spring Boot port)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","/app.jar"]