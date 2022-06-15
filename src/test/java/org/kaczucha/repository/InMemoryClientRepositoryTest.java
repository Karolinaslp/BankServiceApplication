package org.kaczucha.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.Client;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InMemoryClientRepositoryTest {

    private InMemoryClientRepository repository;
    private List<Client> clients;

    @BeforeEach
    public void setup() {

        clients = new ArrayList<>();

        repository = new InMemoryClientRepository(clients);
    }

    @Test
    public void verifyIfUserIsAddingCorrectlyToTheRepository() {
        // Given
        final Client client = new Client("Alek", "alek@pl", 100);
        final Client expectedClient = new Client("Alek", "alek@pl", 100);
        // When
        repository.save(client);
        // Then
        final Client actualClient = clients
                .stream()
                .findFirst()
                .get();
        assertEquals(expectedClient, actualClient);
    }

    @Test
    public void deleteClient_correctEmail_clientDeletedCorrectlyFromRepository() {
        //Given
        String email = "alek@gmail.com";
        Client client = new Client("Alek", email, 0);
        clients.add(client);
        //When
        repository.deleteClient(email);
        //Then
        Assertions.assertFalse(clients.contains(client));
    }

    @Test
    public void deleteClient_nullEmail_throwsIllegalArgumentException() {
        //Given
        final String email = null;
        //When/Then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> repository.deleteClient(email)
        );
    }

    @Test
    public void deleteClient_balanceDifferentThenZero_notAllowToDelete_ThrowsIllegalArgumentException() {
        //Given
        double balance = 1;
        Client clientShouldNotBeRemove = new Client("Alek", "alek@gmail.com", balance);
        clients.add(clientShouldNotBeRemove);
        //When/Then
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                repository.deleteClient("alek@gmail.com"));
    }

    @Test
    public void deleteClient_upperCaseInputEmailOk_clientDeleted() {
        //Given
        String email = "ALEK@GMAIL.COM";
        Client clientToRemove = new Client("Alek", "alek@gmail.com", 0);
        clients.add(clientToRemove);
        //When
        repository.deleteClient(email);
        //Then
        Assertions.assertFalse(clients.contains(clientToRemove));
    }
}