package org.kaczucha.account.web;

import lombok.RequiredArgsConstructor;
import org.kaczucha.account.application.dto.CurrencyResponse;
import org.kaczucha.account.application.port.CurrencyServiceUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyController {
    @Autowired
    private CurrencyServiceUseCase service;

    @GetMapping(path = "/api/currency")
    public ResponseEntity<CurrencyResponse> getRates() {
        CurrencyResponse currencyRates = service.getCurrencyRates();
        return new ResponseEntity<>(currencyRates, HttpStatus.FOUND);
    }
}
