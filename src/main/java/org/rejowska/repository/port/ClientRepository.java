package org.rejowska.repository.port;

import org.rejowska.domain.Client;

public interface ClientRepository {
    void save(Client client);

    Client findByEmail(String email);

    boolean clientAlreadyExists(String email);
}
