package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.stock.Stock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findFirstBySymbol(String symbol);
    Optional<Stock> findBySymbol(String symbol);

}
