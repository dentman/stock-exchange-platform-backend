package com.codecool.priceudpateservice.service;

import com.codecool.priceudpateservice.model.PriceChange;
import com.codecool.priceudpateservice.util.PriceChangeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceChangeService {
    private final int REFRESH_INTERVAL = 1500;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PriceChangeGenerator priceChangeGenerator;


    @Autowired
    public PriceChangeService(ApplicationEventPublisher applicationEventPublisher,
                              PriceChangeGenerator priceChangeGenerator) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.priceChangeGenerator = priceChangeGenerator;
    }

    @Scheduled(initialDelay = REFRESH_INTERVAL, fixedRate = REFRESH_INTERVAL)
    public void simulateStockPrices() {
        PriceChange priceChange = new PriceChange("TSLA", priceChangeGenerator.generateChange());
        log.info(priceChange.getSymbol() + " " + priceChange.getChange());
        PriceChangeEvent priceChangeEvent = new PriceChangeEvent(priceChange);
        applicationEventPublisher.publishEvent(priceChangeEvent);
    }
}
