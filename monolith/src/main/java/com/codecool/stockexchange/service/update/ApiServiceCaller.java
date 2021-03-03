package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiServiceCaller {


    private final StockRepository stockRepository;
    private final RSocketRequester client;


    @Autowired
    public ApiServiceCaller(StockRepository stockRepository, RSocketRequester client) {
        this.stockRepository = stockRepository;
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
        }
    }
}
