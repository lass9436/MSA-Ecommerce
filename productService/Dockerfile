FROM gradle:8.12.1-jdk21 AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .

RUN gradle clean build -x test

FROM amazoncorretto:21
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
