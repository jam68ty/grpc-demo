package com.shoalter.grcpclient.controller;

import brave.sampler.Sampler;
import com.shoalter.grcpclient.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GrpcClientController
{
	@Autowired
	private GrpcClientService grpcClientService;

	@RequestMapping("/")
	public String printMessage(@RequestParam String name){
		return grpcClientService.receiveGreeting(name);
	}

	@Bean
	public Sampler alwaysSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
