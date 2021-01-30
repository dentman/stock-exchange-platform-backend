package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.entity.NewsItem;
import com.codecool.stockexchange.service.StockInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class StockInfoController {

    @Autowired
    StockInfoService stockInfoService;

    @GetMapping("/quote/{symbol}")
    public Quote getStockData(@PathVariable String symbol) {
        return stockInfoService.findFirstBySymbol(symbol);
    }

    @GetMapping("/chart/{symbol}")
    public ChartDataPoint[] getChartData(@PathVariable String symbol) {
        return stockInfoService.getChartDataPoints(symbol);
    }

    @GetMapping("/news/{symbol}")
    public NewsItemAPI[] getNewsData(@PathVariable String symbol, HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        return stockInfoService.findNewsBySymbol(symbol);
    }
}
