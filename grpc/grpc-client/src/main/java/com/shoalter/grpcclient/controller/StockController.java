package com.shoalter.grpcclient.controller;

import com.shoalter.grpcclient.service.GrpcStockClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private GrpcStockClientService grpcStockClientService;

    @RequestMapping("/unaryGetStock")
    public String unaryGetStock(@RequestParam(value = "id") String id) {
        try {
            grpcStockClientService.unaryGetStock(Integer.valueOf(id));
            return "success!";
        } catch (Exception e) {
            return "failed..." + e;
        }
    }
}
