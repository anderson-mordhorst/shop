FROM openjdk:11-jdk
VOLUME /tmp
COPY target/shop-report-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]