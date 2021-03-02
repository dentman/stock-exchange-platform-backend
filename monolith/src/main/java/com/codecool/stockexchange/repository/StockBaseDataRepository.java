package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.StockBaseData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockBaseDataRepository extends JpaRepository<StockBaseData, Long> {
}
