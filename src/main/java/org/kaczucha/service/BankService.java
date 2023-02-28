package org.kaczucha.service;

import org.kaczucha.domain.Client;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.kaczucha.exceptions.NotSufficientFundException;
import org.kaczucha.jpa.ClientJpaRepository;
import org.kaczucha.service.port.BankServiceUseCase;
import org.kaczucha.web.dto.ClientRequest;
import org.kaczucha.web.dto.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BankService implements BankServiceUseCase {
    private final ClientJpaRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public BankService(final ClientJpaRepository clientRepository, final ClientMapper clientMapper) {
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

    public void transfer(String fromEmail, String toEmail, double amount) {
        validateAmount(amount);

        if (fromEmail.equals(toEmail)) {
            throw new IllegalArgumentException("fromEmail and toEmail can't be equal");
        }
        if (clientRepository.findByEmailIgnoreCase(fromEmail) == null && clientRepository.findByEmailIgnoreCase(toEmail) == null) {
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
        if (clientRepository.findByEmailIgnoreCase(email) == null) {
            throw new NoSuchElementException("Client with this email do not exist");
        }
        Client client = clientRepository.findByEmailIgnoreCase(email);
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
