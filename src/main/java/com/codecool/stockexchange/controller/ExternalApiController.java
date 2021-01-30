package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItem;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.apimodel.video.Video;
import com.codecool.stockexchange.service.ExternalApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ExternalApiController {

    @Autowired
    private ExternalApiService apiService;

    @GetMapping("/external/quote/{symbol}")
    public Quote getStockData(@PathVariable String symbol) {
        return apiService.getQuoteBySymbol(symbol);
    }

    @GetMapping("/external/chart/{symbol}")
    public ChartDataPoint[] getChartData(@PathVariable String symbol) {
        return apiService.getChartDataBySymbol(symbol);
    }

    @GetMapping("/news/{symbol}")
    public NewsItem[] getNewsData(@PathVariable String symbol) {
        return apiService.getNewsBySymbol(symbol);
    }

    @GetMapping("/videos/{symbol}")
    public Video getVideoData(@PathVariable String symbol) {
        return apiService.getVideoBySymbol(symbol);
    }

}
