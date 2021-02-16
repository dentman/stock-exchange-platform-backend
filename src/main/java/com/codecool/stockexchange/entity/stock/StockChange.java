package com.codecool.stockexchange.entity.stock;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StockChange {
    private String symbol;
    private BigDecimal latestPrice;
    private double change;
    private double changePercent;


    public static StockChange createStockChange(Stock stock) {
        StockChange stockChange = new StockChange();
        stockChange.setSymbol(stock.getSymbol());
        stockChange.setLatestPrice(stock.getPreviousClose());
        double previousPrice = stock.getPreviousClose().doubleValue();
        double currentPrice = stock.getCurrentPrice().doubleValue();
        stockChange.setChange(currentPrice - previousPrice);
        stockChange.setChangePercent(currentPrice / previousPrice - 1);
        return stockChange;
    }
}
