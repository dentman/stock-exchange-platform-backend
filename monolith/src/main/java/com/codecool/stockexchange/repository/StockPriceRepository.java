package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.stock.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
}
