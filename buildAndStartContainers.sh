#!/bin/bash

set -x

mvn install -DskipTests
docker login
docker build . -t hatemhobbies/wallet:1.0
docker push  hatemhobbies/wallet:1.0

docker network rm easypaynet
docker network create easypaynet
docker run --name epmysql --network=easypaynet  -p3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:latest

MYSQL_CONTAINER_IP=`docker inspect -f \ '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' epmysql`
sleep 10
docker run --name walletservice --network=easypaynet  -p 8080:8080 -e MYSQL_HOST="${MYSQL_CONTAINER_IP## }"  hatemhobbies/wallet:1.0


