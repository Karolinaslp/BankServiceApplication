package org.kaczucha.repository;

import org.kaczucha.Client;

public interface ClientRepository {
    void save(Client client);

    Client findByEmail(String email);

    boolean clientAlreadyExists(String email);
}
