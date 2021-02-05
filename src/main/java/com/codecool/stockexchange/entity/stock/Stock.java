package com.codecool.stockexchange.entity.stock;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codecool.stockexchange.apimodel.Quote;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;
    private String companyName;
    private Long marketCap;
    private BigDecimal previousClose;
    private BigDecimal week52Low;
    private BigDecimal week52High;
    private Integer avgTotalVolume;
    private double peRatio;
    private double ytdChange;
    private LocalDateTime lastTradeTime;
    private BigDecimal latestPrice;

    @OneToMany(mappedBy = "stock", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<StockPrice> stockPrices = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<NewsItem> newsList = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<VideoLink> videoLinkList = new ArrayList<>();

    public Stock(Quote quote) {
        setAllQuoteInfo(quote);
    }

    public void updateByQuote(Quote quote){
        setAllQuoteInfo(quote);
    }

    private void setAllQuoteInfo(Quote quote) {
        this.symbol = quote.getSymbol();
        this.companyName = quote.getCompanyName();
        this.marketCap = quote.getMarketCap();
        this.previousClose = BigDecimal.valueOf(quote.getPreviousClose());
        this.week52Low = BigDecimal.valueOf(quote.getWeek52Low());
        this.week52High = BigDecimal.valueOf(quote.getWeek52High());
        this.avgTotalVolume = quote.getAvgTotalVolume();
        this.peRatio = quote.getPeRatio();
        this.ytdChange = quote.getYtdChange();
        this.lastTradeTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(quote.getLastTradeTime()/1000), ZoneId.systemDefault());
        this.latestPrice = BigDecimal.valueOf(quote.getLatestPrice());
    }

    public void addStockPrice(StockPrice stockPrice) {
        stockPrices.add(stockPrice);
    }

    public BigDecimal getCurrentPrice() {
        Optional<StockPrice> currentPriceOptional = getLastPrice();

        if (currentPriceOptional.isPresent()) {
            return currentPriceOptional.get().getPrice();
        } else {
            return BigDecimal.valueOf(0);
        }

    }

    public Optional<StockPrice> getLastPrice() {
        return stockPrices.stream().max(Comparator.comparing(StockPrice::getDate));
    }

    public void setSimulatedStockPrice(BigDecimal nextPrice) {
        Optional<StockPrice> lastPriceOptional = getLastPrice();
        LocalDate currentDate = LocalDate.now();
        if (lastPriceOptional.isPresent()) {
            StockPrice lastPrice = lastPriceOptional.get();
            if (lastPrice.getDate().isEqual(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()))) {
                lastPrice.setClosing(false);
                lastPrice.setPrice(nextPrice);
            }
            else {
                StockPrice newPrice = new StockPrice();
                newPrice.setSymbol(lastPrice.getSymbol());
                newPrice.setStock(this);
                newPrice.setDate(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()));
                newPrice.setClosing(false);
                newPrice.setPrice(nextPrice);
                addStockPrice(newPrice);
            }
        }
    }

    public void setNextPrice() {
        Random random = new Random();
        int trend = getYtdChange() > 0 ? 1 : -1;
        int direction = random.nextInt(100) < 70 ? trend : -trend;
        double change = (double) random.nextInt(10) / 1000 * direction;
        BigDecimal nextPrice = getCurrentPrice().multiply(BigDecimal.valueOf(1 + change));
        setSimulatedStockPrice(nextPrice);
    }
    public void addNewsItem(NewsItem item) { newsList.add(item); }
}
