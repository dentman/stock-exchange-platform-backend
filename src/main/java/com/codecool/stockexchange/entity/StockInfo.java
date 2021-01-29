package com.codecool.stockexchange.entity;

import java.util.ArrayList;
import java.util.List;

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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StockInfo {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;
    private String companyName;
    private Long stockAmount;

    @OneToMany(mappedBy = "stockInfo", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<StockPrice> stockPrices = new ArrayList<>();

    public StockInfo(Quote quote) {
        this.symbol = quote.getSymbol();
        this.companyName = quote.getCompanyName();
    }

    public void addStockPrice(StockPrice stockPrice) {
        stockPrices.add(stockPrice);
    }

}
