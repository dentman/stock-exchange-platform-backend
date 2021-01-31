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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String symbol;
    private BigDecimal limitPrice;
    @Enumerated( EnumType.STRING )
    private Direction direction;
    @Enumerated( EnumType.STRING )
    private Status status;
    private int count;
    private LocalDateTime date;

    @OneToOne( mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private StockTransaction stockTransaction;

}
