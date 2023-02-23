package org.kaczucha.service.port;

import org.kaczucha.domain.Client;
import org.kaczucha.exceptions.ClientAlreadyExistsException;

public interface BankServiceUseCase {
    void save(Client client) throws ClientAlreadyExistsException;

    Client findByEmail(String email);

    void transfer(String fromEmail, String toEmail, double amount);

    void withdraw(String email, double amount);

    void validateAmount(double amount);
}
