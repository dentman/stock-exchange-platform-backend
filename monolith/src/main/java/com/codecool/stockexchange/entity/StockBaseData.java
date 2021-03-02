package com.codecool.stockexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockBaseData {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;
    private String companyName;

    public StockBaseData(String[] data){
        symbol = data[0];
        companyName = data[1];
    }
}
