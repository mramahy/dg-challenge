# Stage 1: build the project
FROM gradle:7-jdk17 AS builder
ARG PROJECT_DIR=/app
WORKDIR $PROJECT_DIR
COPY . $PROJECT_DIR
RUN gradle clean build

# Stage 2: Run the project
FROM gradle:7-jdk17
ARG PROJECT_DIR=/app
WORKDIR $PROJECT_DIR
COPY --from=builder $PROJECT_DIR/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]