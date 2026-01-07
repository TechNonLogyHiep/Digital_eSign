FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/digital_esign-0.0.1-SNAPSHOT.jar /app/digital_esign-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/digital_esign-0.0.1-SNAPSHOT.jar"]