package org.kaczucha.account.web;

import lombok.RequiredArgsConstructor;
import org.kaczucha.account.application.dto.CurrencyResponse;
import org.kaczucha.account.application.dto.ExchangeResponse;
import org.kaczucha.account.application.port.CurrencyServiceUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyController {
    @Autowired
    private CurrencyServiceUseCase service;

    @GetMapping(path = "/api/latest")
    public ResponseEntity<CurrencyResponse> getRates(@RequestParam String base) {
        CurrencyResponse currencyRates = service.getCurrencyRates(base);
        return new ResponseEntity<>(currencyRates, HttpStatus.FOUND);
    }

    @GetMapping(path = "/api/convert")
    public ResponseEntity<ExchangeResponse> exchange(@RequestParam String from,@RequestParam String to,@RequestParam BigDecimal amount) {
       ExchangeResponse exchangeResponse = service.exchange(from, to, amount);
        return new ResponseEntity<>(exchangeResponse, HttpStatus.ACCEPTED);
    }
}
