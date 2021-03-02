package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiServiceCaller {


    private final StockRepository stockRepository;
    private final RestTemplate restTemplate;


    @Autowired
    public ApiServiceCaller(StockRepository stockRepository, RestTemplate restTemplate) {
        this.stockRepository = stockRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void callService(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        if (stock == null) {
            // "apiservice/create/" + symbol
            stock = restTemplate.getForEntity("http://apiservice/create/" + symbol, Stock.class).getBody();
        } else {
            // "apiservice/update"
            HttpEntity<Stock> entity = new HttpEntity<>(stock);
            ResponseEntity<Stock> response = restTemplate.exchange("http://apiservice/update", HttpMethod.GET, entity, Stock.class);
            stock = response.getBody();
        }
    }
}
