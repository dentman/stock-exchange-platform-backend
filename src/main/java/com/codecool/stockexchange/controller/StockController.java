package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.entity.StockBaseData;
import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin(value = "http://localhost:3000")
public class StockController {

    @Autowired
    StockService stockService;

    @GetMapping("/stock/{symbol}")
    public Stock getStock(@PathVariable String symbol){
        return stockService.findStockBySymbol(symbol);
    }

    @GetMapping("/quote/{symbol}")
    public Quote getStockData(@PathVariable String symbol) {
        return stockService.findFirstBySymbol(symbol);
    }

    @GetMapping("/stock-base-data/list")
    public List<StockBaseData> getStockBaseData(){
        return stockService.getStockBaseData();
    }
}
