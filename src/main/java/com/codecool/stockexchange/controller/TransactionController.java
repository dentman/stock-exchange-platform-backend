package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactions/user/{user_id}/symbol/{symbol}")
    public List<StockTransaction> getTransactionsForUserPerSymbol(@PathVariable Long user_id, @PathVariable String symbol){
        return transactionService.getTransactionsForUserPerSymbol(user_id, symbol);
    }
}
