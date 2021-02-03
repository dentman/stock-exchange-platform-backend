package com.codecool.stockexchange.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.apimodel.video.Video;
import com.codecool.stockexchange.entity.stockinfo.StockInfo;
import com.codecool.stockexchange.entity.stockinfo.StockPrice;
import com.codecool.stockexchange.entity.stockinfo.VideoLink;
import com.codecool.stockexchange.repository.StockInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockInfoService {

    @Autowired
    StockInfoRepository stockInfoRepository;

    Random random = new Random();

    public Quote findFirstBySymbol(String symbol) {
        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        return Quote.createQuote(stockInfo);
    }

    public ChartDataPoint[] getChartDataPoints(String symbol) {
        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        List<StockPrice> stockPrices = stockInfo.getStockPrices().stream()
                .filter(data -> data.getDate().isAfter(LocalDate.now().minusDays(30))).collect(Collectors.toList());
        return stockPrices.stream().map(ChartDataPoint::createChartDataPoint).toArray(ChartDataPoint[]::new);
    }


    public NewsItemAPI[] findNewsBySymbol(String symbol) {
        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        return stockInfo.getNewsList()
                .stream()
                .filter(n -> Instant.now().toEpochMilli() - n.getDatetime() < 3 * 86400000)
                .map(NewsItemAPI::createNewsItem).toArray(NewsItemAPI[]::new);

    }

    public List<VideoLink> findVideosBySymbol(String symbol) {
        return stockInfoRepository.findFirstBySymbol(symbol).getVideoLinkList();
    }

    public VideoLink findRandomVideoForSymbol(String symbol) {
        List<VideoLink> allVids = stockInfoRepository.findFirstBySymbol(symbol).getVideoLinkList();
        return allVids.get(random.nextInt(allVids.size()));
    }

    public StockInfo findStockInfoBySymbol(String symbol) {
        return stockInfoRepository.findFirstBySymbol(symbol);
    }
}
