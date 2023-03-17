package org.karolina.account.application;

import lombok.RequiredArgsConstructor;
import org.karolina.account.application.mapper.AccountMapper;
import org.karolina.account.application.port.AccountServiceUseCase;
import org.karolina.account.application.port.CurrencyServiceUseCase;
import org.karolina.account.db.AccountJpaRepository;
import org.karolina.account.domain.Account;
import org.karolina.account.web.dto.AccountRequest;
import org.karolina.account.web.dto.AccountResponse;
import org.karolina.exceptions.NotSufficientFundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceUseCase {
    private final AccountJpaRepository repository;
    private final CurrencyServiceUseCase currencyService;
    private final AccountMapper mapper;

    @Override
    public void save(final AccountRequest account) {
        Account mappedAccount = mapper.toAccount(account);
        repository.save(mappedAccount);
    }

    @Override
    public AccountResponse findById(final long id) {
        return repository
                .findById(id)
                .map(mapper::toAccountResponse)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Account with id: %d not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void transfer(long fromAccountId, long toAccountId, BigDecimal amount, String currency) {
        validateAmount(amount);
        validateAccount(fromAccountId, toAccountId);

        Account fromAccount = repository.getReferenceById(fromAccountId);
        Account toAccount = repository.getReferenceById(toAccountId);

        if (isNotEnoughFounds(fromAccount, amount)) {
            throw new NotSufficientFundException("Not enough funds, to make transfer");
        }

        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            double result = currencyService.exchange(amount, fromAccount.getCurrency(), toAccount.getCurrency()).getResult();
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(BigDecimal.valueOf(result)));

        } else {
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
        }
        repository.save(fromAccount);
        repository.save(toAccount);
    }

    public void validateAccount(long fromAccountId, long toAccountId) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Transfer to the same account if forbidden");
        }
        if (repository.findById(fromAccountId).isEmpty() || repository.findById(toAccountId).isEmpty()) {
            throw new NoSuchElementException("At least one of the accounts does not exist");
        }
    }

    public void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    public static boolean isNotEnoughFounds(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) < 0;
    }

    public void withdraw(long id, BigDecimal amount) {
        validateAmount(amount);
        Account account = repository.getReferenceById(id);
        if (isNotEnoughFounds(account, amount)) {
            throw new NotSufficientFundException("Balance must be equal or higher then amount");
        }
        account.setBalance(account.getBalance().subtract(amount));
        repository.save(account);
    }
}
