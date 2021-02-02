package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.PortfolioItem;
import com.codecool.stockexchange.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;

    @GetMapping("/transactions/{user_id}")
    public List<StockTransaction> getTransactionsForUser(@PathVariable("user_id") Long user_id){
        return portfolioService.getTransactionsForUser(user_id);
    }

    @GetMapping("/transactions/user/{user_id}/symbol/{symbol}")
    public List<StockTransaction> getTransactionsForUserPerSymbol(@PathVariable Long user_id, @PathVariable String symbol){
        return portfolioService.getTransactionsForUserPerSymbol(user_id, symbol);
    }

    @GetMapping("/portfolio-items/{user_id}")
    public List<PortfolioItem> getPortfolioItemsForUser(@PathVariable("user_id") Long user_id){
        return portfolioService.getPortfolioItemsForUser(user_id);
    }

    @GetMapping("/account/user/{user_id}")
    public Account getAccountForUser(@PathVariable Long user_id){
        Account a = portfolioService.getAccountForUser(user_id);
//        System.out.println(a);
        return a;
    }



}
