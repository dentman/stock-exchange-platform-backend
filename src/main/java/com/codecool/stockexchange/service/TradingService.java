package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.OrderItem;
import com.codecool.stockexchange.entity.trade.Status;
import org.springframework.stereotype.Service;

@Service
public class TradingService {
    public Status handleOrder(OrderItem order) {
        System.out.println(order);
        return Status.COMPLETED;
    }
}
