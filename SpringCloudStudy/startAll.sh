#!/usr/bin/env bash
set -e
# config
export EUREKA_SERVER_IP=172.17.118.200
export EUREKA_SERVER_PORT1=8081
export EUREKA_SERVER_PORT2=8082

# function definition
removeDockerImage()
{
if $(docker images | grep -q $1)
then
    docker images|grep $1|awk '{print $3 }'| xargs docker rmi
fi
}
buildDockerImage()
{
docker build \
-t $1 \
-f ${PWD}/$2/src/main/docker/DockerFile \
${PWD}/$2/build/libs/;
}

# Export the active docker machine IP
export DOCKER_IP=$(docker-machine ip $(docker-machine active))

# docker-machine doesn't exist in Linux, assign default ip if it's not set
DOCKER_IP=${DOCKER_IP:-0.0.0.0}

# Remove existing containers
docker-compose stop
docker-compose rm -f
sleep 2

# Remove old docker images
removeDockerImage springbootstudy_discovery-service
removeDockerImage springbootstudy_user-service
removeDockerImage springbootstudy_order-service
sleep 2

# Build the project and docker images
gradle clean bootJar;

# Build new docker images
buildDockerImage springbootstudy_discovery-service discovery-service
buildDockerImage springbootstudy_user-service user-service
buildDockerImage springbootstudy_order-service order-service
sleep 2
# Start the discovery service next and wait
docker-compose up -d discovery-service-1
docker-compose up -d discovery-service-2

while [ -z ${DISCOVERY_SERVICE_READY} ]; do
  echo "Waiting for discovery service..."
  if [ "$(curl --silent $EUREKA_SERVER_IP:$EUREKA_SERVER_PORT1/actuator/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      DISCOVERY_SERVICE_READY=true;
  fi
  sleep 2
done

while [ -z ${DISCOVERY_SERVICE_READY} ]; do
  echo "Waiting for discovery service..."
  if [ "$(curl --silent $EUREKA_SERVER_IP:$EUREKA_SERVER_PORT2/actuator/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      DISCOVERY_SERVICE_READY=true;
  fi
  sleep 2
done

# Start the other service containers
docker-compose up -d user-service
docker-compose up -d order-service-1
docker-compose up -d order-service-2
