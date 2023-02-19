FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/shape-up-0.0.1.jar app.jar
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "app.jar"]