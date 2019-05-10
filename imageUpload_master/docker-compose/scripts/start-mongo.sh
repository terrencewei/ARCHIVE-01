#!/usr/bin/env bash
. ./set-env.sh
set -e

sudo chmod -R 777 ${DC_MONGO_PATH}/data/db/

sh stop-mongo.sh
sudo chmod -R 777 ${DC_MONGO_PATH}/data/db/
docker-compose up -d mongo_db