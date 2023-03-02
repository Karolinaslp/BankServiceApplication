package org.kaczucha.account.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kaczucha.account.application.dto.CurrencyResponse;
import org.kaczucha.account.application.port.CurrencyServiceUseCase;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService implements CurrencyServiceUseCase {
    private final RestTemplate restTemplate;
    private static final String EXCHANGE_URL = "https://api.apilayer.com/exchangerates_data/";
    private static final String API_KEY = "ZtmmALl5jsKkIGZn7Kb4fGXaT4iW7z9Z";

    public CurrencyResponse getCurrencyRates() {
        ResponseEntity<CurrencyResponse> response = restTemplate.getForEntity(
                EXCHANGE_URL + "latest?apikey={apiKey}&symbols=EUR%2CGBP%2CPLN&base=USD",
                CurrencyResponse.class, API_KEY);
        return response.getBody();
    }

    public String exchange(String from, String to, BigDecimal amount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(
                EXCHANGE_URL + "convert?to={to}&from={from}&amount={amount}&apikey={apiKey}",
                HttpMethod.GET,
                entity,
                String.class,
                to, from, amount,
                API_KEY);
        //TODO: deserialize to Exchange response
//        ResponseEntity<ExchangeResponse> response = restTemplate.exchange(
//                EXCHANGE_URL + "convert?to={to}&from={from}&amount={amount}&apikey={apiKey}", HttpMethod.GET, entity,
//                ExchangeResponse.class, from, to, amount,API_KEY);
        log.info(response.toString());
      return response.getBody();
    }
}
