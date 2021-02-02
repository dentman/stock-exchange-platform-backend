package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.user.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {

    public List<PortfolioItem> findPortfolioItemsByUser_Id(Long id);
}
