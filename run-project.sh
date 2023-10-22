#!/bin/bash

# shellcheck disable=SC2034

SUBDIR="./target"
IMAGE_NAME="notification-service-notificiationapp"
CONTAINER_APP="shapeup-notification-app"
DEFAULT_SLEEP_TIME=2

clear
echo "Iniciando script..."
echo ""

./mvnw clean package
sleep $DEFAULT_SLEEP_TIME
clear

echo "Verificando a existência de $SUBDIR..."
if [ -d "$SUBDIR" ]; then
    if docker-compose ps | grep -q "$CONTAINER_APP"; then
        echo "Encessando o container '$CONTAINER_APP'..."
        echo ""
    else
      echo "O container '$CONTAINER_APP' não está em execução"
    fi

    if docker images | grep -q "$IMAGE_NAME"; then
        echo "A imagem '$IMAGE_NAME' já existe. Removendo..."
        echo ""
        docker rmi "$IMAGE_NAME"
        sleep $DEFAULT_SLEEP_TIME
        clear
    else
      echo "A imagem '$IMAGE_NAME' não existe."
    fi
    docker compose up -d
    sleep $DEFAULT_SLEEP_TIME
    clear
else
    echo "O subdiretório '$SUBDIR' não existe neste diretório."
fi

echo "Script concluído."
sleep $DEFAULT_SLEEP_TIME
clear