package org.kaczucha.client.application.port;

import org.kaczucha.client.domain.Client;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.kaczucha.client.web.dto.ClientRequest;
import org.kaczucha.client.web.dto.ClientResponse;

public interface ClientServiceUseCase {
    void save(ClientRequest clientRequest) throws ClientAlreadyExistsException;

    Client findByEmail(String email);

    void deleteById(Long id);

    ClientResponse findResponseByEmail(String email);
}
