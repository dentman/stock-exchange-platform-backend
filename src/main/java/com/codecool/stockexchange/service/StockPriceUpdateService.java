package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.stockinfo.StockInfo;
import com.codecool.stockexchange.repository.StockInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;


@Service
public class StockPriceUpdateService {

    @Autowired
    private StockInfoRepository stockInfoRepository;


    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    @Transactional
    public void simulateStockPrices() {
        List<StockInfo> stockData = stockInfoRepository.findAll();
        stockData.stream().forEach(stock -> setNextPrice(stock));
    }

    public void setNextPrice(StockInfo stock) {
        Random random = new Random();
        int trend = stock.getYtdChange() > 0 ? 1 : -1;
        int direction = random.nextInt(100) < 80 ? trend : -trend;
        double change = (double) random.nextInt(3) / 100 * direction;
        BigDecimal nextPrice = stock.getCurrentPrice().multiply(BigDecimal.valueOf(1 + change));
        stock.setSimulatedStockPrice(nextPrice);
    }
}
