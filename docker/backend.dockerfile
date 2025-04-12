FROM ubuntu:jammy-20250404 AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY ./backend /app
WORKDIR /app

RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-jammy

EXPOSE 8080

COPY --from=build /app/target/*.jar /app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]
