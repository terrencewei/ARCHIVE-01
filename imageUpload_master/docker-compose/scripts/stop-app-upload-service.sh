#!/usr/bin/env bash
. ./set-env.sh
set -e

# Remove existing containers
docker-compose stop app-upload-service
docker-compose rm -f app-upload-service

# Remove old docker images
sh remove-docker-image.sh app-upload-service
