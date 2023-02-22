package org.rejowska.repository;

import org.rejowska.domain.Client;
import org.rejowska.repository.port.ClientRepository;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.List;

public class InMemoryClientRepository implements ClientRepository {
    private final List<Client> clients;

    public InMemoryClientRepository(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public void save(Client client) {
        clients.add(client);
    }

    @Override
    public Client findByEmail(String email) {
        String emailToLowerCase = email.toLowerCase();
        return clients
                .stream()
                .filter(client -> Objects.equals(client.getEmail().toLowerCase(Locale.ROOT), emailToLowerCase))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Client with following email: %s not found".formatted(emailToLowerCase)));
    }

    public void deleteClient(final String email) {
        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("Email can't be null");
        }
        String clientEmail = email.toLowerCase();
        final Client clientToRemove = findByEmail(clientEmail);
        if (clientToRemove.getBalance() != 0) {
            throw new IllegalArgumentException("Client with founds on the account cannot be deleted");
        }
        clients.remove(clientToRemove);
    }
    public boolean clientAlreadyExists(String email) {
        for (Client client : clients) {
            if (email.equals(client.getEmail())) {
                return true;
            }
        }

        return false;
    }
}
