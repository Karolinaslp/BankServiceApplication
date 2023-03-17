package org.karolina.account.application.mapper;

import org.karolina.account.domain.Account;
import org.karolina.account.web.dto.AccountRequest;
import org.karolina.account.web.dto.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountResponse toAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .userId(account.getUserId())
                .build();
    }

    public Account toAccount(AccountRequest accountRequest) {
        return Account.builder()
                .userId(accountRequest.getUserId())
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .build();
    }
}
