package org.kaczucha.client.application;

import org.kaczucha.client.application.port.ClientServiceUseCase;
import org.kaczucha.client.db.ClientJpaRepository;
import org.kaczucha.client.domain.Client;
import org.kaczucha.client.web.dto.ClientRequest;
import org.kaczucha.client.web.dto.ClientResponse;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientService implements ClientServiceUseCase {
    private final ClientJpaRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(final ClientJpaRepository clientRepository, final ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public void save(Client client) throws ClientAlreadyExistsException {
        if (Objects.isNull(client.getEmail())) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (Objects.isNull(client.getName())) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (clientRepository.findByEmailIgnoreCase(client.getEmail()) != null) {
            throw new ClientAlreadyExistsException("Client with following email: %s already exist".formatted(client.getEmail()));
        }
        clientRepository.save(client);
    }

    public void save(ClientRequest clientRequest) throws ClientAlreadyExistsException {
        Client mappedClient = clientMapper.toClient(clientRequest);
        save(mappedClient);
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmailIgnoreCase(email);
    }

    public ClientResponse findResponseByEmail(String email) {
        final Client client = findByEmail(email);
        return clientMapper.toResponseClient(client);
    }


    @Override
    public void deleteByEmail(String email) {
        clientRepository.deleteByEmail(email);
    }
}
