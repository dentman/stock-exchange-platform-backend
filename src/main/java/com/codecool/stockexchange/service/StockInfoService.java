package com.codecool.stockexchange.service;

import com.codecool.stockexchange.apimodel.Quote;
import com.codecool.stockexchange.entity.StockInfo;
import com.codecool.stockexchange.repository.StockInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockInfoService {

    @Autowired
    StockInfoRepository stockInfoRepository;

    public Quote findFirstBySymbol(String symbol) {
        StockInfo stockInfo = stockInfoRepository.findFirstBySymbol(symbol);
        return Quote.createQuoute(stockInfo);
    }

}
