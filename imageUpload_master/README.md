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
4) Port: `docker-compose/docker-compose.yml:nginx`(DEFAULT: `8082`)

## Config
* Server port: `${server.port}`, default is `8081`
* Mongo DB settings: `${spring.data.mongodb.custom}`
    * hosts: `mongo_db`(by default, using docker-compose service name as mongo db host to connect mongo db via docker-compose default network)
    * ports: `27017`
    * username: `test`
    * password: `123456`
    * database: `datawarehouse`
    * authenticationDatabase: `datawarehouse` 
* Local disk uploaded image backup location: `${io.aaxis.imageupload.config.upload-service.backupFileLocation}`, default is `/tmp/upload`
* Nginx document root: the same as `Local disk uploaded image backup location`

More details, please refer to:

    src/main/resources/application.yml
    src/main/resources/application-dev-linux.yml
    src/main/resources/application-dev-windows.yml
    
# Quick Start
## Installation
Install [Docker&Docker Compose](https://github.com/docker/compose/releases)
## How to running
### On Linux
1. Change directory to `./docker-compose/scripts`
```
cd ./docker-compose/scripts
```

2. Start all(MongoDB, Nginx, App)
```
sh ./start-all.sh
```

3. View mongo DB logs
```
sh ./log-mongo.sh
```

4. Stop all and remove all docker-compose images
```
sh ./stop-all.sh
```

5. Delete all data files(MongoDB)
```
sh ./clear-all.sh
```

The mongo DB data file will stored at `./docker-compose/mongo_db/data/db`

## APIs
### /v1/assets/upload
Upload images

```
POST /v1/assets/upload HTTP/1.1
Host: localhost:8081
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
cache-control: no-cache

Content-Disposition: form-data; name="accountId"

mock accountId

Content-Disposition: form-data; name="entityId"

mock entityId

Content-Disposition: form-data; name="entityType"

Product

Content-Disposition: form-data; name="files"; filename="/home/terrence/Downloads/RCN_practice_imageUpload/upload-test-01.png



Content-Disposition: form-data; name="files"; filename="/home/terrence/Downloads/RCN_practice_imageUpload/upload-test-02.png



Content-Disposition: form-data; name="files"; filename="/home/terrence/Downloads/RCN_practice_imageUpload/upload-test-03.png



Content-Disposition: form-data; name="mediaType"

Image

Content-Disposition: form-data; name="description"

mock description1,mock description2
```

View uploaded images:

Visit `http://localhost:8082/<the path of upload API response>`
### /v1/assets/search
Search uploaded images
```
POST /v1/assets/search HTTP/1.1
Host: localhost:8081
Content-Type: application/json
cache-control: no-cache
{
    "accountId":"xx",
    "entityId":"xx",
    "entityType":"xx",
    "MediaType":"xx",
    "current":"1",
    "pageSize":"99"
}
```

# TODOs
- [X] Image input validation [@see`io.aaxis.core.assets.imageupload.validation.FilePartValidator`]
- [X] Error handling [@see`io.aaxis.core.assets.imageupload.handler.AbstractValidationHandler`]
- [X] Add more properties to image entity [@see`io.aaxis.imageupload.model.Image`]
- [X] Catch MongoDB exception(e.g. when mongo is not started, invoke API /upload) [@see`io.aaxis.imageupload.service.UploadService#addException`]
- [X] MongoDB exception fail fast [@see`io.aaxis.imageupload.service.UploadService#addException`]
- [X] Add track mechanism [@see`io.aaxis.imageupload.handler.UploadHandler#track`]
- [X] Add Gradle [@see`build.gradle`]
- [X] Add docker-compose [@see`.docker-compose/docker-compose.yml`]
- [X] Add retry mechanism [@see`io.aaxis.core.assets.imageupload.task.RetryUploadFile2CloudSchedulingTask`]
- [X] Add Nginx [@see`.docker-compose/docker-compose.yml`]
- [ ] Test codes, refers: https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-client-testing 