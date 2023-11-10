package com.example.currencyrateclient.service;

import com.example.currencyrateclient.clients.RateClient;
import com.example.currencyrateclient.exception.CurrencyRateNotFoundException;
import com.example.currencyrateclient.model.CurrencyRate;
import com.example.currencyrateclient.model.RateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateEndpointService {
    private final Map<String, RateClient> clients;

    public CurrencyRate getCurrencyRate(RateType rateType, String currency, LocalDate date) {
        log.info("getCurrencyRate. rateType: {}, currency: {}, date: {}", rateType, currency, date);

        var client = clients.get(rateType.getServiceName());
        if (client == null) {
            var message = String.format("getCurrencyRate. Rate type not found rateType: %s", rateType);
            log.error(message);
            throw new CurrencyRateNotFoundException(message);
        }

        return client.getCurrencyRate(currency, date);
    }
}
