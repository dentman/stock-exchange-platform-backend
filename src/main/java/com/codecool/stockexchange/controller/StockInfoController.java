package com.codecool.stockexchange.controller;

import javax.servlet.http.HttpServletResponse;

import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.service.StockInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockInfoController {

    @Autowired
    StockInfoService stockInfoService;

    @GetMapping("/quote/{symbol}")
    public Quote getStockData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return stockInfoService.findFirstBySymbol(symbol);
    }

    // @GetMapping("/chart/{symbol}")
    // public ChartDataPoint[] getChartData(@PathVariable String symbol,
    // HttpServletResponse response) {
    // response.addHeader("Access-Control-Allow-Origin", "*");
    // return stockInfoService.findFirstBySymbol(symbol);
    // }
}
