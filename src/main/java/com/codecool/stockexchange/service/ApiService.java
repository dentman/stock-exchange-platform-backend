package com.codecool.stockexchange.service;

import com.codecool.stockexchange.quote.ChartData;
import com.codecool.stockexchange.quote.ChartDataPoint;
import com.codecool.stockexchange.quote.Quote;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.CharacterData;


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
}
