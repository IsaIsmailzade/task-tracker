FROM gradle:jdk17 AS bootjar
WORKDIR /app
COPY . .
RUN ./gradlew clean bootJar -x test

FROM openjdk:17-slim
WORKDIR /app
COPY --from=bootjar /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
