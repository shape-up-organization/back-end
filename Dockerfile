FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY .env .env
COPY target/shape-up-0.0.1.jar app.jar
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ARG AWS_REGION
ARG AWS_BUCKET_NAME

ENV POSTGRES_URL=jdbc:postgresql://postgres-db:5432/shapeupdev \
    POSTGRES_USER=dev \
    POSTGRES_PASSWORD=dev \
    MONGO_DATABASE=shapeupdev \
    MONGO_HOST=localhost \
    MONGO_PORT=27017 \
    AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} \
    AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} \
    AWS_REGION=${AWS_REGION} \
    AWS_BUCKET_NAME=${AWS_BUCKET_NAME}

EXPOSE 7000
ENTRYPOINT ["java", "-jar", "app.jar"]
