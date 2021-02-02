package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.PortfolioItem;
import com.codecool.stockexchange.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;

    @GetMapping("/transactions/{user_id}")
    public List<StockTransaction> getTransactionsForUser(@PathVariable("user_id") Long user_id){
        return portfolioService.getTransactionsForUser(user_id);
    }

    @GetMapping("/portfolio-items/{user_id}")
    public List<PortfolioItem> getPortfolioItemsForUser(@PathVariable("user_id") Long user_id){
        return portfolioService.getPortfolioItemsForUser(user_id);
    }



}
