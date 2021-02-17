package com.codecool.stockexchange.entity.stock;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StockChange {
    private String symbol;
    private String companyName;
    private BigDecimal latestPrice;
    private double change;
    private double changePercent;


    public static StockChange createStockChange(Stock stock) {
        StockChange stockChange = new StockChange();
        stockChange.setSymbol(stock.getSymbol());
        stockChange.setCompanyName(stock.getCompanyName());
        double previousPrice = stock.getPreviousClose().doubleValue();
        BigDecimal currentPrice = stock.getCurrentPrice();
        stockChange.setLatestPrice(currentPrice);
        stockChange.setChange(currentPrice.doubleValue() - previousPrice);
        stockChange.setChangePercent(currentPrice.doubleValue() / previousPrice - 1);
        return stockChange;
    }
}
