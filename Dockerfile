# Compilation step
FROM eclipse-temurin:21-jdk AS build

# Setup working directory
WORKDIR /app

# Copy build.gradle and settings.gradle files
COPY build.gradle settings.gradle /app/

# Copy gradle files configs
COPY gradle /app/gradle

# Build project
RUN ./gradlew build --no-daemon -x test || return 0

# Copy files
COPY . /app

# Build .jar
RUN ./gradlew clean bootJar --no-daemon

# Execution step
FROM eclipse-temurin:21-jre

# Setup working directory
WORKDIR /app

# Copy jar file to working directory
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Execute app
ENTRYPOINT ["java", "-jar", "app.jar"]