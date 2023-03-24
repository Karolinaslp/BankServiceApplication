package org.karolina.client.application;

import org.karolina.account.domain.Account;
import org.karolina.client.domain.Client;
import org.karolina.client.web.dto.ClientRequest;
import org.karolina.client.web.dto.ClientResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClientMapper {
    public ClientResponse toClientResponse(Client client) {
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
