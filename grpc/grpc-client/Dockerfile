FROM openjdk:11
EXPOSE 8081 5005
ADD target/grpc-client-0.0.1-SNAPSHOT.jar grpc-client-0.0.1-SNAPSHOT.jar
# Run the jar file
ENTRYPOINT ["java", "-jar", "/grpc-client-0.0.1-SNAPSHOT.jar"]