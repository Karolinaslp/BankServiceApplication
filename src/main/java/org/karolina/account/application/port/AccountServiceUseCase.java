package org.karolina.account.application.port;

import org.karolina.account.web.dto.AccountRequest;
import org.karolina.account.web.dto.AccountResponse;

import java.math.BigDecimal;

public interface AccountServiceUseCase {
    void save(AccountRequest accountRequest);

    AccountResponse findById(long id);

    void transfer(long fromId, long toId, BigDecimal amount, String currency);

    void validateAmount(BigDecimal amount);

    void deleteById(Long id);

    void withdraw(long id, BigDecimal amount);
}
