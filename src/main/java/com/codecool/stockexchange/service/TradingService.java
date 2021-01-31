package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.Status;
import org.springframework.stereotype.Service;

@Service
public class TradingService {
    public Status handleOrder(Order order) {
        System.out.println(order);
        return Status.COMPLETED;
    }
}
