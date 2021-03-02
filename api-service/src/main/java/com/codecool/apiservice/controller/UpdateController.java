package com.codecool.apiservice.controller;

import com.codecool.apiservice.resultmodel.Stock;
import com.codecool.apiservice.service.update.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {

    @Autowired
    private StockUpdateService stockUpdateService;

    @GetMapping("/create/{symbol}")
    public Stock createNewStock(@PathVariable String symbol) {

        Stock stock = stockUpdateService.createNewStock(symbol);
        System.out.println(stock);
        return stock;
    }

    @GetMapping("/update")
    public Stock updateStock(@RequestBody Stock stock) {

        System.out.println(stock);
        Stock out = stockUpdateService.updateStock(stock);
        return out;
    }


}
