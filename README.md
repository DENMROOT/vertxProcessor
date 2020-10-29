## Vertx clustered example.

### 1. Build.

```#!bash
$ mvn clean install
```

### 2. Run Sender part.
```#!bash
$ java -jar sender/target/clusteredSender.jar -cluster
```
 
 ### 3. Run Receiver part.
```#!bash
$ java -jar receiver/target/clusteredReceiver.jar -cluster
```

 ### 4. Call Sender REST endpoint.
```#!bash
$ curl --request POST "localhost:8080/sendForAll/:helloWorld"
```


