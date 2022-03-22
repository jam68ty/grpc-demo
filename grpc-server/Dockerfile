FROM openjdk:11
EXPOSE 8080
ADD target/grpc-server-0.0.1-SNAPSHOT.jar grpc-server-0.0.1-SNAPSHOT.jar
# Run the jar file
ENTRYPOINT ["java", "-jar", "/grpc-server-0.0.1-SNAPSHOT.jar"]