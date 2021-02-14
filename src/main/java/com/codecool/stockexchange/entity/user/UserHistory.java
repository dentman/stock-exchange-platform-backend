package com.codecool.stockexchange.entity.user;

import com.codecool.stockexchange.entity.trade.OrderDirection;
import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserHistory {

    @Id
    @GeneratedValue
    private Long id;
    private Long transactionId;
    private LocalDateTime transactionTime;
    private BigDecimal balanceAfter;
    private int stockCountAfter;
    private String symbol;
    private int stockChange;
    private BigDecimal stockPrice;

    @ManyToOne
    private User user;

}
