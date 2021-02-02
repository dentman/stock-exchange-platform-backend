package com.codecool.stockexchange.entity.orderexception;

public class SymbolNotFoundException extends RuntimeException {

    public SymbolNotFoundException() {
        super("Stock symbol is invalid. Please check the stocks available for trading!");
    }

    public SymbolNotFoundException(String symbol) {
        super(String.format("%s symbol is invalid. Please check the stocks available for trading!", symbol));
    }
}
