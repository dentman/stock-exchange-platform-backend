package com.codecool.stockexchange.exception.trade;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException() {
        super("Status of incoming trade order is invalid!");
    }

    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
