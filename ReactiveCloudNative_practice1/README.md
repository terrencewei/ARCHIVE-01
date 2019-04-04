# ReactiveCloudNative_practice1
Reactive Cloud Native practice project for SpringWebFlux, MongoDB demo

## Dev required
 * Java8
 * Lombok plugin
## Install/Config MongoDB
1. Install mongo db4 using docker:
```
docker pull mongo:4
docker run -d -p 27017:27017 -v mongo_configdb:/data/configdb -v mongo_db:/data/db --name mongo mongo:4 --auth
docker exec -it mongo mongo admin
db.createUser({ user: 'admin', pwd: 'admin123', roles: [ { role: "userAdminAnyDatabase", db: "admin" } ] });
```

2. Create webflux_demo database:
```
docker exec -it mongo mongo admin
db.auth("admin","admin123");
use webflux_demo
db.createUser({ user: 'demo', pwd: '123456', roles: [{ role: "readWrite", db: "webflux_demo" }] });
```
## Test
```
gradle test
```
## Run
```
gradle bootRun
```
## Demo
### Tweet
Visit:
* [http://localhost:8081/tweets](http://localhost:8081/tweets)
* [http://localhost:8081/stream/tweets](http://localhost:8081/stream/tweets)
### Time
Visit:
* [http://localhost:8081/time](http://localhost:8081/stream/time)
* [http://localhost:8081/date](http://localhost:8081/stream/date)
* [http://localhost:8081/times](http://localhost:8081/stream/times)
### Data flows infinitely both directions on http
1. Insert one data after application startup:
```
curl -X POST \
  http://localhost:8081/events \
  -H 'Content-Type: application/stream+json' \
  -d '{"message": 1}';
```

2. Visit [http://localhost:8081/events](http://localhost:8081/stream/events)

3. Insert more data, then, new data will auto displayed at the pages of [http://localhost:8081/events](http://localhost:8081/stream/events)