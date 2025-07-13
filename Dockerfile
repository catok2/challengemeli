# Etapa de construcción con Maven + JDK 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución con solo JRE
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# 👇 Instalá certificados raíz para SSL
RUN apt-get update && apt-get install -y ca-certificates

COPY --from=build /app/target/coupon-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]