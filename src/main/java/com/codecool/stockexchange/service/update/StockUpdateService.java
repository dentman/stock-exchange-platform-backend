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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class StockUpdateService {

    private final StockRepository stockRepository;
    private final ExternalApiService apiService;

    @Autowired
    public StockUpdateService(StockRepository stockRepository, ExternalApiService apiService) {
        this.stockRepository = stockRepository;
        this.apiService = apiService;
    }

    //TODO: redundancy in API calls: logic does not take into account trading days!
    @Transactional
    public void saveOrUpdate(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        if (stock == null) {
            createAndPersistNewStock(symbol);
        } else {
            removeSimulatedAndOutdatedStockPrices(stock);
            long daysToFetch = getNumberOfDaysToFetch(stock);
            updateStock(stock, daysToFetch);
        }
    }

    private void createAndPersistNewStock(String symbol) {
        Stock stock = new Stock(apiService.getQuoteBySymbol(symbol));
        setCharDataPointsOnStock(stock,30);
        setVideoLinkListOnStock(stock);
        setNewsListOnStock(stock);
        stockRepository.save(stock);
    }

    private void removeSimulatedAndOutdatedStockPrices(Stock stock) {
        Iterator<StockPrice> priceIterator = stock.getStockPrices().iterator();
        while (priceIterator.hasNext()) {
            StockPrice price = priceIterator.next();
            if (!price.isClosing() || ChronoUnit.DAYS.between(price.getDate(), LocalDate.now()) >= 30) {
                price.setStock(null);
                priceIterator.remove();
            }
        }
    }

    private long getNumberOfDaysToFetch(Stock stock) {
        StockPrice latest = stock.getStockPrices()
                .stream()
                .max(Comparator.comparing(StockPrice::getDate))
                .orElse(null);
        long daysToFetch = ChronoUnit.DAYS.between(latest.getDate(), LocalDate.now()); // 1 means latest price is yesterday
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
            stockPrice.setStock(stock);
            stock.addStockPrice(stockPrice);
        }
    }

    private void setVideoLinkListOnStock(Stock stock){
        List<VideoLink> videoLinks = new ArrayList<>();
        List<VideoItems> videoFromApi = apiService.getVideoBySymbol(stock.getSymbol()).getItems();
        for (VideoItems item : videoFromApi){
            VideoLink link = new VideoLink();
            link.setDate(LocalDate.now());
            link.setStock(stock);
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
            newsItem.setStock(stock);
            newsItemList.add(newsItem);
        }
        stock.setNewsList(newsItemList);
    }
}
