package org.karolina.client.web;

import lombok.RequiredArgsConstructor;
import org.karolina.client.application.port.ClientServiceUseCase;
import org.karolina.client.domain.Client;
import org.karolina.client.web.dto.ClientRequest;
import org.karolina.client.web.dto.ClientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/clients")
public class ClientController {
    private final ClientServiceUseCase service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getAll() {
        return service.findAll();
    }

    @GetMapping(path = "/email")
    public ResponseEntity<ClientResponse> getByEmail(@RequestParam String email) {
        ClientResponse client = service.findResponseByEmail(email);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
    @GetMapping(path = "/{clientId})")
    public ResponseEntity<ClientResponse> geById(@PathVariable(value = "clientId") Long id) {
        final ClientResponse client = service.findById(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addClient(@RequestBody ClientRequest client) {
        service.save(client);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(value = "userId") Long id) {
        service.deleteById(id);
    }
}
