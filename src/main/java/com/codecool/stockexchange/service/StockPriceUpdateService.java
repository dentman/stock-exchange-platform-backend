package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.stockinfo.StockInfo;
import com.codecool.stockexchange.repository.StockInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockPriceUpdateService {

    @Autowired
    private StockInfoRepository stockInfoRepository;


    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    @Transactional
    public void simulateStockPrices() {
        List<StockInfo> stockData = stockInfoRepository.findAll();
        stockData.stream().forEach(stock -> stock.setNextPrice());
    }

}
