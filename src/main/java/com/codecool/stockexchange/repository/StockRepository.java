package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.stock.Stock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

    public Stock findFirstBySymbol(String symbol);

}
