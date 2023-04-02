package org.karolina.account.application.port;

import org.karolina.account.domain.Account;

import java.math.BigDecimal;

public interface TransferStrategiesUseCase {
    void transfer(Account fromAccount, Account toAccount, BigDecimal amount, String currency);
}
