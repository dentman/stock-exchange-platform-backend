package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.Symbol;
import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItem;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.apimodel.video.Video;
import com.codecool.stockexchange.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ApiController {

    @Autowired
    private Symbol stocklist;

    @Autowired
    private ApiService apiService;

    @GetMapping("/quote/{symbol}")
    public Quote getStockData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return apiService.getQuoteBySymbol(symbol);
    }

    @GetMapping("/chart/{symbol}")
    public ChartDataPoint[] getChartData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return apiService.getChartDataBySymbol(symbol);
    }

    @GetMapping("/news/{symbol}")
    public NewsItem[] getNewsData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return apiService.getNewsBySymbol(symbol);
    }

    @GetMapping("/videos/{symbol}")
    public Video getVideoData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return apiService.getVideoBySymbol(symbol);
    }

}
