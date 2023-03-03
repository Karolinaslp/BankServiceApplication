package org.kaczucha.account.application.port;

import org.kaczucha.account.web.dto.CurrencyResponse;
import org.kaczucha.account.web.dto.ExchangeResponse;

import java.math.BigDecimal;

public interface CurrencyServiceUseCase {
    CurrencyResponse getCurrencyRates(String baseCurrency);

    ExchangeResponse exchange(BigDecimal amount, String from, String to);
}
