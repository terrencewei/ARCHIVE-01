#!/usr/bin/env bash
set -e
# function definition
removeDockerImage()
{
if $(docker images | grep -q $1)
then
    docker images|grep $1|awk '{print $3 }'| xargs docker rmi
fi
}

# Remove existing containers
docker-compose stop
docker-compose rm -f
sleep 2

# Remove old docker images
removeDockerImage springbootstudy_discovery-service
removeDockerImage springbootstudy_catalog-service
removeDockerImage springbootstudy_inventory-service
removeDockerImage springbootstudy_price-service
removeDockerImage springbootstudy_order-service
removeDockerImage springbootstudy_user-service
sleep 2
