FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar app.jar
# Railway usa volúmenes automáticamente, no necesitas VOLUME
ENTRYPOINT ["java","-jar","/app.jar"]