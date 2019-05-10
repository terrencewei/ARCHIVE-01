#!/usr/bin/env bash
. ./set-env.sh
if $(docker images | grep -q $1)
then
    docker images|grep $1|awk '{print $3 }'| xargs docker rmi
fi