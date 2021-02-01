package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TradingController {

    @Autowired
    TradingService tradingService;

    @PostMapping("/trade")
    public OrderStatus postOrder(@RequestBody @Validated Order order){
        return tradingService.handleOrder(order);
    }
}
