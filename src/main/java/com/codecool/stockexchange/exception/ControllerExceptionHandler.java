package com.codecool.stockexchange.exception;

import com.codecool.stockexchange.exception.trade.InvalidOrderStatusException;
import com.codecool.stockexchange.exception.trade.InvalidSymbolFormatException;
import com.codecool.stockexchange.exception.trade.SymbolNotFoundException;
import com.codecool.stockexchange.exception.user.InvalidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidSymbolFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidSymbolFormat(InvalidSymbolFormatException e) { return new ErrorMessage(e); }

    @ExceptionHandler(SymbolNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleSymbolNotFound(SymbolNotFoundException e) {
        return new ErrorMessage(e);
    }

    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidUser(InvalidUserException e) {
        return new ErrorMessage(e);
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidOrderStatus(InvalidOrderStatusException e) { return new ErrorMessage(e); }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidNumberFormat(NumberFormatException e) { return new ErrorMessage(e); }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMessageNotReadable(InvalidParameterException e) {
        return new ErrorMessage(
                "Invalid data provided for the order. Please check the selected paramaters!",
                e.getClass().getSimpleName());
    }
}
