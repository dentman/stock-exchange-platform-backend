package com.codecool.priceudpateservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceChange {
    private final String symbol;
    private final Double change;
}
