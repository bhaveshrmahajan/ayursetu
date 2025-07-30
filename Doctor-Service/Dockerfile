FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn ./.mvn
COPY src ./src
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/Doctor-Service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 