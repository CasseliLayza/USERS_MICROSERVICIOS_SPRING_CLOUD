FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ADD ./target/USERS-MICROSERVICIOS-SPRING-CLOUD-0.0.1-SNAPSHOT.jar msvc_users.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "msvc_users.jar"]