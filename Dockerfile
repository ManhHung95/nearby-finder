FROM eclipse-temurin:11-jdk-alpine

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn .mvn
COPY mvnw mvnw.cmd pom.xml ./

# Copy source code
COPY src src

# Set JAVA_HOME and build the application
ENV JAVA_HOME=/opt/java/openjdk
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/nearby-places-0.0.1-SNAPSHOT.jar"]