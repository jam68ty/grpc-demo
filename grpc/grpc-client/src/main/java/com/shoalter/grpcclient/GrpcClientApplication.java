package com.shoalter.grpcclient;

import com.shoalter.grpcclient.service.GrpcStockClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Time;

@SpringBootApplication
public class GrpcClientApplication {

    @Autowired
    private GrpcStockClientService service;

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @PostConstruct
    public void doStuff() throws InterruptedException {
        // the service will have been initialized and wired into the field by now
        service.unaryGetStock();
        Thread.sleep(200);
        service.serverSideStreamingGetStock();
        Thread.sleep(200);
        service.clientSideStreamingGetStatisticsOfStocks();
        Thread.sleep(200);
        service.bidirectionalStreamingGetListOfStocks();
    }
}
