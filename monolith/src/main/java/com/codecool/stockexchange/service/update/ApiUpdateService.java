package com.codecool.stockexchange.service.update;

import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.stock.StockPrice;
import com.codecool.stockexchange.repository.StockPriceRepository;
import com.codecool.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiUpdateService {

    private final StockRepository stockRepository;
    private final StockPriceRepository stockPriceRepository;
    private final RSocketRequester client;


    @Autowired
    public ApiUpdateService(StockRepository stockRepository, StockPriceRepository stockPriceRepository, RSocketRequester client) {
        this.stockRepository = stockRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.client = client;
    }

    @Transactional
    public void updateService(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol);
        if (stock == null) {
            stock = client.route("create")
                    .data(symbol)
                    .retrieveMono(Stock.class)
                    .block();
            stockRepository.save(stock);
        } else {
            Stock updatedStock = client.route("update")
                    .data(stock)
                    .retrieveMono(Stock.class)
                    .block();
            if (updatedStock != null && updatedStock.getStockPrices() != null) {
                updatePrices(stock.getStockPrices(), updatedStock.getStockPrices(), stock);
            }
        }
    }

    private void updatePrices(List<StockPrice> priceList, List<StockPrice> updatedPriceList, Stock stock) {
        List<Long> storedPrices = priceList
                .stream()
                .map(StockPrice::getId)
                .collect(Collectors.toList());
        updatedPriceList.stream()
                .filter(price -> !storedPrices.contains(price.getId()))
                .forEach(entity -> {
                    entity.setStock(stock);
                    stockPriceRepository.save(entity);
                });
    }
}
