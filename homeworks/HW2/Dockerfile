FROM openjdk:8-jre-alpine
COPY build/libs/lab2-1.0-SNAPSHOT.jar .
COPY dictionary.txt .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "lab2-1.0-SNAPSHOT.jar"]