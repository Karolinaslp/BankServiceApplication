package org.karolina.client.application;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.karolina.account.db.AccountJpaRepository;
import org.karolina.account.domain.Account;
import org.karolina.client.application.port.ClientServiceUseCase;
import org.karolina.client.db.ClientJpaRepository;
import org.karolina.client.domain.Client;
import org.karolina.client.web.dto.ClientRequest;
import org.karolina.client.web.dto.ClientResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientServiceUseCase {
    private final ClientJpaRepository clientRepository;
    private final AccountJpaRepository accountRepository;
    private final ClientMapper clientMapper;


    public void save(Client client) {
        clientRepository.save(client);
    }

    @SneakyThrows
    public void save(ClientRequest clientRequest) {
        Client mappedClient = clientMapper.toClient(clientRequest);
        save(mappedClient);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmailToLowerCase(email.toLowerCase());
    }

    public ClientResponse findResponseByEmail(String email) {
        final Optional<Client> client = findByEmail(email);
        return clientMapper.toResponseClient(client.orElseThrow());
    }

    @Override
    @Transactional
    public UpdateClientResponse updateClient(UpdateClientCommand command) {
        return clientRepository
                .findById(command.getId())
                .map(client -> {
                    updateFields(command, client);
                    return UpdateClientResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateClientResponse(false, Collections.singletonList("Client not found with id: comm" + command.getId()))
                );
    }

    private Set<Account> fetchAccountsById(Set<Long> accounts) {
        return accounts
                .stream()
                .map(accountId -> accountRepository
                        .findById(accountId)
                        .orElseThrow(() -> new IllegalArgumentException("Unable to find account with id:" + accountId))
                )
                .collect(Collectors.toSet());
    }

    private Client updateFields(UpdateClientCommand command, Client client) {
        if (command.getName() != null) {
            client.setName(command.getName());
        }
        if (command.getEmail() != null) {
            client.setEmail(command.getEmail());
        }
        if (command.getAccounts() != null && !command.getAccounts().isEmpty()) {
            updateClients(client, fetchAccountsById(command.getAccounts()));
        }
        return client;
    }

    private void updateClients(Client client, Set<Account> accounts) {
        client.removeAccounts();
        accounts.forEach(client::addAccount);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
