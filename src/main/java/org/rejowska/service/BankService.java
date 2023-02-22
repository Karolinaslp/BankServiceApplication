package org.rejowska.service;

import org.rejowska.domain.Client;
import org.rejowska.exceplion.NotSufficientFundException;
import org.rejowska.repository.port.ClientRepository;
import org.rejowska.exceplion.ClientAlreadyExistsException;

import java.util.*;

public class BankService {
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
        if(client.getBalance() < 0) {
            throw new IllegalArgumentException("Balance must be positive");
        }
        if(clientRepository.clientAlreadyExists(clientEmail)){
            throw new ClientAlreadyExistsException("Client with following email: %s already exist".formatted(clientEmail));
        }

        clientRepository.save(client);
    }


    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email.toLowerCase(Locale.ROOT));
        //  public Client findByEmail(String email) {
//            if (!emailExists(clients, email)) {
//                throw new NoSuchElementException("Email has not been found!");
//            } else {
//                return clientSet
//                        .stream()
//                        .filter(client -> Objects.equals(client.getEmail(), email.toLowerCase()))
//                        .findFirst()
//                        .get();
//            }
//        }

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

    public void withdraw(final String email, final double amount) {
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

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }


}
