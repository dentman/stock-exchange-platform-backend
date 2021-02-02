package com.codecool.stockexchange.controller;

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
            order.setUser(userOptional.get());
            order.setDate(LocalDateTime.now());
            return tradingService.handleOrder(order);
        } else return null;
    }
}
