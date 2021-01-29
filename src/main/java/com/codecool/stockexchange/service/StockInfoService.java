package com.codecool.stockexchange.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.entity.StockInfo;
import com.codecool.stockexchange.entity.StockPrice;
import com.codecool.stockexchange.repository.StockInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockInfoService {

    @Autowired
    StockInfoRepository stockInfoRepository;

    public Quote findFirstBySymbol(String symbol) {
        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        return Quote.createQuoute(stockInfo);
    }

    public ChartDataPoint[] getChartDataPoints(String symbol) {
        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        List<StockPrice> stockPrices = stockInfo.getStockPrices().stream()
                .filter(data -> data.getDate().isAfter(LocalDate.now().minusDays(30))).collect(Collectors.toList());
        return stockPrices.stream().map(ChartDataPoint::createChartDataPoint).toArray(ChartDataPoint[]::new);
    }

}
