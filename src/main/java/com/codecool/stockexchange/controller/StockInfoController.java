package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.apimodel.video.Video;
import com.codecool.stockexchange.entity.stockinfo.StockInfo;
import com.codecool.stockexchange.entity.stockinfo.VideoLink;
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

    @GetMapping("/stock/{symbol}")
    public StockInfo getStockInfo(@PathVariable String symbol){
        return stockInfoService.findStockInfoBySymbol(symbol);
    }

    @GetMapping("/quote/{symbol}")
    public Quote getStockData(@PathVariable String symbol) {
        return stockInfoService.findFirstBySymbol(symbol);
    }

    @GetMapping("/chart/{symbol}")
    public ChartDataPoint[] getChartData(@PathVariable String symbol) {
        return stockInfoService.getChartDataPoints(symbol);
    }

    @GetMapping("/news/{symbol}")
    public NewsItemAPI[] getNewsData(@PathVariable String symbol){
        return stockInfoService.findNewsBySymbol(symbol);
    }

    @GetMapping("/videos/{symbol}")
    public List<VideoLink> getVideoData(@PathVariable String symbol){ return stockInfoService.findVideosBySymbol(symbol);}

    @GetMapping("/videos/{symbol}/random")
    public VideoLink getRandomVideo(@PathVariable String symbol){ return stockInfoService.findRandomVideoForSymbol(symbol);}
}
