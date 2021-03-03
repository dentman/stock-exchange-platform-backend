package com.codecool.apiservice.service.update;

import com.codecool.apiservice.apimodel.ChartDataPoint;
import com.codecool.apiservice.apimodel.NewsItemAPI;
import com.codecool.apiservice.apimodel.video.VideoItems;
import com.codecool.apiservice.resultmodel.NewsItem;
import com.codecool.apiservice.resultmodel.Stock;
import com.codecool.apiservice.resultmodel.StockPrice;
import com.codecool.apiservice.resultmodel.VideoLink;
import com.codecool.apiservice.service.external.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class StockUpdateService {

    private final ExternalApiService apiService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public StockUpdateService(ExternalApiService apiService,
                              ApplicationEventPublisher applicationEventPublisher) {
        this.apiService = apiService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    //TODO: redundancy in API calls: logic does not take into account trading days!
//    public void handleStockUpdate(String symbol){
//        Stock stock = stockRepository.findFirstBySymbol(symbol);
//        if (stock == null) {
//            stock = createNewStock(symbol);
//        } else {
//            removeSimulatedAndOutdatedStockPrices(stock);
//            long daysToFetch = getNumberOfDaysToFetch(stock);
//            updateStock(stock, daysToFetch);
//        }
//        StockChangeEvent stockChangeEvent = new StockChangeEvent(StockChange.createStockChange(stock));
//        applicationEventPublisher.publishEvent(stockChangeEvent);
//    }

    public Stock updateStock(Stock stock) {
        removeSimulatedAndOutdatedStockPrices(stock);
        long daysToFetch = getNumberOfDaysToFetch(stock);
        updateStock(stock, daysToFetch);
        return stock;
    }

    public Stock createNewStock(String symbol) {
        Stock stock = new Stock(apiService.getQuoteBySymbol(symbol));
        setCharDataPointsOnStock(stock,30);
        setVideoLinkListOnStock(stock);
        setNewsListOnStock(stock);
        return stock;
    }

    private void removeSimulatedAndOutdatedStockPrices(Stock stock) {
        Iterator<StockPrice> priceIterator = stock.getStockPrices().iterator();
        while (priceIterator.hasNext()) {
            StockPrice price = priceIterator.next();
            if (!price.isClosing() || ChronoUnit.DAYS.between(price.getDate(), LocalDate.now()) >= 30) {
                price.setStockId(null);
                priceIterator.remove();
            }
        }
    }

    private long getNumberOfDaysToFetch(Stock stock) {
        LocalDate latest = stock.getStockPrices()
                .stream()
                .max(Comparator.comparing(StockPrice::getDate))
                .map(StockPrice::getDate)
                .orElse(LocalDate.now().minusMonths(1l));
        long daysToFetch = ChronoUnit.DAYS.between(latest, LocalDate.now()); // 1 means latest price is yesterday
        return daysToFetch > 30 ? 30 : daysToFetch;  //should never fetch more than a month
    }

    private void updateStock(Stock stock, long daysToFetch) {
        if (daysToFetch == 1) {
            // chart data is up to date (most recent closing price is yesterday), check if quote is
            long daysPassed = ChronoUnit.DAYS.between(stock.getLastTradeTime(), LocalDateTime.now());
            if (daysPassed > 0) {
                stock.updateByQuote(apiService.getQuoteBySymbol(stock.getSymbol()));
                setVideoLinkListOnStock(stock);
                setNewsListOnStock(stock);
            }
        } else if (daysToFetch > 1) {
            stock.updateByQuote(apiService.getQuoteBySymbol(stock.getSymbol()));
            setVideoLinkListOnStock(stock);
            setCharDataPointsOnStock(stock, daysToFetch);
            setNewsListOnStock(stock);
        }
    }

    private void setCharDataPointsOnStock(Stock stock, long daysToFetch){
        ChartDataPoint[] stockPrices = apiService.getChartDataBySymbolForDays(stock.getSymbol(), daysToFetch - 1);
        for (ChartDataPoint chartDataPoint : stockPrices) {
            StockPrice stockPrice = new StockPrice(chartDataPoint);
            stockPrice.setStockId(stock.getId());
            stock.addStockPrice(stockPrice);
        }
    }

    private void setVideoLinkListOnStock(Stock stock){
        List<VideoLink> videoLinks = new ArrayList<>();
        List<VideoItems> videoFromApi = apiService.getVideoBySymbol(stock.getSymbol()).getItems();
        for (VideoItems item : videoFromApi){
            VideoLink link = new VideoLink();
            link.setDate(LocalDate.now());
    //        link.setStock(stock);
            link.setVideoId(item.getId().getVideoId());
            videoLinks.add(link);
        }
        stock.setVideoLinkList(videoLinks);
    }

    private void setNewsListOnStock(Stock stock){
        List<NewsItemAPI> newsItemAPIList = apiService.getNewsBySymbol(stock.getSymbol());
        List<NewsItem> newsItemList = new ArrayList<>();
        for (NewsItemAPI newsItemAPI : newsItemAPIList){
            NewsItem newsItem = new NewsItem(newsItemAPI);
        //    newsItem.setStockId(stock.getId());
            newsItemList.add(newsItem);
        }
        stock.setNewsList(newsItemList);
    }
}
