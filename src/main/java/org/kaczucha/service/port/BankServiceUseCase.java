package org.kaczucha.service.port;

import org.kaczucha.domain.Client;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.kaczucha.web.dto.ClientRequest;
import org.kaczucha.web.dto.ClientResponse;

public interface BankServiceUseCase {
    void save(ClientRequest clientRequest) throws ClientAlreadyExistsException;

    Client findByEmail(String email);

    void transfer(String fromEmail, String toEmail, double amount);

    void withdraw(String email, double amount);

    void validateAmount(double amount);

    void deleteByEmail(String email);

    ClientResponse findResponseByEmail(String email);
}
