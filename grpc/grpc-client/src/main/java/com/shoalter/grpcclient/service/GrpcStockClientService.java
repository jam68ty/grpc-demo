package com.shoalter.grpcclient.service;

import com.shoalter.grpc.StockRequest;
import com.shoalter.grpc.StockResponse;
import com.shoalter.grpc.StockServiceGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrpcStockClientService {
    @GrpcClient("grpc-server-side")
    private StockServiceGrpc.StockServiceBlockingStub stockServiceBlockingStub;

    @GrpcClient("grpc-server-side")
    private StockServiceGrpc.StockServiceStub stockServiceStub;

    Logger logger = LoggerFactory.getLogger(GrpcStockClientService.class);

    public void unaryGetStock() {
        StockRequest request = StockRequest.newBuilder().setId(1).build();
        StockResponse response;
        try {
            response = stockServiceBlockingStub.unaryGetStock(request);
            logger.info("[unaryGetStock] - id#{} {}: ${}/{} ",
                    response.getId(),
                    response.getProductName(),
                    response.getPrice(),
                    response.getPrice());
        } catch (StatusRuntimeException e) {
            logger.error("[unaryGetStock] - RPC failed: {}", String.valueOf(e));
        }
    }

}
