package org.kaczucha.client.web;

import lombok.RequiredArgsConstructor;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.kaczucha.client.application.port.ClientServiceUseCase;
import org.kaczucha.client.web.dto.ClientRequest;
import org.kaczucha.client.web.dto.ClientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ClientController {
    private final ClientServiceUseCase service;

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
