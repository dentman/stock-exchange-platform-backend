package com.codecool.stockexchange.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.stock.StockPrice;
import com.codecool.stockexchange.entity.stock.VideoLink;
import com.codecool.stockexchange.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    Random random = new Random();

    public Quote findFirstBySymbol(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        return Quote.createQuote(stock);
    }

    public ChartDataPoint[] getChartDataPoints(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        List<StockPrice> stockPrices = stock.getStockPrices().stream()
                .filter(data -> data.getDate().isAfter(LocalDate.now().minusDays(30))).collect(Collectors.toList());
        return stockPrices.stream().map(ChartDataPoint::createChartDataPoint).toArray(ChartDataPoint[]::new);
    }


    public NewsItemAPI[] findNewsBySymbol(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        return stock.getNewsList()
                .stream()
                .filter(n -> Instant.now().toEpochMilli() - n.getDatetime() < 3 * 86400000)
                .map(NewsItemAPI::createNewsItem).toArray(NewsItemAPI[]::new);

    }

    public List<VideoLink> findVideosBySymbol(String symbol) {
        return stockRepository.findFirstBySymbol(symbol).getVideoLinkList();
    }

    public VideoLink findRandomVideoForSymbol(String symbol) {
        List<VideoLink> allVids = stockRepository.findFirstBySymbol(symbol).getVideoLinkList();
        return allVids.get(random.nextInt(allVids.size()));
    }

    public Stock findStockBySymbol(String symbol) {
        return stockRepository.findFirstBySymbol(symbol);
    }
}
