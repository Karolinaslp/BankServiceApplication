package org.karolina.client.application;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.karolina.account.db.AccountJpaRepository;
import org.karolina.account.domain.Account;
import org.karolina.client.application.port.ClientServiceUseCase.UpdateClientCommand;
import org.karolina.client.application.port.ClientServiceUseCase.UpdateClientResponse;
import org.karolina.client.db.ClientJpaRepository;
import org.karolina.client.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientServiceTest {

    @Autowired
    ClientService service;

    @MockBean
    ClientJpaRepository clientRepository;

    @MockBean
    AccountJpaRepository accountRepository;


    /**
     * Throw exception if saved clients email already exists
     */
    @Test
    public void save_twoDifferentClientWithTheSameMail_secondClientNotAdded() {
        //Given
        String email = "a@a";
        Client client = new Client("Bartek", email, Set.of(account(BigDecimal.valueOf(100), "PLN")));
        Client client1 = alek(email);
        when(clientRepository.findByEmailIgnoreCase(client.getEmail())).thenReturn(Optional.of(client));
        //When
        service.save(client1);
        //Then
        assertFalse(clientRepository.findAllEager().contains(client1));
    }

    @SneakyThrows
    @Test
    public void save_newClient_Ok() {
        //Given
        Client client = alek("a@a.pl");
        when(clientRepository.findByEmailIgnoreCase(client.getEmail())).thenReturn(Optional.of(client));
        //When
        service.save(client);
        //Then
        verify(clientRepository).save(client);
    }

    /**
     * Ignore case when saving the client
     */
    @Test
    public void save_clientWithUpperCaseEmailOk_clientSaved() {
        //Given
        String email = "UPPER@eMail.PL";
        Client client = alek(email);
        when(clientRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(client));
        //When
        service.save(client);
        //Then
        verify(clientRepository).save(client);
    }

    /**
     * Throw exception when try to save already existing client
     */
    @Test
    public void save_alreadyExistingClient_clientDoNotExistInRepository() {
        //Given
        String email = "alek@pl";
        Client client = alek(email);
        Client client2 = alek(email);
        when(clientRepository.findByEmailIgnoreCase(client.getEmail())).thenReturn(Optional.of(client));
        //When
        service.save(client2);
        //Then
        assertFalse(clientRepository.findAllEager().contains(client2));
    }

    /**
     * Do not allow save client with null name
     */
    @Test
    public void save_clientWithNullName_clientDoNotExistInRepository() {
        //Given
        String name = null;
        Client client = new Client(name, "Alek@pl", Set.of(account(new BigDecimal(100), "PLN")));
        //When
        service.save(client);
        //Then
        assertFalse(clientRepository.findAllEager().contains(client));
    }

    /**
     * Do not allow save client with null email
     */
    @Test
    public void save_clientWithNullEmail_clientDoNotExistInRepository() {
        //Given
        String email = null;
        Client client = alek(email);
        //When
        service.save(client);
        //Then
        assertFalse(clientRepository.findAllEager().contains(client));
    }

    /**
     * Do not allow save client with negative balance
     */
    @Test
    public void save_ClientWithNegativeBalance_throwsIllegalArgumentException() {
        //Given
        Client client = new Client("Alek", "alek@pl", Set.of(account(BigDecimal.valueOf(-100), "PLN")));
        //When
        service.save(client);
        //Then
        assertFalse(clientRepository.findAllEager().contains(client));
    }

    /**
     * Find client by writing upper case email
     */
    @Test
    public void findByEmail_upperCaseInputEmailOk_clientFound() {
        //Given
        String email = "alek@pl";
        Client client = alek(email);
        when(clientRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(client));
        //When
        service.save(client);
        Optional<Client> actualClient = service.findByEmail("ALEK@PL");
        //Then
        assertEquals(Optional.of(client), actualClient);
    }

    @Test
    public void updateClient_updatingClientName_Ok() {
        //Given
        String newName = "Olek";
        Set<Long> accountIds = new HashSet<>(Collections.singletonList(1L));

        Client existingClient = alek("alek@a.pl");
        when(clientRepository.findById(existingClient.getId())).thenReturn(Optional.of(existingClient));

        Account account = new Account(new BigDecimal("100"), "PLN");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        UpdateClientCommand updateClientCommand = new UpdateClientCommand(existingClient.getId(), newName, existingClient.getEmail(), accountIds);
        //When
        UpdateClientResponse clientResponse = service.updateClient(updateClientCommand);

        //Then
        assertTrue(clientResponse.isSuccess());
        assertEquals(newName, existingClient.getName());
    }

    @Test
    void updateClient_shouldAddAccountsToClient_Ok322222() {
        // given
        String newName = "New Name";
        String newEmail = "newemail@example.com";
        Set<Long> accountIds = new HashSet<>(Collections.singletonList(1L));

        Client existingClient = alek("a@a.pl");
        when(clientRepository.findById(existingClient.getId())).thenReturn(Optional.of(existingClient));

        Account account = new Account(new BigDecimal("100"), "PLN");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        UpdateClientCommand updateClientCommand = new UpdateClientCommand(existingClient.getId(), newName, newEmail, accountIds);

        // when
        UpdateClientResponse updateClientResponse = service.updateClient(updateClientCommand);

        // then
        assertTrue(updateClientResponse.isSuccess());
        assertEquals(newName, existingClient.getName());
        assertEquals(newEmail, existingClient.getEmail());
        assertEquals(1, existingClient.getAccounts().size());
        assertTrue(existingClient.getAccounts().contains(account));
    }
    @Test
    public void updateClient_clientNotFound_falseResponse() {
        //Given
        Set<Long> accountsIds = new HashSet<>(Collections.singletonList(1L));
        UpdateClientCommand command = new UpdateClientCommand(1L,"Alek", "a@a.pl",accountsIds);
        when(clientRepository.findById(command.getId())).thenReturn(Optional.empty());

        //When
        UpdateClientResponse response = service.updateClient(command);

        //Then
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getErrors().contains("Client not found with id: 1"));
    }

    Set<Account> accounts = new HashSet<>();

    private Client alek(String email) {
        accounts.add(account(BigDecimal.valueOf(100), "PLN"));
        return new Client("Alek", email, accounts);
    }

    private Account account(BigDecimal balance, String currency) {
        return Account.builder().balance(balance).currency(currency).build();
    }
}
