package com.codecool.stockexchange.service.stomp;

import com.codecool.stockexchange.entity.stock.StockChange;
import com.codecool.stockexchange.entity.stock.StockChangeEvent;
import com.codecool.stockexchange.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class StockChangeClientService {

    private final StockService stockService;
    private final SimpMessageSendingOperations sendingOperations;

    @Autowired
    public StockChangeClientService(StockService stockService, SimpMessageSendingOperations sendingOperations) {
        this.stockService = stockService;
        this.sendingOperations = sendingOperations;
    }

    @EventListener
    public void sendDataWhenSubscribed(SessionSubscribeEvent event) {
        List<StockChange> stockChanges = stockService.getStockChangeList();
        Map<String, ArrayList<String>> headers = (Map<String, ArrayList<String>>) event.getMessage().getHeaders().get("nativeHeaders");
        String dest = headers.get("destination").get(0);
        if (dest.equals("/stock/all")) {
            sendingOperations.convertAndSend( "/stock/all", stockChanges);
        }
    }

    @EventListener
    public void stockPriceChangeListener(StockChangeEvent event) {
        StockChange stockChange = (StockChange) event.getSource();
        sendingOperations.convertAndSend( "/stock/data", stockChange);
    }
}
