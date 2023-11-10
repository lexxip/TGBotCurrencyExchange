package com.example.currencyrateclient.clients;

import com.example.currencyrateclient.config.CbrRateClientConfig;
import com.example.currencyrateclient.exception.HttpClientException;
import com.example.currencyrateclient.exception.RateClientException;
import com.example.currencyrateclient.model.CurrencyRate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service("cbr")
@RequiredArgsConstructor
@Slf4j
public class CbrRateClient implements RateClient {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CbrRateClientConfig config;
    private final com.example.currencyrateclient.clients.HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Override
    public CurrencyRate getCurrencyRate(String currency, LocalDate date) {
        log.info("getCurrencyRate currency: {}, date: {}", currency, date);
        var urlWithParams = String.format("%s/%s/%s", config.getUrl(), currency, DATE_FORMATTER.format(date));

        try {
            var response = httpClient.performRequest(urlWithParams);
            return objectMapper.readValue(response, CurrencyRate.class);
        } catch (HttpClientException e) {
            throw new RateClientException("Error from Cbr Client host:" + e.getMessage());
        } catch (Exception e) {
            log.error("Getting currencyRate error, currency:{}, date:{}", currency, date, e);
            throw new RateClientException("Can't get currencyRate. currency:" + currency + ", date:" + date);
        }
    }
}
