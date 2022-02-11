package com.shoalter.grcpserver.impl;

import com.shoalter.grpc.HelloReply;
import com.shoalter.grpc.HelloRequest;
import com.shoalter.grpc.MyServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
public class MyServiceImpl extends MyServiceGrpc.MyServiceImplBase
{

	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver)
	{
		HelloReply reply = HelloReply.newBuilder()
				.setMessage("Hello! " + request.getName())
				.build();
		System.out.println("Hello! " + request.getName());
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

}
