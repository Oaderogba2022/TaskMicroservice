FROM openjdk:23-jdk-slim AS build

WORKDIR /app

# Copy the project files into the container
COPY mvnw /app/
COPY .mvn /app/.mvn
COPY src /app/src
COPY pom.xml /app/

# Grant execute permission and build the application using Maven
RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests

FROM openjdk:23-jdk-slim

WORKDIR /app

# Update with the correct JAR file name
COPY --from=build /app/target/TaskServiceApplication-0.0.1-SNAPSHOT.jar taskserviceapplication.jar

# Expose port 8080
EXPOSE 8080

# Set the entrypoint for the container
ENTRYPOINT ["java", "-jar", "taskserviceapplication.jar"]
