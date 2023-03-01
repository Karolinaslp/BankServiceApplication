package org.kaczucha.account.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kaczucha.account.application.port.CurrencyServiceUseCase;
import org.kaczucha.account.application.dto.CurrencyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
