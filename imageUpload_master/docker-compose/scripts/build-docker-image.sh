#!/usr/bin/env bash
. ./set-env.sh
docker build \
-t $1 \
-f ${PWD}/../../src/main/docker/DockerFile \
${PWD}/../../build/libs/;