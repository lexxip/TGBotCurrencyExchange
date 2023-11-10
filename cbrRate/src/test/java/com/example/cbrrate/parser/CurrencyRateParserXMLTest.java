package com.example.cbrrate.parser;

import com.example.cbrrate.model.CurrencyRate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CurrencyRateParserXMLTest {

    @Test
    void parseTest() throws URISyntaxException, IOException {
        // given
        var parser = new CurrencyRateParserXML();
        var uri = ClassLoader.getSystemResource("XML_daily.asp").toURI();
        var ratesXml = Files.readString(Paths.get(uri), Charset.forName("Windows-1251"));

        // when
        var rates = parser.parse(ratesXml);

        // then
        assertThat(rates.size()).isEqualTo(43);
        assertThat(rates.contains(getUSDRate())).isTrue();
        assertThat(rates.contains(getEURRate())).isTrue();
        assertThat(rates.contains(getJPYRate())).isTrue();
    }

    CurrencyRate getUSDRate() {
        return CurrencyRate.builder()
                .numCode("840")
                .charCode("USD")
                .nominal("1")
                .name("Доллар США")
                .value("92,1973")
                .build();
    }

    CurrencyRate getEURRate() {
        return CurrencyRate.builder()
                .numCode("978")
                .charCode("EUR")
                .nominal("1")
                .name("Евро")
                .value("98,4403")
                .build();
    }

    CurrencyRate getJPYRate() {
        return CurrencyRate.builder()
                .numCode("392")
                .charCode("JPY")
                .nominal("100")
                .name("Японских иен")
                .value("61,2159")
                .build();
    }
}