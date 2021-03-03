package com.codecool.stockexchange.entity.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceChange {
    private final String symbol;
    private final Double change;
}
