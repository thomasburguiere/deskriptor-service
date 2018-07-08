## MongoDB setup for local development 

```bash

### Installation
brew install mongodb@3.4 #mac
mongod #start server
mongo #start client

### Configuration in mongo client

# creating db
use ef-db
db.order.save({id:"1"})

# setting up user
db.createUser({
    user: "local-ef",
    pwd: "local-ef",
    roles: [{
        role: "readWrite",
        db: "ef-db"
    }]
})
```