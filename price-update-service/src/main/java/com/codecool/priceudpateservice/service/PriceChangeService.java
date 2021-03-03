package com.codecool.priceudpateservice.service;

import com.codecool.priceudpateservice.PriceChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class PriceChangeService {
    private final int REFRESH_INTERVAL = 1500;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    public PriceChangeService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(initialDelay = REFRESH_INTERVAL, fixedRate = REFRESH_INTERVAL)
    public void simulateStockPrices() {
        Random random = new Random();
        List<String> stockData = new LinkedList<>();
        stockData.stream().forEach(stock -> {
            if (random.nextInt(100) < 15) {
                PriceChangeEvent priceChangeEvent = new PriceChangeEvent("");
                applicationEventPublisher.publishEvent(priceChangeEvent);
            }
        });
    }
}
