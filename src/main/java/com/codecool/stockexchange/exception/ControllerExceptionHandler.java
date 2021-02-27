package com.codecool.stockexchange.exception;

import com.codecool.stockexchange.exception.resource.ResourceNotFoundException;
import com.codecool.stockexchange.exception.trade.InvalidOrderStatusException;
import com.codecool.stockexchange.exception.trade.InvalidSymbolFormatException;
import com.codecool.stockexchange.exception.trade.SymbolNotFoundException;
import com.codecool.stockexchange.exception.user.InvalidUserException;
import com.codecool.stockexchange.exception.user.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


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

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleUserExistsException(UserExistsException e){return new ErrorMessage(e);}

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleResourceNotFound(ResourceNotFoundException e){ return new ErrorMessage(e); }
}
