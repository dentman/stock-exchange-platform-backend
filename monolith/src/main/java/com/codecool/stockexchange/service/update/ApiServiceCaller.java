package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.repository.StockRepository;
import io.rsocket.Payload;
import io.rsocket.core.RSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.PayloadUtils;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceCaller {


    private final StockRepository stockRepository;
    private final RestTemplate restTemplate;
    private final RSocketRequester client;


    @Autowired
    public ApiServiceCaller(StockRepository stockRepository, RestTemplate restTemplate, RSocketRequester client) {
        this.stockRepository = stockRepository;
        this.restTemplate = restTemplate;
        this.client = client;
    }

    @Transactional
    public void callService(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        if (stock == null) {
            stock = client.route("create")
                    .data(symbol)
                    .retrieveMono(Stock.class)
                    .block();
        } else {
            stock = client.route("update")
                    .data(stock)
                    .retrieveMono(Stock.class)
                    .block();
            System.out.println(stock.toString());
        }
    }
}
