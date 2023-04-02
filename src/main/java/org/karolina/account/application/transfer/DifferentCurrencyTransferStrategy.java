package org.karolina.account.application.transfer;

import lombok.RequiredArgsConstructor;
import org.karolina.account.application.port.CurrencyServiceUseCase;
import org.karolina.account.application.port.TransferStrategiesUseCase;
import org.karolina.account.domain.Account;
import org.karolina.account.web.dto.ExchangeResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DifferentCurrencyTransferStrategy implements TransferStrategiesUseCase {
    private final CurrencyServiceUseCase currencyService;
    @Override
    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount, String currency) {
        ExchangeResponse response = currencyService.exchange(amount, fromAccount.getCurrency(), toAccount.getCurrency());
        double result = response.getResult();

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(BigDecimal.valueOf(result)));
    }
}
