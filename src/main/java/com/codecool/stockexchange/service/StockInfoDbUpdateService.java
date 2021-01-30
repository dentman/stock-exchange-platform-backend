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

import java.time.DayOfWeek;
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
        int day = LocalDate.now().getDayOfWeek().getValue(); //6 or 7 if it is weekend

        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        long daysToFetch;
        if (stockInfo == null) {
            //stock not in db yet
            stockInfo = new StockInfo(apiService.getQuoteBySymbol(symbol));
            daysToFetch = 30;
        } else {
            StockPrice latest = stockInfo.getStockPrices().stream().max(Comparator.comparing(StockPrice::getDate)).orElse(null);
            System.out.println("latest price info " + latest.getDate());
            daysToFetch = ChronoUnit.DAYS.between(latest.getDate(), LocalDate.now()); //if history starts "yesterday", we should subtract 1!
            // if I ask for 2days on Saturday, api gives Thursday & Friday -> should account for this:
            daysToFetch = day == 6 ? daysToFetch - 1 : day == 7 ? daysToFetch - 2 : daysToFetch;
            // should never fetch more than a month:
            daysToFetch = daysToFetch > 30 ? 30 : daysToFetch;
            System.out.println("days to fetch " + daysToFetch);
        }
        if (daysToFetch > 0){
            // if chart data is not up to date, neither is the quote
            stockInfo.updateByQuote(apiService.getQuoteBySymbol(symbol));
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
