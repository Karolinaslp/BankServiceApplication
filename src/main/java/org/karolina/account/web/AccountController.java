package org.karolina.account.web;

import lombok.RequiredArgsConstructor;
import org.karolina.account.application.port.AccountServiceUseCase;
import org.karolina.account.web.dto.AccountRequest;
import org.karolina.account.web.dto.AccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {
    private final AccountServiceUseCase service;

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<AccountResponse> getById(@PathVariable(value = "accountId") Long id) {
        final AccountResponse account = service.findById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountRequest accountRequest) {
        service.save(accountRequest);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void withdraw(@PathVariable(value = "id") Long id, @RequestParam BigDecimal amount) {
        service.withdraw(id, amount);
    }
}
