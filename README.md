# grpc-demo
## startup
```
cd grpc-server
mvn clean install
mvn spring-boot:run
```
## call api
```
grpcurl --plaintext localhost:9090 list
grpcurl -plaintext -d {\"name\":\"a~\"} localhost:9099 com.shoalter.grpc.MyService/SayHello
```
