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
    private Order order;

    private LocalDateTime transactionTime;

    private String symbol;

    private BigDecimal stockPrice;

    private int stockChange;

    private BigDecimal accountBalanceChange;
}
