# ============================================
# STAGE 1: Build the application
# ============================================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# copy pom.xml first (for layer caching)
COPY pom.xml .

# download dependencies (this layer is cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# copy source code
COPY src ./src

# build the application (skip tests for faster build)
RUN mvn clean package -DskipTests -B

# ============================================
# STAGE 2: Run the application
# ============================================
FROM eclipse-temurin:17-jre-alpine

# install curl for healthcheck
RUN apk add --no-cache curl

WORKDIR /app

# create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring

# copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# create uploads directory and give permissions
RUN mkdir -p /app/uploads && chown -R spring:spring /app

# switch to non-root user
USER spring

# expose application port
EXPOSE 8080

# JVM optimization flags
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:+UseContainerSupport"

# healthcheck via actuator
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]