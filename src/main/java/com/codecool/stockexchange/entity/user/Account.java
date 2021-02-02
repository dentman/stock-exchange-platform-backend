package com.codecool.stockexchange.entity.user;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.StockTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    //(mappedBy = "account", cascade = CascadeType.PERSIST)
    @OneToOne
    private User user;

    private BigDecimal balance;
    private String currency;

    public boolean checkAvailableFunds(Order order, BigDecimal stockPrice) {
        BigDecimal requiredBalance = stockPrice.multiply(BigDecimal.valueOf(order.getCount()));
        return getBalance().compareTo(requiredBalance) >= 0;
    }

    public void transferOrderFunding(StockTransaction transaction) {
        setBalance(balance.add(transaction.getAccountBalanceChange()));
    }
}
