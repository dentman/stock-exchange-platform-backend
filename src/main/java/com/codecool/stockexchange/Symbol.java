package com.codecool.stockexchange;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Symbol {
    private Map<String, String> stocklist = Map.of("OBLN", "Obalon Therapeutics Inc", "GEVO", "Gevo Inc", "PBR",
            "Petroleo Brasileiro S.A. Petrobras - ADR", "BB", "BlackBerry Ltd", "BABA",
            "Alibaba Group Holding Ltd - ADR", "SENS", "Senseonics Holdings Inc", "MSFT", "Microsoft Corporation",
            "BNGO", "Bionano Genomics Inc", "AMC", "AMC Entertainment Holdings Inc - Class A", "GM",
            "General Motors Company");

    private Map<String, Integer[]> quotes = Map.of("OBLN", new Integer[] { 1, 2 }, "GEVO", new Integer[] { 1, 2 },
            "PBR", new Integer[] { 1, 2 }, "BB", new Integer[] { 1, 2 }, "BABA", new Integer[] { 1, 2 }, "SENS",
            new Integer[] { 1, 2 }, "MSFT", new Integer[] { 1, 2 }, "BNGO", new Integer[] { 1, 2 }, "AMC",
            new Integer[] { 1, 2 }, "GM", new Integer[] { 1, 2 });

    public Map<String, Integer[]> getQuotes() {
        return quotes;
    }

    public Map<String, String> getStocklist() {
        return stocklist;
    }

    public void setStocklist(Map<String, String> stocklist) {
        this.stocklist = stocklist;
    }

    public Stock getStock(String symbol) {

        return new Stock(symbol, stocklist.get(symbol), quotes.get(symbol)[0], quotes.get(symbol)[1]);
    }
}
