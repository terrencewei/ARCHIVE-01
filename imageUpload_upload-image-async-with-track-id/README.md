# Image Upload Service
This project is a demo for image upload using Spring Reactive Web and Reactive Mongo
    
## Lib Versions:
* spring-boot-starter-parent: [2.1.3.RELEASE](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent/2.1.3.RELEASE)
* Spring Framework: [5.1.5](https://docs.spring.io/spring/docs/5.1.5.RELEASE/spring-framework-reference/overview.html)
* Spring WebFlux: [5.1.5](https://docs.spring.io/spring/docs/5.1.5.RELEASE/spring-framework-reference/web-reactive.html)
* Spring Boot: [2.1.3](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/)
* Reactive Mongo: [2.1.5](https://docs.spring.io/spring-data/data-mongodb/docs/2.1.5.RELEASE/reference/html/)
## Required
1) [Docker&Docker Compose](https://github.com/docker/compose/releases)
2) Port: `${server.port}`(DEFAULT: `8081`)
3) Port: `${spring.data.mongodb.custom.ports}`(DEFAULT: `27017`)

## Config
* Server port: `${server.port}`
    * DEFAULT: `8081`
* Mongo DB settings: `${spring.data.mongodb.custom}`
    * DEFAULT:
        * hosts: `localhost`
        * ports: `27017`
        * username: `test`
        * password: `123456`
        * database: `datawarehouse`
        * authenticationDatabase: `datawarehouse`
* Local disk uploaded image backup location: `${io.aaxis.imageupload.config.upload-service.backupFileLocation}`
    * DEFAULT: `/tmp/io.aaxis.imageupload/`

More details, please refer to:

    src/main/resources/application.yml
    src/main/resources/application-dev-linux.yml
    src/main/resources/application-dev-windows.yml
    
>Notice: when using docker-compose, to avoid network issue, the simple way is to set the `absolute IP` of the host machine of docker-compose to `${spring.data.mongodb.custom.hosts}`, avoid using `localhost` or `127.0.0.1`   

# Quick Start
## Installation
Install [Docker&Docker Compose](https://github.com/docker/compose/releases)
## How to running
### On Linux
1. Start mongo DB
```
sh start-mongo.sh
```
2. Start upload service
```
sh start-upload-sevice.sh
```
3. View mongo DB logs
```
sh log-mongo.sh
```
4. Stop and remove mongo DB docker-compose images
```
sh stop-mongo.sh
```
5. Delete all mongo db data files
```
sh clear-mongo-db.sh
```
6. The mongo DB data file will stored at `./mongo_db/data/db`

## APIs
### /upload
Upload images

Using postman to upload multi files:
```
POST http://localhost:8081/upload
```
Choose "Body" tag, choose "form-data", KEY="files", select multi files, then submit
### /download
Get all download images

Visit [http://localhost:8081/download](http://localhost:8081/download)

Content-Type: application/stream+json
### /track/{trackId}
Track uploaded images status

Visit [http://localhost:8081/track/{trackId}](http://localhost:8081/track/{trackId})

Content-Type: application/stream+json

# TODOs
- [X] Image input validation [@see`io.aaxis.core.assets.imageupload.validation.FilePartValidator`]
- [X] Error handling [@see`io.aaxis.core.assets.imageupload.handler.AbstractValidationHandler`]
- [X] Add more properties to image entity [@see`io.aaxis.imageupload.model.Image`]
- [X] Catch MongoDB exception(e.g. when mongo is not started, invoke API /upload) [@see`io.aaxis.imageupload.service.UploadService#addException`]
- [X] MongoDB exception fail fast [@see`io.aaxis.imageupload.service.UploadService#addException`]
- [X] Add track mechanism [@see`io.aaxis.imageupload.handler.UploadHandler#track`]
- [X] Add Gradle [@see`build.gradle`]
- [X] Add docker-compose [@see`docker-compose.yml`]
- [X] Add retry mechanism [@see`io.aaxis.core.assets.imageupload.task.RetryUploadFile2CloudSchedulingTask`]
- [ ] Test codes, refers: https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-client-testing 