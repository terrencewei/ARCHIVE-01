#!/usr/bin/env bash
sh stop-all.sh
sh start-mongo.sh
sh start-nginx.sh
sh start-app-upload-service.sh