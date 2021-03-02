package com.codecool.apiservice.controller;

import com.codecool.apiservice.resultmodel.Stock;
import com.codecool.apiservice.service.update.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class SocketUpdateController {

    @Autowired
    private StockUpdateService stockUpdateService;

    @MessageMapping("create")
    Mono<Stock> createStock (String symbol) {
        Stock out = stockUpdateService.createNewStock(symbol);
        return Mono.just(out);
    }


    @MessageMapping("update")
    Mono<Stock> updateStock (Stock stock) {
        Stock out = stockUpdateService.updateStock(stock);
        return Mono.just(out);
    }
}
