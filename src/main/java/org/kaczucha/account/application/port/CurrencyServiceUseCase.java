package org.kaczucha.account.application.port;

import org.kaczucha.account.application.dto.CurrencyResponse;

public interface CurrencyServiceUseCase {
    CurrencyResponse getCurrencyRates();
}
