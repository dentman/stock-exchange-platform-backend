package com.codecool.stockexchange.service;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.apimodel.video.Video;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalApiService {

    private String apiFruzsi = "pk_376df3da39174f00af08424cb3bcd321";
    private String baseUrl = "https://cloud.iexapis.com/stable/stock";

    public Quote getQuoteBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<Quote> quoteResponseEntity = template.exchange(
                String.format("%s/%s/quote?token=%s", baseUrl, symbol, apiFruzsi),
                HttpMethod.GET, null, Quote.class);
        return quoteResponseEntity.getBody();
    }

    public ChartDataPoint[] getChartDataBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<ChartDataPoint[]> chartResponseEntity = template.exchange(
                String.format("https://cloud.iexapis.com/stable/stock/%s/chart/1m?token=%s", symbol, apiFruzsi),
                HttpMethod.GET, null, ChartDataPoint[].class);
        return chartResponseEntity.getBody();
    }

    public ChartDataPoint[] getChartDataBySymbolForDays(String symbol, long days) {
        String limit = days == 1? "chartLast=1" : "";
        RestTemplate template = new RestTemplate();
        ResponseEntity<ChartDataPoint[]> chartResponseEntity = template.exchange(
                String.format("%s/%s/chart/%sd?%s&token=%s", baseUrl, symbol, days, limit, apiFruzsi),
                HttpMethod.GET, null, ChartDataPoint[].class);
        return chartResponseEntity.getBody();
    }

    public List<NewsItemAPI> getNewsBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<NewsItemAPI[]> newsResponseEntity = template.exchange(
                String.format("https://cloud.iexapis.com/stable/stock/%s/news/last/5?token=%s", symbol, apiFruzsi),
                HttpMethod.GET, null, NewsItemAPI[].class);
        NewsItemAPI[] newsItemAPIS = newsResponseEntity.getBody();
        return Arrays.stream(newsItemAPIS).filter(n -> n.getLang().equals("en")).collect(Collectors.toList()); // only englished saved to db
    }

    public Video getVideoBySymbol(String symbol) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<Video> videoResponseEntity = template.exchange(String.format(
                "https://youtube.googleapis.com/youtube/v3/search?maxResults=25&q=%s,stock&key=AIzaSyAe01pCr4EnVpSvnImAUSqRrSZAlQxAN5I",
                symbol), HttpMethod.GET, null, Video.class);
        return videoResponseEntity.getBody();
    }
}
