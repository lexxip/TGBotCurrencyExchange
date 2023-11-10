package com.example.cbrrate.parser;

import com.example.cbrrate.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateParser {
    List<CurrencyRate> parse(String ratesAsString);
}
