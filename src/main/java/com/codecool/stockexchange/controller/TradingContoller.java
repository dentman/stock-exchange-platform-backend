package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.Status;
import com.codecool.stockexchange.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradingContoller {

    @Autowired
    TradingService tradingService;

    @PostMapping("/trade")
    public Status postOrder(@RequestBody @Validated Order order){
        return tradingService.handleOrder(order);
    }
}
