package com.shoalter.grpcserver.service.impl;

import com.shoalter.grpc.StockResponse;
import com.shoalter.grpc.StockServiceGrpc;
import com.shoalter.grpcserver.dao.StockDao;
import com.shoalter.grpcserver.enitity.Stock;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@GrpcService
@Transactional
public class StockServiceImpl extends StockServiceGrpc.StockServiceImplBase {

    @Autowired
    private StockDao stockDao;

    Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    public void unaryGetStock(com.shoalter.grpc.StockRequest request,
                              io.grpc.stub.StreamObserver<com.shoalter.grpc.StockResponse> responseObserver) {

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
    public void serverSideStreamingGetStock(com.shoalter.grpc.StockRequest request,
                                            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockResponse> responseObserver) {
    }

    @Override
    public io.grpc.stub.StreamObserver<com.shoalter.grpc.StockRequest> clientSideStreamingGetStatisticsOfStocks(
            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockResponse> responseObserver) {
        return null;
    }

    @Override
    public io.grpc.stub.StreamObserver<com.shoalter.grpc.StockRequest> bidirectionalStreamingGetListOfStocks(
            io.grpc.stub.StreamObserver<com.shoalter.grpc.StockResponse> responseObserver) {
        return null;
    }

}
