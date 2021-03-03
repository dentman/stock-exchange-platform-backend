package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.stock.StockChange;
import com.codecool.stockexchange.entity.stock.StockChangeEvent;
import com.codecool.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class StockPriceUpdateService {

    private final int REFRESH_INTERVAL = 1500;
    private final StockRepository stockRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RSocketRequester client;


    @Autowired
    public StockPriceUpdateService(StockRepository stockRepository,
                                   ApplicationEventPublisher applicationEventPublisher,
                                   @Qualifier("pricerequester") RSocketRequester client) {
        this.stockRepository = stockRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.client = client;
    }

    @Scheduled(initialDelay = REFRESH_INTERVAL, fixedRate = REFRESH_INTERVAL)
    @Transactional
    public void simulateStockPrices() {
        Random random = new Random();
        List<Stock> stockData = stockRepository.findAll();
        stockData.stream().forEach(stock -> {
            if (random.nextInt(100) < 15) {
                stock.setNextPrice();
                StockChangeEvent stockChangeEvent = new StockChangeEvent(StockChange.createStockChange(stock));
                applicationEventPublisher.publishEvent(stockChangeEvent);
            }
        });
    }

}
