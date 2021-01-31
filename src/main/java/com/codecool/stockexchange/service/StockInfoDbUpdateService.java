package com.codecool.stockexchange.service;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.entity.stockinfo.NewsItem;
import com.codecool.stockexchange.entity.stockinfo.StockInfo;
import com.codecool.stockexchange.entity.stockinfo.StockPrice;
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

    /***
     * if I ask for 2 days on Saturday, api gives Thursday & Friday
     * if I ask for 2 days on Sunday, it gives Friday
     * -> current day is not part of the returned data -> daysToFetch = (now - latest) - 1
     * @param symbol
     */
    //TODO: this does not take into account trading days!
    public void saveOrUpdate(String symbol) {
        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        long daysToFetch = 30;
        if (stockInfo == null) {
            stockInfo = new StockInfo(apiService.getQuoteBySymbol(symbol));
        } else {
            StockPrice latest = stockInfo.getStockPrices().stream().max(Comparator.comparing(StockPrice::getDate)).orElse(null);
            daysToFetch = ChronoUnit.DAYS.between(latest.getDate(), LocalDate.now()); // 1 means latest price is yesterday (chart up-to-date)
            daysToFetch = daysToFetch > 30 ? 30 : daysToFetch;  //should never fetch more than a month
        }
        if (daysToFetch == 1){
            // chart data is up to date, check if quote is
            long daysPassed = ChronoUnit.DAYS.between(stockInfo.getLastTradeTime(), LocalDate.now());
            if (daysPassed > 0) stockInfo.updateByQuote(apiService.getQuoteBySymbol(symbol));
        } else if (daysToFetch > 1){
            stockInfo.updateByQuote(apiService.getQuoteBySymbol(symbol));
            // fetch missing historical data points (add to previously stored data)
            ChartDataPoint[] stockPrices = apiService.getChartDataBySymbolForDays(symbol, daysToFetch - 1);
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
