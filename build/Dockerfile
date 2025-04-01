FROM openjdk:21-slim

WORKDIR /app

COPY target/server.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]