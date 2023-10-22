FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY .env .env

ENV POSTGRES_URL=jdbc:postgresql://postgres-db:5432/shapeupdev
ENV POSTGRES_USER=dev
ENV POSTGRES_PASSWORD=dev
ENV MONGO_HOST=localhost
ENV MONGO_DATABASE=shapeupdev
ENV MONGO_PORT=27017


COPY target/shape-up-0.0.1.jar app.jar
EXPOSE 7000
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "app.jar"]