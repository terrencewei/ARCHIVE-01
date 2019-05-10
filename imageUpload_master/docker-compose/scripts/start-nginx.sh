#!/usr/bin/env bash
. ./set-env.sh
set -e
sudo sh mkdir.sh /tmp/upload
sudo chmod -R 777 /tmp/upload
docker-compose up -d nginx