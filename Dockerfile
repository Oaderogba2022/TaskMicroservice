FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/TaskServiceApplication-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "TaskServiceApplication-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]
