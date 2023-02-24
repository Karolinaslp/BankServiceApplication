package org.kaczucha.repository;

import org.kaczucha.domain.Client;

import java.sql.SQLException;

public interface ClientRepository {
    void save(Client client) throws SQLException;

    Client findByEmail(String email);

}
