package com.codecool.stockexchange.entity.stockinfo;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
public class StockInfo {

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

    @OneToMany(mappedBy = "stockInfo", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<StockPrice> stockPrices = new ArrayList<>();

    @OneToMany(mappedBy = "stockInfo", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<NewsItem> newsList = new ArrayList<>();

    @OneToMany(mappedBy = "stockInfo", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<VideoLink> videoLinkList = new ArrayList<>();

    public StockInfo(Quote quote) {
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
    }

    public void addStockPrice(StockPrice stockPrice) {
        stockPrices.add(stockPrice);
    }

    // TODO: extended handling of random generation
    public BigDecimal getCurrentPrice() {
        Optional<StockPrice> currentPriceOptional = stockPrices.stream().max(Comparator.comparing(StockPrice::getDate));

        if (currentPriceOptional.isPresent()) {
            return currentPriceOptional.get().getPrice();
        } else {
            return BigDecimal.valueOf(0);
        }

    }

    public void addNewsItem(NewsItem item) { newsList.add(item); }
}
