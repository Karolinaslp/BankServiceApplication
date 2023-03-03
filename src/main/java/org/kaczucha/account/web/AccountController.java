package org.kaczucha.account.web;

import lombok.RequiredArgsConstructor;
import org.kaczucha.account.application.port.AccountServiceUseCase;
import org.kaczucha.account.web.dto.AccountRequest;
import org.kaczucha.account.web.dto.AccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceUseCase service;

    @GetMapping(path = "/api/account")
    public ResponseEntity<AccountResponse> getById(@RequestParam Long id) {
        final AccountResponse account = service.findById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = "/api/account")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountRequest accountRequest) {
        service.save(accountRequest);
    }

    @PatchMapping(path = "/api/withdraw")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void withdraw(@RequestParam Long id, @RequestParam BigDecimal amount) {
        service.withdraw(id, amount);
    }
}
