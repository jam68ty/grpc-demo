package com.shoalter.grcpserver.impl;

import com.shoalter.grcpserver.dao.StockDao;
import com.shoalter.grcpserver.entity.Stock;
import com.shoalter.grpc.HelloReply;
import com.shoalter.grpc.HelloRequest;
import com.shoalter.grpc.MyServiceGrpc;
import com.shoalter.grpc.StockQuote;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@GrpcService
public class MyServiceImpl extends MyServiceGrpc.MyServiceImplBase {
    @Autowired
    private StockDao stockDao;

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello! " + request.getName())
                .build();
        System.out.println("Hello! " + request.getName());
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void serverSideStreamingGetStock(com.shoalter.grpc.Stock request,
                                            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockQuote> responseObserver) {
        List<Stock> stocks = stockDao.findStockByDescription(request.getDescription());
        for (com.shoalter.grcpserver.entity.Stock stock : stocks) {
            StockQuote stockQuote = StockQuote.newBuilder()
                    .setPrice(stock.getPrice())
                    .setOfferNumber(stock.getOffer_number())
                    .setDescription(stock.getDescription()).build();
            responseObserver.onNext(stockQuote);
        }
        responseObserver.onCompleted();
    }

    @Override
    public io.grpc.stub.StreamObserver<com.shoalter.grpc.Stock> clientSideStreamingGetStatisticsOfStocks(
            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockQuote> responseObserver) {

        return new StreamObserver<com.shoalter.grpc.Stock>() {

            int count;
            StringBuilder sb = new StringBuilder();

            @Override
            public void onNext(com.shoalter.grpc.Stock stock) {
                count++;
                List<Stock> stocks = stockDao.findStockByDescription(stock.getDescription());
                for (Stock s : stocks) {
                    sb.append(s.getProduct_name());
                }

            }

            @Override
            public void onError(Throwable t) {
                System.out.println("something wrong!");
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(StockQuote.newBuilder()
                        .setDescription(sb.toString()).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public io.grpc.stub.StreamObserver<com.shoalter.grpc.Stock> bidirectionalStreamingGetListsStockQuotes(
            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockQuote> responseObserver) {
        return new StreamObserver<com.shoalter.grpc.Stock>() {
            @Override
            public void onNext(com.shoalter.grpc.Stock stock) {
                List<Stock> stockList = stockDao.findStockByDescription(stock.getDescription());

                for (int i = 0; i < stockList.size(); i++) {
                    StockQuote stockQuote = StockQuote.newBuilder()
                            .setPrice(stockList.get(i).getPrice())
                            .setOfferNumber(stockList.get(i).getOffer_number())
                            .setDescription(stockList.get(i).getDescription()).build();
                    responseObserver.onNext(stockQuote);
                }

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

}
