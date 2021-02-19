package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.exception.trade.InvalidOrderStatusException;
import com.codecool.stockexchange.exception.trade.InvalidSymbolFormatException;
import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.security.CustomUser;
import com.codecool.stockexchange.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@CrossOrigin(value = "${cors.allowed.path}")
public class TradingController {

    @Autowired
    TradingService tradingService;

    @PostMapping("/trade")
    public OrderStatus postOrder(@RequestBody @Validated Order order){
        String symbol = order.getSymbol();
        if (symbol == null || symbol.equals("") || symbol.chars().anyMatch(c -> !Character.isUpperCase((char) c))) {
            throw new InvalidSymbolFormatException();
        }
        else if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new InvalidOrderStatusException();
        }
        else if (Arrays.stream(OrderStatus.values())
                .noneMatch(orderStatus -> orderStatus.equals(order.getStatus()))) {
            throw new InvalidOrderStatusException();
        }
        else if (order.getCount() <= 0 || order.getLimitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NumberFormatException("Order count and price must be positive numbers!");
        }
        else {
            order.setDate(LocalDateTime.now());
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return tradingService.handleOrder(order, user.getUserId());
        }
    }

}
