FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/shape-up-0.0.1.jar app.jar
ENV POSGRES_URL=jdbc:postgresql://postgres-db:5432/shapeupdev \
    POSTGRES_USER=dev \
    POSTGRES_PASSWORD=dev
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "app.jar"]