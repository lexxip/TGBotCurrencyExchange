package com.example.cbrrate.requester;

import com.example.cbrrate.exception.RequesterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class CbrRequesterImpl implements CbrRequester {
    @Override
    public String getRatesAsXML(String url) {
        try {
            log.info("Request for url: {}", url);
            var client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            log.error("cbr request error, url: {}", url);
            throw new RequesterException(e);
        }
    }
}
