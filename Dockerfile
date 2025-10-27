# Etapa de build
FROM gradle:8.5-jdk21 AS build
WORKDIR /home/gradle/app
COPY . .
RUN ./gradlew clean build -x test

# Etapa final (imagem leve)
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /home/gradle/app/build/libs/*.jar /app/
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar /app/*.jar --server.port=${PORT:-8080}"]
