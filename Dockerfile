FROM openjdk:8-jdk-alpine
COPY build/libs/*.jar docker_app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/docker_app.jar"]
