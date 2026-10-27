# Build
FROM eclipse-temurin:25-jdk AS builder
WORKDIR /build
COPY gradle/ ./gradle/
COPY src/ ./src/
COPY build.gradle.kts settings.gradle.kts gradlew ./
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew bootJar --no-daemon

# Final
FROM bellsoft/liberica-runtime-container:jre-25-musl
RUN addgroup -g 100001 app_group && \
    adduser -S -H -s /usr/sbin/nologin -G app_group -u 100001 app
USER app
WORKDIR /app
COPY --chown=app:app_group \
     --from=builder \
     build/build/libs/*-SNAPSHOT.jar /app/bot.jar
ENTRYPOINT ["java", "-jar", "/app/bot.jar"]