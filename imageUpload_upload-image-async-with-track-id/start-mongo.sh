#!/usr/bin/env bash
set -e

sudo chmod -R 777 ./mongo_db/data/db/

export DOCKER_IP=$(docker-machine ip $(docker-machine active))
DOCKER_IP=${DOCKER_IP:-0.0.0.0}
sh stop-mongo.sh
sleep 1
sudo chmod -R 777 ./mongo_db/data/db/
sleep 1
docker-compose up -d mongo_db