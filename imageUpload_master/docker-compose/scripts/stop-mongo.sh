#!/usr/bin/env bash
. ./set-env.sh
set -e

# Remove existing containers
docker-compose stop mongo_db
docker-compose rm -f mongo_db

# Remove old docker images
sh remove-docker-image.sh docker-compose_mongo_db
