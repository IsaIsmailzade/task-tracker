FROM gradle:jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]