FROM openjdk:11-jdk
VOLUME /tmp
COPY target/shop-validator-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]