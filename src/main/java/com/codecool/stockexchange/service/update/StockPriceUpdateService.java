package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockPriceUpdateService {

    @Autowired
    private StockRepository stockRepository;


    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    @Transactional
    public void simulateStockPrices() {
        List<Stock> stockData = stockRepository.findAll();
        stockData.stream().forEach(stock -> {
            stock.setNextPrice();
        });
    }

}
