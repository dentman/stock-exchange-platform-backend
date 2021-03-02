package com.codecool.stockexchange.entity.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StockPrice {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;
    private LocalDate date;
    private BigDecimal price;
    private boolean isClosing = true;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Stock stock;

}
