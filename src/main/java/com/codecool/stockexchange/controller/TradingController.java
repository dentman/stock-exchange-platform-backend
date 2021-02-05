package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.Symbol;
import com.codecool.stockexchange.exception.trade.InvalidOrderStatusException;
import com.codecool.stockexchange.exception.trade.InvalidSymbolFormatException;
import com.codecool.stockexchange.exception.user.InvalidUserException;
import com.codecool.stockexchange.exception.trade.SymbolNotFoundException;
import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.UserRepository;
import com.codecool.stockexchange.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
public class TradingController {

    @Autowired
    TradingService tradingService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/trade/{user_id}")
    public OrderStatus postOrder(@PathVariable Long user_id, @RequestBody @Validated Order order){
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()){
            String symbol = order.getSymbol();
            Symbol symbols = new Symbol();
            Set<String> stocks = symbols.getStocklist().keySet();
            if (symbol == null || symbol.equals("") || symbol.chars().anyMatch(c -> !Character.isUpperCase((char) c))) {
                throw new InvalidSymbolFormatException();
            }
            else if (!stocks.contains(order.getSymbol())) {
                throw new SymbolNotFoundException(order.getSymbol());
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
                order.setUser(userOptional.get());
                order.setDate(LocalDateTime.now());
                return tradingService.handleOrder(order);
            }
        } else {
            throw new InvalidUserException();
        }
    }


    @ExceptionHandler(InvalidSymbolFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidSymbolFormat(InvalidSymbolFormatException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(SymbolNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSymbolNotFound(SymbolNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidUser(InvalidUserException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidOrderStatus(InvalidOrderStatusException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidNumberFormat(NumberFormatException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMessageNotReadable(HttpMessageNotReadableException exception) {
        return "Invalid data provided for the order. Please check the selected paramaters!";
    }
}
