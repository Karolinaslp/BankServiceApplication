package org.kaczucha.service;

import org.kaczucha.domain.Client;
import org.kaczucha.repository.ClientRepository;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.kaczucha.service.port.BankServiceUseCase;

import java.sql.SQLException;
import java.util.*;

public class BankService implements BankServiceUseCase {
    private final ClientRepository clientRepository;

    public BankService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void save(Client client) throws ClientAlreadyExistsException {

        if (client.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        String clientEmail = client.getEmail().toLowerCase(Locale.ROOT);
        client.setEmail(clientEmail);
        if (client.getName() == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (client.getBalance() < 0) {
            throw new IllegalArgumentException("Balance must be positive");
        }
        if (clientRepository.findByEmail(client.getEmail()) != null){
            throw new ClientAlreadyExistsException("Client with following email: %s already exist".formatted(clientEmail));
        }
        try {
            clientRepository.save(client);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email.toLowerCase());
    }

    public void transfer(String fromEmail, String toEmail, double amount) {
        validateAmount(amount);
        if (fromEmail.equals(toEmail)) {
            throw new IllegalArgumentException("fromEmail and toEmail cant be equal");
        }
        Client fromClient = findByEmail(fromEmail);
        Client toClient = findByEmail(toEmail);
        if (fromClient.getBalance() - amount >= 0) {
            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);
        } else {
            throw new NotSufficientFundException("not enough funds");
        }
    }

    public void withdraw(String email, double amount) {
        validateAmount(amount);
        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("Email can't be null");
        }
        final String lowerCaseEmail = email.toLowerCase();
        final Client client = findByEmail(lowerCaseEmail);
        if (amount > client.getBalance()) {
            throw new NotSufficientFundException("Balance must be equal or higher then amount");
        }
        final double newBalance = client.getBalance() - amount;
        client.setBalance(newBalance);
    }

    public void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }


}
