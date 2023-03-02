package org.kaczucha.account.application.port;

import org.kaczucha.account.application.dto.CurrencyResponse;

import java.math.BigDecimal;

public interface CurrencyServiceUseCase {
    CurrencyResponse getCurrencyRates();
    String exchange(String from, String to, BigDecimal amount);
}
