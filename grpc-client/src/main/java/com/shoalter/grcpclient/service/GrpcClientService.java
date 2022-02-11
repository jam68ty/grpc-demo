package com.shoalter.grcpclient.service;

import com.shoalter.grpc.HelloReply;
import com.shoalter.grpc.HelloRequest;
import com.shoalter.grpc.MyServiceGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;


@Service
public class GrpcClientService
{
	@GrpcClient("server-stream-server-side")
	private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;


	public String receiveGreeting(String name){

		HelloRequest request = HelloRequest.newBuilder()
				.setName(name)
				.build();
		HelloReply reply;
		try
		{
			reply = myServiceBlockingStub.sayHello(request);
			System.out.println(reply.getMessage());
		}catch (StatusRuntimeException e){
			System.out.println("RPC failed:"  + e.getMessage());
		}
		return myServiceBlockingStub.sayHello(request).getMessage();
	}
}
