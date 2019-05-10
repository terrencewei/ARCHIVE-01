#!/usr/bin/env bash
. ./set-env.sh
sh stop-mongo.sh
sudo rm -rf $DC_MONGO_PATH/data/db/*