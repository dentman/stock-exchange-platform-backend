package com.codecool.stockexchange.exception.user;

public class UserExistsException extends RuntimeException {

    public UserExistsException() {super("Username already exists");}

    public UserExistsException(String message){super(message);}
}
