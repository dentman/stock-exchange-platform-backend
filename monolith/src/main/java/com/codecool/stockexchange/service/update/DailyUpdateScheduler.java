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
    private final ApiUpdateService apiUpdateService;

    @Autowired
    public DailyUpdateScheduler(StockBaseDataRepository stockBaseDataRepository, ApiUpdateService apiUpdateService) {
        this.stockBaseDataRepository = stockBaseDataRepository;
        this.apiUpdateService = apiUpdateService;
    }

    @Scheduled(cron = "${schedule.cron}")
    public void saveOrUpdate() {
        List<StockBaseData> stocks = stockBaseDataRepository.findAll();
        for (StockBaseData stock : stocks) {
            apiUpdateService.updateService(stock.getSymbol());
        }
    }
}
