package com.codecool.stockexchange.entity.stock;

import org.springframework.context.ApplicationEvent;

public class StockChangeEvent extends ApplicationEvent {
    public StockChangeEvent(Object source) {
        super(source);
    }
}
