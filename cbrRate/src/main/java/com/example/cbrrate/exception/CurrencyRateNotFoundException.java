package com.example.cbrrate.exception;

public class CurrencyRateNotFoundException extends RuntimeException{
    public CurrencyRateNotFoundException(String message) {
        super(message);
    }
}
