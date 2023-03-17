package org.karolina.account.application.port;

import org.karolina.account.web.dto.CurrencyResponse;
import org.karolina.account.web.dto.ExchangeResponse;

import java.math.BigDecimal;

public interface CurrencyServiceUseCase {
    CurrencyResponse getCurrencyRates(String baseCurrency);

    ExchangeResponse exchange(BigDecimal amount, String from, String to);
}
