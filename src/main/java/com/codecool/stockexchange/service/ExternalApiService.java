package com.codecool.stockexchange.service;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItem;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.apimodel.video.Video;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private String apiFruzsi = "pk_aa5a4316080144f4a68c7c8d27f5a360";

    public Quote getQuoteBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<Quote> quoteResponseEntity = template.exchange(
                String.format("https://cloud.iexapis.com/stable/stock/%s/quote?token=%s", symbol, apiFruzsi),
                HttpMethod.GET, null, Quote.class);
        return quoteResponseEntity.getBody();
    }

    public ChartDataPoint[] getChartDataBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<ChartDataPoint[]> chartResponseEntity = template.exchange(
                String.format("https://cloud.iexapis.com/stable/stock/%s/chart/5d?token=%s", symbol, apiFruzsi),
                HttpMethod.GET, null, ChartDataPoint[].class);
        return chartResponseEntity.getBody();
    }

    public NewsItem[] getNewsBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<NewsItem[]> newsResponseEntity = template.exchange(
                String.format("https://cloud.iexapis.com/stable/stock/%s/news/last/5?token=%s", symbol, apiFruzsi),
                HttpMethod.GET, null, NewsItem[].class);
        return newsResponseEntity.getBody();
    }

    public Video getVideoBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<Video> videoResponseEntity = template.exchange(String.format(
                "https://youtube.googleapis.com/youtube/v3/search?maxResults=25&q=%s,stock&key=AIzaSyAe01pCr4EnVpSvnImAUSqRrSZAlQxAN5I",
                symbol), HttpMethod.GET, null, Video.class);
        return videoResponseEntity.getBody();
    }
}
