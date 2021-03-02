package com.codecool.stockexchange.exception.trade;

public class InvalidSymbolFormatException extends RuntimeException {

    public InvalidSymbolFormatException() {
        super("Symbol format is invalid. Only upper case letters should be used!");
    }

    public InvalidSymbolFormatException(String message) {
        super(message);
    }
}
