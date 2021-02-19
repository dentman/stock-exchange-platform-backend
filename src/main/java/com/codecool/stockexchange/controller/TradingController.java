package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.security.CustomUser;
import com.codecool.stockexchange.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(value = "${cors.allowed.path}")
public class TradingController {

    TradingService tradingService;

    @Autowired
    public TradingController (TradingService tradingService){
        this.tradingService = tradingService;
    }

    @PostMapping("/trade")
    public OrderStatus postOrder(@RequestBody @Validated Order order){
        tradingService.checkOrder(order);
        order.setDate(LocalDateTime.now());
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return tradingService.handleOrder(order, user.getUserId());
    }
}
