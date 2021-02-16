package com.codecool.stockexchange.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private String message;
    private String exception;

    public ErrorMessage(Exception exception){
        this.message = exception.getMessage();
        this.exception = exception.getClass().getSimpleName();
    }
}
