FROM gradle:8.2.0-jdk17 AS build
COPY --chown=gradle:gradle src /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jre
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
