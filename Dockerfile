FROM openjdk:17-jdk-slim
EXPOSE 7000
VOLUME /tmp
COPY target/shape-up-0.0.1.jar shapeup.jar
ENTRYPOINT ["java", "-jar", "shapeup.jar"]