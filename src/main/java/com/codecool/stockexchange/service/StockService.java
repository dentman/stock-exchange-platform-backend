package com.codecool.stockexchange.service;

import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    public Quote findFirstBySymbol(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        return Quote.createQuote(stock);
    }

    public Stock findStockBySymbol(String symbol) {
        return stockRepository.findFirstBySymbol(symbol);
    }
}
