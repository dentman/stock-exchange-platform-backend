package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.trade.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
}
