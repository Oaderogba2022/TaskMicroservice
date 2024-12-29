FROM openjdk:23-jdk

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "taskserviceapplication.jar"]
