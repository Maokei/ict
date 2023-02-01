ict
```
client: gradlew :client:compileJava
server: gradlew :server:compileJava
share: gradlew :share:compileJava

client: ./gradlew :client:run
server: ./gradlew :server:run -Parg=-port=21298
server: ./gradlew server:run -Parg=-port=6298,-clients=5,-max_waiting_queue=10
```