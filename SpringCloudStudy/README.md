# Description
This project is used to verify spring cloud technical stack.
# Quick Start
* Install [docker-ce](https://www.docker.com/community-edition#/download)
* Install [docker-compose](https://docs.docker.com/compose/install/)
* Change variables in `startAll.sh`
```
export EUREKA_SERVER_IP=<your server ip>
export EUREKA_SERVER_PORT=xxxx
export EUREKA_SERVER_PORT2=xxxx
```
* Change ports in `docker-compose.yml`
* Default ports are `8081~8085`
## On Linux
* Open terminal, run scripts
```
sh startAll.sh
```
## On Windows
* TODO
# Technicals&TODO List
- [X] Registry/Discovery: Eureka
- [X] Load Balance: Ribbon
- [X] API Consumer: Feign
- [ ] Circle Breaker: Hytrix
- [ ] API Gateway: Zuul
- [ ] Logging: Sleuth
- [ ] Spring Cloud Bus: Kafka
- [ ] Security: SpringSecurity
- [ ] Cloud Config: SpringCloudConfig
- [ ] Spring Data Flow: Spark
- [ ] Command Query Responsibility Segregation: TODO
 
# Versions
* Spring 5
* SpringBoot 2
* SpringCloud Finchley.RC2