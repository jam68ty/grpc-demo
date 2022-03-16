package com.shoalter.grcpclient.service;

import com.shoalter.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.AbstractBlockingStub;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;


@Service
public class GrpcClientService {
    @GrpcClient("server-stream-server-side")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    @GrpcClient("server-stream-server-side")
    private MyServiceGrpc.MyServiceStub noneBlockingStub;

    Logger logger = LoggerFactory.getLogger(GrpcClientService.class);


    public String receiveGreeting(String name) {

        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        HelloReply reply;
        try {
            reply = myServiceBlockingStub.sayHello(request);
            System.out.println(reply.getMessage());
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed:" + e.getMessage());
        }
        return myServiceBlockingStub.sayHello(request).getMessage();
    }

    //    Client RPC with Server-Side Streaming
    public void serverSideStreamingGetStock() {
        Stock request = Stock.newBuilder()
                .setDescription("all")
                .build();
        Iterator<StockQuote> stockQuotes;
        try {
            logger.info(request.getDescription());
            stockQuotes = myServiceBlockingStub.serverSideStreamingGetStock(request);
            for (int i = 1; stockQuotes.hasNext(); i++) {
                StockQuote stockQuote = stockQuotes.next();
                String price = String.valueOf(stockQuote.getPrice());
                String offer = String.valueOf(stockQuote.getOfferNumber());
                logger.info("RESPONSE - Product #" + i + " : $" + price + " /" + offer);
            }
        } catch (StatusRuntimeException e) {
            logger.error("RPC failed: {0}", e.getStatus());
        }
    }

    //    Client RPC with Client-Side Streaming
    public void clientSideStreamingGetStatisticsOfStocks() throws InterruptedException {

        StreamObserver<StockQuote> responseObserver = new StreamObserver<StockQuote>() {
            @Override
            public void onNext(StockQuote summary) {
                String d = String.valueOf(summary.getOfferNumber());
                logger.info("RESPONSE - offerNumber: " + d);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("gRPC failed: {0}", t);
            }

            @Override
            public void onCompleted() {
                logger.info("Finished clientSideStreamingGetStatisticsOfStocks");
            }
        };
        StreamObserver<Stock> requestObserver = noneBlockingStub.clientSideStreamingGetStatisticsOfStocks(responseObserver);

        try {
            requestObserver.onNext(Stock.newBuilder().build());
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }
        requestObserver.onCompleted();
    }

    public void bidirectionalStreamingGetListsStockQuotes() throws InterruptedException {
        StreamObserver<StockQuote> responseObserver = new StreamObserver<StockQuote>() {
            @Override
            public void onNext(StockQuote stockQuote) {
                logger.info("RESPONSE price#{0} : {1}, description:{2}", stockQuote.getPrice(), stockQuote.getOfferNumber(), stockQuote.getDescription());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                logger.info("Finished bidirectionalStreamingGetListsStockQuotes");
            }

        };

        StreamObserver<Stock> requestObserver = noneBlockingStub.bidirectionalStreamingGetListsStockQuotes(responseObserver);
        try {
            requestObserver.onNext(Stock.newBuilder().build());
        }catch (RuntimeException e){
            requestObserver.onError(e);
            throw e;
        }
        requestObserver.onCompleted();

    }
}
