package com.codecool.stockexchange.exception.user;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("Order not authorized due to invalid user data!");
    }

    public InvalidUserException(String message) {
        super(message);
    }
}