package com.example.cbrrate.parser;

import com.example.cbrrate.exception.CurrencyRateParsingException;
import com.example.cbrrate.model.CurrencyRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD;
import static javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA;
import static javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING;
import static org.w3c.dom.Node.ELEMENT_NODE;

@Service
@Slf4j
public class CurrencyRateParserXML implements CurrencyRateParser {
    @Override
    public List<CurrencyRate> parse(String ratesAsString) {
        var rates = new ArrayList<CurrencyRate>();

        var dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(ACCESS_EXTERNAL_SCHEMA, "");

        try {
            dbf.setFeature(FEATURE_SECURE_PROCESSING, true);
            var db = dbf.newDocumentBuilder();

            try (var reader = new StringReader(ratesAsString)) {
                Document doc = db.parse(new InputSource(reader));
                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("Valute");

                for (int valueIdx = 0; valueIdx < list.getLength(); valueIdx++) {
                    var node = list.item(valueIdx);

                    if (node.getNodeType() == ELEMENT_NODE) {
                        var element = (Element) node;
                        var rate = CurrencyRate.builder()
                                .numCode(element.getElementsByTagName("NumCode").item(0).getTextContent())
                                .charCode(element.getElementsByTagName("CharCode").item(0).getTextContent())
                                .nominal(element.getElementsByTagName("Nominal").item(0).getTextContent())
                                .name(element.getElementsByTagName("Name").item(0).getTextContent())
                                .value(element.getElementsByTagName("Value").item(0).getTextContent())
                                .build();
                        rates.add(rate);
                    }

                }

            }

        } catch (Exception e) {
            throw new CurrencyRateParsingException(e);
        }

        return rates;
    }
}
