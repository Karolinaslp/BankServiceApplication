package org.kaczucha.account.application;

import lombok.RequiredArgsConstructor;
import org.kaczucha.account.application.port.AccountServiceUseCase;
import org.kaczucha.account.db.AccountJpaRepository;
import org.kaczucha.account.domain.Account;
import org.kaczucha.account.web.dto.AccountRequest;
import org.kaczucha.account.web.dto.AccountResponse;
import org.kaczucha.exceptions.NotSufficientFundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceUseCase {
    private final AccountJpaRepository repository;
    private final AccountMapper mapper;

    @Override
    public void save(final AccountRequest account) {
        Account mappedAccount = mapper.toAccount(account);
        repository.save(mappedAccount);
    }

    @Override
    public AccountResponse findById(final long id) {
        return repository.findById(id)
                .map(mapper::toAccountResponse)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Account with id: %d not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void transfer(long fromAccountId, long toAccountId, double amount) {
        validateAmount(amount);

        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Transfer to the same account if forbidden");
        }
        if (repository.findById(fromAccountId).isEmpty() && repository.findById(toAccountId).isEmpty()) {
            throw new NoSuchElementException("Account with this id do not exist");
        }
        Account fromAccount = repository.getReferenceById(fromAccountId);
        Account toAccount = repository.getReferenceById(fromAccountId);

        if (fromAccount.getBalance() - amount >= 0) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
        } else {
            throw new NotSufficientFundException("not enough funds");
        }
        repository.save(fromAccount);
        repository.save(toAccount);
    }

    public void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    public void withdraw(long id, double amount) {
        validateAmount(amount);
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Account with this id do not exist");
        }
        Account account = repository.getReferenceById(id);
        if (amount > account.getBalance()) {
            throw new NotSufficientFundException("Balance must be equal or higher then amount");
        }
        final double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        repository.save(account);
    }
}
