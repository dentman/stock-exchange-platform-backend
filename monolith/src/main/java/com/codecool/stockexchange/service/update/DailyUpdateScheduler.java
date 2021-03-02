package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.entity.StockBaseData;
import com.codecool.stockexchange.repository.StockBaseDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyUpdateScheduler {

    private final StockBaseDataRepository stockBaseDataRepository;
    private final StockUpdateService stockUpdateService;

    @Autowired
    public DailyUpdateScheduler(StockBaseDataRepository stockBaseDataRepository, StockUpdateService stockUpdateService) {
        this.stockBaseDataRepository = stockBaseDataRepository;
        this.stockUpdateService = stockUpdateService;
    }

    @Scheduled(cron = "${schedule.cron}")
    public void saveOrUpdate() {
        List<StockBaseData> stocks = stockBaseDataRepository.findAll();
        for (StockBaseData stock : stocks) {
            stockUpdateService.handleStockUpdate(stock.getSymbol());
        }
    }
}
