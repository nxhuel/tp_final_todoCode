FROM amazoncorretto:22.0.2-alpine-jdk

COPY target/tpFinal-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]