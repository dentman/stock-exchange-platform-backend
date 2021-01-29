package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.StockInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {

}
