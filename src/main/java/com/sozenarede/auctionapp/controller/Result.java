package com.sozenarede.auctionapp.controller;

public class Result {
    private String bidOwner;
    private double bidValue;
    private double auctionProft;


    public Result(String bidowner, double bidvalue, double auctionproft){
        this.bidOwner = bidowner;
        this.bidValue = bidvalue;
        this.auctionProft = auctionproft;
    }

    public void setBidValue(double bidValue) {
        this.bidValue = bidValue;
    }

    public void setBidOwner(String bidowner) {
        this.bidOwner = bidowner;
    }

    public void setAuctionProft(double auctionProft) {
        this.auctionProft = auctionProft;
    }

    public double getBidValue() {
        return bidValue;
    }

    public String getBidOwner() {
        return bidOwner;
    }

    public double getAuctionProft() {
        return auctionProft;
    }
}
