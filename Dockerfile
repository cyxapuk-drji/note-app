FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar /app/noteapp.jar
ENTRYPOINT ["java", "-jar", "noteapp.jar"]
