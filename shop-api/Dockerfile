FROM openjdk:11-jdk
VOLUME /tmp
COPY target/shop-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]