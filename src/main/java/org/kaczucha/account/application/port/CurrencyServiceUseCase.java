package org.kaczucha.account.application.port;

import org.kaczucha.account.application.dto.CurrencyResponse;
import org.kaczucha.account.application.dto.ExchangeResponse;

import java.math.BigDecimal;

public interface CurrencyServiceUseCase {
    CurrencyResponse getCurrencyRates(String baseCurrency);
    ExchangeResponse exchange(String from, String to, BigDecimal amount);
}
