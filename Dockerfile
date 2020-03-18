FROM gradle:6.0.1-jdk13 as build

WORKDIR /build
COPY . .
RUN ["gradle", "bootJar", "-Dorg.gradle.daemon=false"]

FROM openjdk:13-slim
COPY --from=build /build/server/build/libs/server.jar /server.jar

EXPOSE 4242
CMD ["java", "-jar", "/server.jar"]
