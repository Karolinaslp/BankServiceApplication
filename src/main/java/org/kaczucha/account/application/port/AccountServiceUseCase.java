package org.kaczucha.account.application.port;

import org.kaczucha.account.web.dto.AccountRequest;
import org.kaczucha.account.web.dto.AccountResponse;

public interface AccountServiceUseCase {
    void save(AccountRequest accountRequest);

    AccountResponse findById(long id);

    void transfer(long fromId, long toId, double amount);

    void validateAmount(double amount);

    void deleteById(Long id);

    void withdraw(long id, double amount);
}
