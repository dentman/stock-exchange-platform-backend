package com.codecool.stockexchange.entity.stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stock", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @ToString.Exclude
    private List<StockPrice> stockPrices = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "stock", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<NewsItem> newsList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "stock", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<VideoLink> videoLinkList = new ArrayList<>();

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
        int direction = random.nextInt(100) < 52 ? trend : -trend;
        double change = (double) random.nextInt(5) / 1000 * direction;
        BigDecimal nextPrice = getCurrentPrice().multiply(BigDecimal.valueOf(1 + change));
        setSimulatedStockPrice(nextPrice);
    }
}
