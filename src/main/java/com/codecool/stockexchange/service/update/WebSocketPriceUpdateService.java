package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.entity.stock.StockChange;
import com.codecool.stockexchange.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketPriceUpdateService {

    private final SimpMessagingTemplate messagingTemplate;
    private final StockService stockService;

    @Autowired
    public WebSocketPriceUpdateService(SimpMessagingTemplate messagingTemplate, StockService stockService) {
        this.messagingTemplate = messagingTemplate;
        this.stockService = stockService;
    }

    @Scheduled(fixedRate = 3000)
    public void testSendServerMessage() {
        List<StockChange> stockChanges = stockService.getStockChangeList();
        messagingTemplate.convertAndSend("/stock-data", stockChanges);
    }

}
