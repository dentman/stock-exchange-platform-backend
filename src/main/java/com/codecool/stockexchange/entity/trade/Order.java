package com.codecool.stockexchange.entity.trade;

import com.codecool.stockexchange.entity.user.User;
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
@Table(name = "order_item")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String symbol;
    private BigDecimal limitPrice;
    @Enumerated( EnumType.STRING )
    private OrderDirection direction;
    @Enumerated( EnumType.STRING )
    private OrderStatus status;
    private int count;
    private LocalDateTime date;

    @OneToOne( mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private StockTransaction stockTransaction;


    public StockTransaction createTransaction(BigDecimal stockPrice) {
        StockTransaction transaction = new StockTransaction();
        transaction.setOrder(this);
        setStockTransaction(transaction);
        transaction.setSymbol(getSymbol());
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setStockPrice(stockPrice);
        switch (getDirection()) {
            case BUY:
                transaction.setAccountBalanceChange(stockPrice.multiply(BigDecimal.valueOf(-1 * getCount())));
                transaction.setStockChange(getCount());
                break;
            case SELL:
                transaction.setAccountBalanceChange(stockPrice.multiply(BigDecimal.valueOf(getCount())));
                transaction.setStockChange(-1 * getCount());
                break;
        }
        return transaction;
    }

}
