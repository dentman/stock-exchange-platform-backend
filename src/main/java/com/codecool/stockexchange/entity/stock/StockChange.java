package com.codecool.stockexchange.entity.stock;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StockChange {
    private String symbol;
    private String companyName;
    private double latestPrice;
    private double change;
    private double changePercent;

    public static StockChange createStockChange(Stock stock) {
        StockChange stockChange = new StockChange();
        stockChange.setSymbol(stock.getSymbol());
        stockChange.setCompanyName(stock.getCompanyName());
        double previousPrice = stock.getPreviousClose().doubleValue();
        BigDecimal currentPrice = stock.getCurrentPrice();
        stockChange.setLatestPrice(round(currentPrice.doubleValue(), 2));
        stockChange.setChange(round(currentPrice.doubleValue() - previousPrice, 1));
        stockChange.setChangePercent(round(currentPrice.doubleValue() / previousPrice - 1, 4));
        return stockChange;
    }

    private static double round(double number, int decimal) {
        double scale = Math.pow(10, decimal);
        return (double) Math.round(number * scale) / scale;
    }
}
