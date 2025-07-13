# ==========================
# 🏗️ Etapa de construcción
# ==========================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Construir el proyecto y generar el JAR
RUN mvn clean package -DskipTests

# ==========================
# 🚀 Etapa de ejecución final
# ==========================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# ✅ Instalar certificados y utilidades necesarias para MongoDB Atlas (SRV y TLS)
RUN apt-get update && \
    apt-get install -y ca-certificates netbase dnsutils && \
    update-ca-certificates

# Copiar el JAR generado desde la etapa anterior
COPY --from=build /app/target/coupon-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
