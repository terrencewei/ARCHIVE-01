#!/bin/bash
mongo <<EOF
use admin;
db.createUser({user:"admin",pwd:"admin",roles:[{role:'root',db:'admin'}]});
db.auth("admin","admin");
use datawarehouse;
db.createUser({ user: 'test', pwd: '123456', roles: [{ role: "readWrite", db: "datawarehouse" }] });
db.auth("test","123456");
db.createCollection("image_collection");
EOF
# currently, do not import the init data json
#mongoimport --db datawarehouse --collection image_collection --file $WORKSPACE/image_collection1.json
#mongoimport --db datawarehouse --collection image_collection --file $WORKSPACE/image_collection2.json