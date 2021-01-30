package com.codecool.stockexchange.service;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.entity.NewsItem;
import com.codecool.stockexchange.entity.StockInfo;
import com.codecool.stockexchange.entity.StockPrice;
import com.codecool.stockexchange.repository.StockInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class StockInfoDbUpdateService {

    @Autowired
    StockInfoRepository stockInfoRepository;

    @Autowired
    ExternalApiService apiService;

    public void saveOrUpdate(String symbol) {
        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        long daysToFetch;
        if (stockInfo == null) {
            stockInfo = new StockInfo(apiService.getQuoteBySymbol(symbol));
            daysToFetch = 30;
        } else {
            StockPrice latest = stockInfo.getStockPrices().stream().max(Comparator.comparing(StockPrice::getDate)).orElse(null);
            daysToFetch = ChronoUnit.DAYS.between(latest.getDate(), LocalDate.now()) - 1;
            daysToFetch = daysToFetch > 30 ? 30 : daysToFetch;
        }
        if (daysToFetch > 0){
            // fetch missing historical data points (add to previously stored data)
            ChartDataPoint[] stockPrices = apiService.getChartDataBySymbolForDays(symbol, daysToFetch);
            for (ChartDataPoint chartDataPoint : stockPrices) {
                StockPrice stockPrice = new StockPrice(chartDataPoint);
                stockPrice.setStockInfo(stockInfo);
                stockInfo.addStockPrice(stockPrice);
            }
            // re-write news list
            List<NewsItemAPI> newsItemAPIList = apiService.getNewsBySymbol(symbol);
            List<NewsItem> newsItemList = new ArrayList<>();
            for (NewsItemAPI newsItemAPI : newsItemAPIList){
                NewsItem newsItem = new NewsItem(newsItemAPI);
                newsItem.setStockInfo(stockInfo);
                newsItemList.add(newsItem);
            }
            stockInfo.setNewsList(newsItemList);
        }
        stockInfoRepository.save(stockInfo);


    }
}
