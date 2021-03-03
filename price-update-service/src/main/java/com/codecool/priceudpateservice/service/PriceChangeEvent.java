package com.codecool.priceudpateservice.service;

import org.springframework.context.ApplicationEvent;

public class PriceChangeEvent extends ApplicationEvent {
    public PriceChangeEvent(Object source) {
        super(source);
    }
}
