package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TransactionRepository extends JpaRepository<StockTransaction, Long> {

    @Query("SELECT t FROM StockTransaction t WHERE t.order IN (SELECT oi.id FROM Order oi WHERE oi.user = :user AND oi.status = 'COMPLETED')")
    List<StockTransaction> getTransactionsForUser(@Param("user") User user);

    @Query("SELECT t FROM StockTransaction t WHERE t.symbol = :symbol AND t.order IN (SELECT oi.id FROM Order oi WHERE oi.user = :user AND oi.status = 'COMPLETED')")
    List<StockTransaction> getTransactionsForUserForSymbol(@Param("user") User user, @Param("symbol") String symbol);
}
