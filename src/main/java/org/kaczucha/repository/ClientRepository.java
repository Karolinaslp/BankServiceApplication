package org.kaczucha.repository;

import org.kaczucha.repository.entity.Client;

import java.sql.SQLException;

public interface ClientRepository {
    void save(Client client) throws SQLException;

    Client findByEmail(String email);

}
