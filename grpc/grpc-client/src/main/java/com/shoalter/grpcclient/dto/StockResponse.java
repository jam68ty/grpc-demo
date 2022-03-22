package com.shoalter.grpcclient.dto;

public class StockResponse {
    private int id;
    private String productName;
    private double price;
    private int offerNumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOfferNumber() {
        return offerNumber;
    }

    public void setOfferNumber(int offerNumber) {
        this.offerNumber = offerNumber;
    }
}
