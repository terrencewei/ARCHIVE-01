#!/usr/bin/env bash
. ./set-env.sh
set -e
sh stop-app-upload-service.sh
gradle -b ../../build.gradle clean bootJar
sh build-docker-image.sh app-upload-service
docker-compose up -d app-upload-service