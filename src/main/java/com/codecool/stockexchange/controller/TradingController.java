package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.exception.ErrorMessage;
import com.codecool.stockexchange.exception.trade.InvalidOrderStatusException;
import com.codecool.stockexchange.exception.trade.InvalidSymbolFormatException;
import com.codecool.stockexchange.exception.user.InvalidUserException;
import com.codecool.stockexchange.exception.trade.SymbolNotFoundException;
import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@CrossOrigin
public class TradingController {

    @Autowired
    TradingService tradingService;

    @PostMapping("/trade/{user_id}")
    public OrderStatus postOrder(@PathVariable Long user_id, @RequestBody @Validated Order order){
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
            return tradingService.handleOrder(order, user_id);
        }
    }


    @ExceptionHandler(InvalidSymbolFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidSymbolFormat(InvalidSymbolFormatException e) { return new ErrorMessage(e); }

    @ExceptionHandler(SymbolNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleSymbolNotFound(SymbolNotFoundException e) {
        return new ErrorMessage(e);
    }

    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidUser(InvalidUserException e) {
        return new ErrorMessage(e);
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidOrderStatus(InvalidOrderStatusException e) { return new ErrorMessage(e); }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidNumberFormat(NumberFormatException e) { return new ErrorMessage(e); }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMessageNotReadable(HttpMessageNotReadableException e) {
        return new ErrorMessage(
                "Invalid data provided for the order. Please check the selected paramaters!",
                e.getClass().getSimpleName());
    }
}
