package com.codecool.stockexchange.entity.orderexception;

public class InvalidSymbolFormatException extends RuntimeException {

    public InvalidSymbolFormatException() {
        super("Symbol format is invalid. Only upper case letters should be used!");
    }

    public InvalidSymbolFormatException(String message) {
        super(message);
    }
}
