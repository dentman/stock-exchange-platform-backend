package com.codecool.stockexchange.controller;

import javax.servlet.http.HttpServletResponse;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.apimodel.video.Video;
import com.codecool.stockexchange.service.ExternalApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExternalApiController {

    @Autowired
    private ExternalApiService apiService;

    @GetMapping("/external/quote/{symbol}")
    public Quote getStockData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return apiService.getQuoteBySymbol(symbol);
    }

    @GetMapping("/external/chart/{symbol}")
    public ChartDataPoint[] getChartData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return apiService.getChartDataBySymbol(symbol);
    }

    @GetMapping("/external/news/{symbol}")
    public List<NewsItemAPI> getNewsData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return apiService.getNewsBySymbol(symbol);
    }

    @GetMapping("/videos/{symbol}")
    public Video getVideoData(@PathVariable String symbol, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return apiService.getVideoBySymbol(symbol);
    }

}
