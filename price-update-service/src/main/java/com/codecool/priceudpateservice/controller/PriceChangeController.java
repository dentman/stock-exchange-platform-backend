package com.codecool.priceudpateservice.controller;

import com.codecool.priceudpateservice.model.PriceChange;
import com.codecool.priceudpateservice.service.PriceChangeEvent;
import com.codecool.priceudpateservice.service.PriceChangePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class PriceChangeController {

    private final PriceChangePublisher priceChangePublisher;

    @Autowired
    public PriceChangeController(PriceChangePublisher priceCangePublisher) {
        this.priceChangePublisher = priceCangePublisher;
    }

    @MessageMapping("change")
    Flux<PriceChange> stream(final String request) {
        Flux<PriceChangeEvent> publish = Flux
                .create(priceChangePublisher);
        return publish
                .map(priceChangeEvent -> (PriceChange) priceChangeEvent.getSource());
    }
}
