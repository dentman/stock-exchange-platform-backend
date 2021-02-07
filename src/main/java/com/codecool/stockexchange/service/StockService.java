package com.codecool.stockexchange.service;

import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.entity.StockBaseData;
import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.repository.StockBaseDataRepository;
import com.codecool.stockexchange.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockBaseDataRepository baseDataRepository;

    public Quote findFirstBySymbol(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        return Quote.createQuote(stock);
    }

    public Stock findStockBySymbol(String symbol) {
        return stockRepository.findFirstBySymbol(symbol);
    }

    public List<StockBaseData> getStockBaseData(){ return baseDataRepository.findAll(); }
}
