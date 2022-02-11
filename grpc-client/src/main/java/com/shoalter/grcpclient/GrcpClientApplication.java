package com.shoalter.grcpclient;

import com.shoalter.grcpclient.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class GrcpClientApplication {

	@Autowired
	private GrpcClientService grpcClientService;

	public static void main(String[] args) {

		SpringApplication.run(GrcpClientApplication.class, args);
	}

//	@PostConstruct
//	public void doStuff() {
//		// the service will have been initialized and wired into the field by now
//		grpcClientService.receiveGreeting("hi?");
//	}
}
