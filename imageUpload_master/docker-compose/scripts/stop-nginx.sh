#!/usr/bin/env bash
. ./set-env.sh
set -e

# Remove existing containers
docker-compose stop nginx
docker-compose rm -f nginx
