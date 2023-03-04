package org.kaczucha.account.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kaczucha.GlobalConfigurationProperties;
import org.kaczucha.account.application.port.CurrencyServiceUseCase;
import org.kaczucha.account.web.dto.CurrencyResponse;
import org.kaczucha.account.web.dto.ExchangeResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService implements CurrencyServiceUseCase {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final GlobalConfigurationProperties configurationProperties;

    public CurrencyResponse getCurrencyRates(String baseCurrency) {
        HttpEntity<String> entity = getEntity();
        ResponseEntity<CurrencyResponse> response = restTemplate.exchange(
                configurationProperties.getRatesUrl(), HttpMethod.GET, entity,
                CurrencyResponse.class, baseCurrency);
        return response.getBody();
    }

    public ExchangeResponse exchange(BigDecimal amount, String from, String to) {
        HttpEntity<String> entity = getEntity();
        ResponseEntity<ExchangeResponse> response = restTemplate.exchange(
                configurationProperties.getExchangeUrl(), HttpMethod.GET, entity,
                ExchangeResponse.class, amount, from, to);
        log.info(response.toString());
        return objectMapper.convertValue(response.getBody(), ExchangeResponse.class);
    }

    private static HttpEntity<String> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>("parameters", headers);
    }
}
