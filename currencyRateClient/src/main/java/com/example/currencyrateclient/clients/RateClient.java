package com.example.currencyrateclient.clients;

import com.example.currencyrateclient.model.CurrencyRate;

import java.time.LocalDate;

public interface RateClient {
    CurrencyRate getCurrencyRate(String currency, LocalDate date);
}
