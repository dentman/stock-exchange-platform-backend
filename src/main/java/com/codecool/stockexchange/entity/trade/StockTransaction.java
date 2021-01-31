package com.codecool.stockexchange.entity.trade;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StockTransaction {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private OrderItem order;

    private LocalDateTime transactionTime;

    private String symbol;

    private BigDecimal tradeStockPrice;

    private BigDecimal accountBalanceChange;

    private int portfolioItemChange;
}
