## Vertx clustered example.

### 1. Build java artifacts and Docker images.

```#!bash
$ mvn clean install
$ mvn jib:dockerBuild
```

### 3. Run Docker environment.
```#!bash
$ cd _Docker
$ docker-compose up
```

### 4. Call Sender REST endpoint.
```#!bash
$ curl --request POST "localhost:8080/sendForAll/:helloWorld"
```


