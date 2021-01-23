package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.Stock;
import com.codecool.stockexchange.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class QuoteController {

    @Autowired
    private Symbol stocklist;

    @GetMapping("/quote/{symbol}")
    public Stock getStockData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println(stocklist.getStock(symbol));
        return stocklist.getStock(symbol);
    }

}
