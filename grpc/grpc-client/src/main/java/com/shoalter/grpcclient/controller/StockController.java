package com.shoalter.grpcclient.controller;

import com.google.gson.Gson;
import com.shoalter.grpc.StockResponse;
import com.shoalter.grpcclient.service.GrpcStockClientService;
import com.shoalter.grpcclient.util.ProtoJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private GrpcStockClientService grpcStockClientService;

    @RequestMapping("/unaryGetStock")
    public ResponseEntity<com.shoalter.grpcclient.dto.StockResponse> unaryGetStock(@RequestParam(value = "id") String id) {
        try {
            StockResponse response = grpcStockClientService.unaryGetStock(Integer.parseInt(id));
            String json = ProtoJsonUtil.toJson(response);
            com.shoalter.grpcclient.dto.StockResponse result = new Gson().fromJson(json, com.shoalter.grpcclient.dto.StockResponse.class);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/serverSideStreamingGetStock")
    public ResponseEntity<String> serverSideStreamingGetStock(@RequestParam(value = "prefix") String prefix) {
        try {
            grpcStockClientService.serverSideStreamingGetStock(prefix);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/clientSideStreamingGetStatisticsOfStocks")
    public ResponseEntity<String> clientSideStreamingGetStatisticsOfStocks() {
        try {
            grpcStockClientService.clientSideStreamingGetStatisticsOfStocks();
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/bidirectionalStreamingGetListOfStocks")
    public ResponseEntity<String> bidirectionalStreamingGetListOfStocks() {
        try {
            grpcStockClientService.bidirectionalStreamingGetListOfStocks();
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
