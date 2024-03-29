package org.karolina.account.application;

import lombok.RequiredArgsConstructor;
import org.karolina.account.application.mapper.AccountMapper;
import org.karolina.account.application.port.AccountServiceUseCase;
import org.karolina.account.application.port.NotificationServiceUseCase;
import org.karolina.account.application.port.TransferStrategiesUseCase;
import org.karolina.account.application.transfer.DifferentCurrencyTransferStrategy;
import org.karolina.account.application.transfer.SameCurrencyTransferStrategy;
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
    private final AccountJpaRepository accountRepository;
    private final AccountMapper mapper;
    private final NotificationServiceUseCase notificationSystem;
    private final SameCurrencyTransferStrategy sameCurrencyTransferStrategy;
    private final DifferentCurrencyTransferStrategy differentCurrencyTransferStrategy;

    @Override
    public void save(final AccountRequest account) {
        Account mappedAccount = mapper.toAccount(account);
        accountRepository.save(mappedAccount);
    }

    @Override
    public AccountResponse findById(final long id) {
        return accountRepository
                .findById(id)
                .map(mapper::toAccountResponse)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Account with id: %d not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void transfer(long fromAccountId, long toAccountId, BigDecimal amount, String currency) {
        validateAmount(amount);
        validateAccount(fromAccountId, toAccountId);

        Account fromAccount = accountRepository.getReferenceById(fromAccountId);
        Account toAccount = accountRepository.getReferenceById(toAccountId);

        if (isNotEnoughFounds(fromAccount, amount)) {
            throw new NotSufficientFundException("Not enough funds, to make transfer");
        }

        TransferStrategiesUseCase transferStrategy;
        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())){
            transferStrategy = differentCurrencyTransferStrategy;
        } else {
            transferStrategy = sameCurrencyTransferStrategy;
        }
        transferStrategy.transfer(fromAccount,toAccount,amount,currency);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        notificationSystem.sendNotification("Transaction has been made on your account", fromAccountId);
        notificationSystem.sendNotification("Transaction has been made on your account", toAccountId);
    }

    private void validateAccount(long fromAccountId, long toAccountId) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Transfer to the same account if forbidden");
        }
        if (accountRepository.findById(fromAccountId).isEmpty() || accountRepository.findById(toAccountId).isEmpty()) {
            throw new NoSuchElementException("At least one of the accounts does not exist");
        }
    }

    public void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private static boolean isNotEnoughFounds(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) < 0;
    }

    public void withdraw(long accountId, BigDecimal amount) {
        validateAmount(amount);
        Account account = accountRepository.getReferenceById(accountId);
        if (isNotEnoughFounds(account, amount)) {
            throw new NotSufficientFundException("Balance must be equal or higher then amount");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        notificationSystem.sendNotification("Withdrawal:" + amount, accountId);
    }
}
