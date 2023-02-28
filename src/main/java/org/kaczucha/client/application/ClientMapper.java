package org.kaczucha.client.application;

import org.kaczucha.account.domain.Account;
import org.kaczucha.client.domain.Client;
import org.kaczucha.client.web.dto.ClientRequest;
import org.kaczucha.client.web.dto.ClientResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClientMapper {
    public ClientResponse toResponseClient(Client client) {
        return ClientResponse
                .builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .accounts(
                        client
                                .getAccounts()
                                .stream()
                                .map(Account::getId)
                                .collect(Collectors.toList()))
                .build();
    }

    public Client toClient(ClientRequest clientRequest) {
        return Client.builder()
                .name(clientRequest.getName())
                .email(clientRequest.getEmail())
                .build();
    }
}
