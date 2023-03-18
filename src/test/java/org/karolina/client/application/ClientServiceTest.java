package org.karolina.client.application;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.karolina.account.domain.Account;
import org.karolina.client.db.ClientJpaRepository;
import org.karolina.client.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientServiceTest {

    @Autowired
    ClientService service;

    @MockBean
    ClientJpaRepository repository;


    /**
     * Throw exception if saved clients email already exists
     */
    @Test
    public void save_twoDifferentClientWithTheSameMail_secondClientNotAdded() {
        //Given
        String email = "a@a";
        Client client = new Client("Bartek", email, Set.of(account(BigDecimal.valueOf(100),"PLN")));
        Client client1 = alek(email);
        when(repository.findByEmailToLowerCase(client.getEmail())).thenReturn(Optional.of(client));
        //When
        service.save(client1);
        //Then
        assertFalse(repository.getAllClients().contains(client1));
    }

    @SneakyThrows
    @Test
    public void save_newClient_Ok() {
        //Given
        Client client = alek("a@a.pl");
        when(repository.findByEmailToLowerCase(client.getEmail())).thenReturn(Optional.of(client));
        //When
        service.save(client);
        //Then
        verify(repository).save(client);
    }

    /**
     * Ignore case when saving the client
     */
    @Test
    public void save_clientWithUpperCaseEmailOk_clientSaved()  {
        //Given
        String email = "UPPER@eMail.PL";
        Client client = alek(email);
        when(repository.findByEmailToLowerCase(email)).thenReturn(Optional.of(client));
        //When
        service.save(client);
        //Then
        verify(repository).save(client);
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
        when(repository.findByEmailToLowerCase(client.getEmail())).thenReturn(Optional.of(client));
        //When
        service.save(client2);
        //Then
        assertFalse(repository.getAllClients().contains(client2));
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
        assertFalse(repository.getAllClients().contains(client));
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
        assertFalse(repository.getAllClients().contains(client));
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
        assertFalse(repository.getAllClients().contains(client));
    }

    /**
     * Find client by writing upper case email
     */
    @Test
    public void findByEmail_upperCaseInputEmailOk_clientFound() {
        //Given
        String email = "alek@pl";
        Client client = alek(email);
        when(repository.findByEmailToLowerCase(email)).thenReturn(Optional.of(client));
        //When
        service.save(client);
        Optional<Client> actualClient = service.findByEmail("ALEK@PL");
        //Then
        assertEquals(Optional.of(client), actualClient);
    }

    Set<Account> accounts = new HashSet<>();

    private Client alek(String email) {
        accounts.add(account(BigDecimal.valueOf(100), "PLN"));
       return new Client("Alek", email, accounts);
    }

    private Account account(BigDecimal balance, String currency ){
        return Account.builder().balance(balance).currency(currency).build();
    }
}
