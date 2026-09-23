# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Production stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar from the build stage
# Note: If your artifact ID is dynamic, 'COPY --from=build /app/target/*.jar app.jar' works,
# but ensuring it only grabs the heavy executable jar is safer.
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]