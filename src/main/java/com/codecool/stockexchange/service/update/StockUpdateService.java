package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.apimodel.video.VideoItems;
import com.codecool.stockexchange.entity.stock.NewsItem;
import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.stock.StockPrice;
import com.codecool.stockexchange.entity.stock.VideoLink;
import com.codecool.stockexchange.repository.StockRepository;
import com.codecool.stockexchange.service.external.ExternalApiService;
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
public class StockUpdateService {

    @Autowired
    StockRepository stockRepository;

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
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        long daysToFetch = 30; // !!!! 30
        if (stock == null) {
            stock = new Stock(apiService.getQuoteBySymbol(symbol));
            setVideoLinkList(stock, symbol);
        } else {
            StockPrice latest = stock.getStockPrices().stream().max(Comparator.comparing(StockPrice::getDate)).orElse(null);
            daysToFetch = ChronoUnit.DAYS.between(latest.getDate(), LocalDate.now()); // 1 means latest price is yesterday (chart up-to-date)
            daysToFetch = daysToFetch > 30 ? 30 : daysToFetch;  //should never fetch more than a month
        }
        if (daysToFetch == 1){
            // chart data is up to date, check if quote is
            long daysPassed = ChronoUnit.DAYS.between(stock.getLastTradeTime(), LocalDate.now());
            if (daysPassed > 0) {
                stock.updateByQuote(apiService.getQuoteBySymbol(symbol));
                setVideoLinkList(stock, symbol);
            }
        } else if (daysToFetch > 1){
            stock.updateByQuote(apiService.getQuoteBySymbol(symbol));
            setVideoLinkList(stock, symbol);
            // fetch missing historical data points (add to previously stored data)
            ChartDataPoint[] stockPrices = apiService.getChartDataBySymbolForDays(symbol, daysToFetch - 1);
            for (ChartDataPoint chartDataPoint : stockPrices) {
                StockPrice stockPrice = new StockPrice(chartDataPoint);
                stockPrice.setStock(stock);
                stock.addStockPrice(stockPrice);
            }
            // re-write news list
            List<NewsItemAPI> newsItemAPIList = apiService.getNewsBySymbol(symbol);
            List<NewsItem> newsItemList = new ArrayList<>();
            for (NewsItemAPI newsItemAPI : newsItemAPIList){
                NewsItem newsItem = new NewsItem(newsItemAPI);
                newsItem.setStock(stock);
                newsItemList.add(newsItem);
            }
            stock.setNewsList(newsItemList);
        }
        stockRepository.save(stock);


    }

    private void setVideoLinkList(Stock stock, String symbol){
        List<VideoLink> videoLinks = new ArrayList<>();
        List<VideoItems> videoFromApi = apiService.getVideoBySymbol(symbol).getItems();
        for (VideoItems item : videoFromApi){
            VideoLink link = new VideoLink();
            link.setDate(LocalDate.now());
            link.setStock(stock);
            link.setVideoId(item.getId().getVideoId());
            videoLinks.add(link);
        }
        stock.setVideoLinkList(videoLinks);
    }
}
