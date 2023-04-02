package org.karolina.account.application.transfer;

import org.karolina.account.application.port.TransferStrategiesUseCase;
import org.karolina.account.domain.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SameCurrencyTransferStrategy implements TransferStrategiesUseCase {
    @Override
    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount, String currency) {
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
    }
}
