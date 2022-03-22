package com.shoalter.grpcserver.service.impl;

import com.shoalter.grpc.*;
import com.shoalter.grpcserver.dao.StockDao;
import com.shoalter.grpcserver.enitity.Stock;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

@GrpcService
@Transactional
public class StockServiceImpl extends StockServiceGrpc.StockServiceImplBase {

    @Autowired
    private StockDao stockDao;

    Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    public void unaryGetStock(com.shoalter.grpc.StockRequest request,
                              io.grpc.stub.StreamObserver<com.shoalter.grpc.StockResponse> responseObserver) {
        logger.info("[unaryGetStock] - client send a request");
        logger.info("REQUEST - id: {}", request.getId());
        Stock stock = stockDao.getById(request.getId());
        StockResponse response = StockResponse.newBuilder()
                .setId(stock.getId())
                .setProductName(stock.getProductName())
                .setPrice(stock.getPrice())
                .setOfferNumber(stock.getOfferNumber())
                .build();
        logger.info("[unaryGetStock] - Finished!");
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void serverSideStreamingGetStock(com.shoalter.grpc.StockProductNameRequest request,
                                            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockResponse> responseObserver) {
        logger.info("[serverSideStreamingGetStock] - client send a request");
        logger.info("REQUEST - prefix: {}", request.getPrefix());
        List<Stock> stocks = stockDao.findStockByProductNamePrefix(request.getPrefix());
        for (Stock stock : stocks) {
            StockResponse response = StockResponse.newBuilder()
                    .setId(stock.getId())
                    .setProductName(stock.getProductName())
                    .setPrice(stock.getPrice())
                    .setOfferNumber(stock.getOfferNumber())
                    .build();
            responseObserver.onNext(response);
        }
        logger.info("[serverSideStreamingGetStock] - Finished!");
        responseObserver.onCompleted();

    }

    @Override
    public io.grpc.stub.StreamObserver<com.shoalter.grpc.StockRequest> clientSideStreamingGetStatisticsOfStocks(
            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockStatisticsResponse> responseObserver) {
        logger.info("[clientSideStreamingGetStatisticsOfStocks] - client send streaming requests ... ");
        return new StreamObserver<StockRequest>() {

            int totalValue = 0;

            @Override
            public void onNext(StockRequest request) {
                Stock stock = stockDao.getById(request.getId());
                int value = (int) (stock.getOfferNumber() * stock.getPrice());
                totalValue += value;
                logger.info("REQUEST - id: {} , value = {}", String.valueOf(stock.getId()), String.valueOf(value));
            }

            @Override
            public void onError(Throwable t) {
                logger.error("[ERROR] Stream Error!");
            }

            @Override
            public void onCompleted() {
                logger.info("[clientSideStreamingGetStatisticsOfStocks] - Finished!");
                responseObserver.onNext(StockStatisticsResponse.newBuilder().setTotalValue(totalValue).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public io.grpc.stub.StreamObserver<com.shoalter.grpc.StockProductNameRequest> bidirectionalStreamingGetListOfStocks(
            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockResponse> responseObserver) {
        logger.info("[bidirectionalStreamingGetListOfStocks] - client send streaming requests ... ");
        return new StreamObserver<StockProductNameRequest>() {
            @Override
            public void onNext(StockProductNameRequest request) {
                logger.info("REQUEST - prefix: {}", request.getPrefix());
                List<Stock> stocks = stockDao.findStockByProductNamePrefix(request.getPrefix());
                for (Stock s : stocks) {
                    StockResponse response = StockResponse.newBuilder()
                            .setId(s.getId())
                            .setPrice(s.getPrice())
                            .setProductName(s.getProductName())
                            .setOfferNumber(s.getOfferNumber())
                            .build();
                    responseObserver.onNext(response);
                }
            }

            @Override
            public void onError(Throwable t) {
                logger.error("[ERROR] Stream Error!");
            }

            @Override
            public void onCompleted() {
                logger.info("[bidirectionalStreamingGetListOfStocks] - Finished!");
                responseObserver.onCompleted();
            }
        };
    }

}
