package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.StockBaseData;
import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.stock.StockChange;
import com.codecool.stockexchange.repository.StockBaseDataRepository;
import com.codecool.stockexchange.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockBaseDataRepository baseDataRepository;

    @Autowired
    public StockService(StockRepository stockRepository, StockBaseDataRepository baseDataRepository) {
        this.stockRepository = stockRepository;
        this.baseDataRepository = baseDataRepository;
    }

    public Stock findStockBySymbol(String symbol) {
        return stockRepository.findFirstBySymbol(symbol);
    }

    public List<StockBaseData> getStockBaseData(){ return baseDataRepository.findAll(); }

    public StockChange getStockChangeData(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        return StockChange.createStockChange(stock);
    }
}
