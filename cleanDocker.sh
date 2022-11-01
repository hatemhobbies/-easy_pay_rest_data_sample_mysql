#!/bin/bash

set -x

docker rm -f $(docker ps -a -q)
docker rmi capstone-project/wallet:2.0
docker network rm easypaynet
docker volume rm $(docker volume ls -q)