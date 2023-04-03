#!/bin/bash

docker image rmi shapeup/shapeup-api -f

docker build -t shapeup-api:latest ..

docker tag shapeup-api:latest shapeup/shapeup-api:latest

echo docker images ls | grep shapeup-api

docker login

docker push shapeup/shapeup-api:latest

