package org.karolina.account.web;

import lombok.RequiredArgsConstructor;
import org.karolina.account.application.port.CurrencyServiceUseCase;
import org.karolina.account.web.dto.CurrencyResponse;
import org.karolina.account.web.dto.ExchangeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/currency")
public class CurrencyController {

    private CurrencyServiceUseCase service;

    @GetMapping(path = "/{baseCurrency}")
    public ResponseEntity<CurrencyResponse> getRates(@RequestParam(value = "baseCurrency") String base) {
        CurrencyResponse currencyRates = service.getCurrencyRates(base);
        return new ResponseEntity<>(currencyRates, HttpStatus.FOUND);
    }

    @GetMapping(path = "/exchange")
    public ResponseEntity<ExchangeResponse> exchange(@RequestParam BigDecimal amount, @RequestParam String from, @RequestParam String to) {
        ExchangeResponse exchangeResponse = service.exchange(amount, from, to);
        return new ResponseEntity<>(exchangeResponse, HttpStatus.ACCEPTED);
    }
}
