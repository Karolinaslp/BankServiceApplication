package org.kaczucha.service;

import org.kaczucha.domain.Client;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.kaczucha.exceptions.NotSufficientFundException;
import org.kaczucha.repository.ClientJpaRepository;
import org.kaczucha.service.port.BankServiceUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BankService implements BankServiceUseCase {
    private final ClientJpaRepository clientRepository;

    @Autowired
    public BankService(ClientJpaRepository clientRepository) {
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
        if (clientRepository.findByEmail(client.getEmail()) != null) {
            throw new ClientAlreadyExistsException("Client with following email: %s already exist".formatted(clientEmail));
        }
            clientRepository.save(client);
    }


    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email.toLowerCase());
    }

    public void transfer(String fromEmail, String toEmail, double amount) {
        validateAmount(amount);

        if (fromEmail.equals(toEmail)) {
            throw new IllegalArgumentException("fromEmail and toEmail can't be equal");
        }
        if (clientRepository.findByEmail(fromEmail) == null && clientRepository.findByEmail(toEmail) == null) {
            throw new NoSuchElementException("Client with this email do not exist");
        }

        Client fromClient = findByEmail(fromEmail);
        Client toClient = findByEmail(toEmail);

        if (fromClient.getBalance() - amount >= 0) {
            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);
        } else {
            throw new NotSufficientFundException("not enough funds");
        }
            clientRepository.save(fromClient);
            clientRepository.save(toClient);
    }

    public void withdraw(String email, double amount) {
        validateAmount(amount);
        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("Email can't be null");
        }
        if (clientRepository.findByEmail(email) == null) {
            throw new NoSuchElementException("Client with this email do not exist");
        }
        Client client = clientRepository.findByEmail(email);
        if (amount > client.getBalance()) {
            throw new NotSufficientFundException("Balance must be equal or higher then amount");
        }
        final double newBalance = client.getBalance() - amount;
        client.setBalance(newBalance);
            clientRepository.save(client);
    }

    public void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    @Override
    public void deleteByEmail(String email) {
        clientRepository.deleteByEmail(email);
    }
}
