package com.codecool.stockexchange;

public class Stock {
    private String symbol;
    private String companyName;
    private double changePercent;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    private double latestPrice;

    public Stock(String symbol, String companyName, double changePercent, double latestPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.changePercent = changePercent;
        this.latestPrice = latestPrice;
    }
}
