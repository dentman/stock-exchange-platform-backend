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
public class ApiService {

    public Quote getQuoteBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<Quote> quoteResponseEntity = template.exchange(String.format("https://cloud.iexapis.com/stable/stock/%s/quote?token=pk_04d8fce4b22a41bb815e8f7fc13944bc", symbol), HttpMethod.GET, null, Quote.class);
        return quoteResponseEntity.getBody();
    }

    public ChartDataPoint[] getChartDataBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<ChartDataPoint[]> chartResponseEntity = template.exchange(String.format("https://cloud.iexapis.com/stable/stock/%s/chart/5d?token=pk_04d8fce4b22a41bb815e8f7fc13944bc", symbol), HttpMethod.GET, null, ChartDataPoint[].class);
        return chartResponseEntity.getBody();
    }

    public NewsItem[] getNewsBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<NewsItem[]> newsResponseEntity = template.exchange(String.format("https://cloud.iexapis.com/stable/stock/%s/news/last/5?token=pk_04d8fce4b22a41bb815e8f7fc13944bc", symbol), HttpMethod.GET, null, NewsItem[].class);
        return newsResponseEntity.getBody();
    }

    public Video getVideoBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<Video> videoResponseEntity = template.exchange(String.format("https://youtube.googleapis.com/youtube/v3/search?maxResults=25&q=%s,stock&key=AIzaSyAe01pCr4EnVpSvnImAUSqRrSZAlQxAN5I", symbol), HttpMethod.GET, null, Video.class);
        return videoResponseEntity.getBody();
    }
}
