package org.kaczucha.web;

import lombok.RequiredArgsConstructor;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.kaczucha.service.port.BankServiceUseCase;
import org.kaczucha.web.dto.ClientRequest;
import org.kaczucha.web.dto.ClientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class BankController {
    private final BankServiceUseCase service;

    @GetMapping(path = "/api/user")
    public ResponseEntity<ClientResponse> getByEmail(@RequestParam String email) {
        ClientResponse client = service.findResponseByEmail(email);
        return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/api/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addClient(@RequestBody ClientRequest client) throws ClientAlreadyExistsException {
        service.save(client);
    }
}
