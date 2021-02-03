package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.Symbol;
import com.codecool.stockexchange.exception.trade.InvalidSymbolFormatException;
import com.codecool.stockexchange.exception.user.InvalidUserException;
import com.codecool.stockexchange.exception.trade.SymbolNotFoundException;
import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.UserRepository;
import com.codecool.stockexchange.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

            if (symbol == null || symbol.equals("") || symbol.chars().anyMatch(c -> Character.isLowerCase((char) c))) {
                throw new InvalidSymbolFormatException();
            }
            else if (!stocks.contains(order.getSymbol())) {
                throw new SymbolNotFoundException(order.getSymbol());
            }
            order.setUser(userOptional.get());
            order.setDate(LocalDateTime.now());
            return tradingService.handleOrder(order);
        } else {
            throw new InvalidUserException();
        }
    }


    @ExceptionHandler
    public String handleInvalidSymbolFormat(InvalidSymbolFormatException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler
    public String handleSymbolNotFound(SymbolNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler
    public String handleInvalidUser(InvalidUserException exception) {
        return exception.getMessage();
    }

}
