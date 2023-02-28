//package org.kaczucha.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.kaczucha.account.domain.Account;
//import org.kaczucha.client.domain.Client;
//import org.kaczucha.client.application.ClientMapper;
//import org.kaczucha.client.application.ClientService;
//import org.kaczucha.exceptions.ClientAlreadyExistsException;
//import org.kaczucha.exceptions.NotSufficientFundException;
//import org.kaczucha.client.db.ClientJpaRepository;
//
//import java.util.NoSuchElementException;
//
//import static java.util.Collections.singletonList;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class ClientServiceTest {
//
//    private ClientService service;
//    private ClientJpaRepository repository;
//
//    @BeforeEach
//    public void setup() {
//        repository = mock(ClientJpaRepository.class);
//        ClientMapper mapper = mock(ClientMapper.class);
//        service = new ClientService(repository, mapper);
//    }
//
//    @Test
//    public void transfer_allParamsOk_foundsTransferred() {
//        //Given
//        final String emailFrom = "alek@pl";
//        final String emailTo = "bartek@pl";
//
//        final Client clientFrom = new Client(
//                "Alek",
//                emailFrom,
//                singletonList(new Account(1000, "PLN"))
//        );
//        final Client clientTo = new Client(
//                "Bartek",
//                emailTo,
//                singletonList(new Account(500, "PLN"))
//        );
//        final double amount = 100;
//        when(repository.findByEmailIgnoreCase(emailFrom)).thenReturn(clientFrom);
//        when(repository.findByEmailIgnoreCase(emailTo)).thenReturn(clientTo);
//        //When
//        service.transfer(emailFrom, emailTo, amount);
//        //Then
//        final Client expectedFromClient = new Client(
//                "Alek",
//                emailFrom,
//                singletonList(new Account(900, "PLN"))
//        );
//        final Client expectedToClient = new Client(
//                "Bartek",
//                emailTo, singletonList(new Account(600, "PLN"))
//        );
//
//        verify(repository).save(expectedFromClient);
//        verify(repository).save(expectedToClient);
//    }
//
//    @Test
//    public void transfer_allFunds_fundsTransferred() {
//        //Given
//        final String emailFrom = "alek@pl";
//        final String emailTo = "bartek@pl";
//        final Client clientFrom = new Client("Alek", emailFrom, singletonList(new Account(1000, "PLN")));
//        final Client clientTo = new Client("Bartek", emailTo, singletonList(new Account(500, "PLN")));
//        final double amount = 1000;
//        when(repository.findByEmailIgnoreCase(emailFrom)).thenReturn(clientFrom);
//        when(repository.findByEmailIgnoreCase(emailTo)).thenReturn(clientTo);
//
//        //When
//        service.transfer(emailFrom, emailTo, amount);
//        //Then
////        final Client expectedFromClient = new Client("Alek", emailFrom, singletonList(new Account(0, "PLN")));
////        final Client expectedToClient = new Client("Bartek", emailTo, singletonList(new Account(1500, "PLN")));
//
//        verify(repository).save(clientFrom);
//        verify(repository).save(clientTo);
//    }
//
//    /**
//     * Throw exception if client do not have enough funds
//     */
//    @Test
//    public void transfer_notEnoughFunds_throwNotSufficientFundException() {
//        //Given
//        final String emailFrom = "alek@pl";
//        final String emailTo = "bartek@pl";
//        final Client clientFrom = new Client("Alek", emailFrom, singletonList(new Account(100, "PLN")));
//        final Client clientTo = new Client("Bartek", emailTo, singletonList(new Account(500, "PLN")));
//
//        final double amount = 1000;
//        when(repository.findByEmailIgnoreCase(emailFrom)).thenReturn(clientFrom);
//        when(repository.findByEmailIgnoreCase(emailTo)).thenReturn(clientTo);
//        //When/Then
//
//        // never().verify(repository.save(clientFrom));
//        Assertions.assertThrows(
//                NotSufficientFundException.class,
//                () -> service.transfer(emailFrom, emailTo, amount)
//        );
//    }
//
//    /**
//     * Throw exception if transferred amount das a negative value
//     */
//    @Test
//    public void transfer_negativeAmount_throwIllegalArgumentException() {
//        //Given
//        final String emailFrom = "alek@pl";
//        final String emailTo = "bartek@pl";
//        final double amount = -1000;
//
//        //When/Then
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.transfer(emailFrom, emailTo, amount)
//        );
//    }
//
//    /**
//     * Throw exception if try to transfer money to the same client
//     */
//    @Test
//    public void transfer_toTheSameClient_thrownException() {
//        //Given
//        final String email = "alek@pl";
//        //When/Then
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.transfer(email, email, 10)
//        );
//    }
//
//    /**
//     * Verify the existence of the client
//     */
//    @Test
//    public void transfer_fundsToNonExistingClient_throwsNoSuchElementException() {
//        //Given
//        String emailFrom = "karolina@gmail.com";
//        String emailTo = "michal@gmail.com";
//        double amount = 500;
//
//        //When
//        Assertions.assertThrows(NoSuchElementException.class, () -> service.transfer(emailFrom, emailTo, amount));
//    }
//
//    /**
//     * Throw exception if saved clients email already exists
//     */
//    @Test
//    public void save_twoDifferentClientWithTheSameMail_thrownException() {
//        //Given
//        String email = "a@a";
//        Client client = new Client("Bartek", email, singletonList(new Account(100, "PLN")));
//        Client client1 = new Client("Pawel", "a@a", singletonList(new Account(100, "PLN")));
//        when(repository.findByEmailIgnoreCase(email)).thenReturn(client);
//        //When/Then
//        Assertions.assertThrows(ClientAlreadyExistsException.class, () -> service.save(client1)
//        );
//    }
//
//    /**
//     * Ignore case when saving the client
//     */
//    @Test
//    public void save_clientWithUpperCaseEmailOk_clientSaved() throws ClientAlreadyExistsException {
//        //Given
//        String email = "UPPER@eMail.PL";
//        Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));
//        when(repository.findByEmailIgnoreCase(email)).thenReturn(client);
//        //When
//        service.save(client);
//        //Then
//        Client exceptedClient = new Client("Alek", "upper@email.pl", singletonList(new Account(100, "PLN")));
//
//        verify(repository).save(exceptedClient);
//    }
//
//    /**
//     * Throw exception when try to save already existing client
//     */
//    @Test
//    public void save_alreadyExistingClient_throwClientAlreadyExistsException() {
//        //Given
//        String email = "alek@pl";
//        Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));
//        Client client2 = new Client("Alek", "alek@pl", singletonList(new Account(100, "PLN")));
//        when(repository.findByEmailIgnoreCase(email)).thenReturn(client);
//        //When/Then
//        Assertions.assertThrows(ClientAlreadyExistsException.class, () -> service.save(client2));
//    }
//
//    /**
//     * Do not allow save client with null name
//     */
//    @Test
//    public void save_clientWithNullName_throwsIllegalArgumentException() {
//        //Given
//        String name = null;
//        Client client = new Client(name, "Alek@pl", singletonList(new Account(100, "PLN")));
//        //When/Then
//        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
//    }
//
//    /**
//     * Do not allow save client with null email
//     */
//    @Test
//    public void save_clientWithNullEmail_throwsIllegalArgumentException() {
//        //Given
//        String email = null;
//        Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));
//        //When/Then
//        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
//    }
//
//    /**
//     * Do not allow save client with negative balance
//     */
//    @Test
//    public void save_ClientWithNegativeBalance_throwsIllegalArgumentException() {
//        //Given
//        Client client = new Client("Alek", "alek@pl", singletonList(new Account(-100, "PLN")));
//        //When/Then
//        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
//    }
//
//    /**
//     * Find client by writing upper case email
//     */
//    @Test
//    public void findByEmail_upperCaseInputEmailOk_clientFound() {
//        //Given
//        String email = "alek@pl";
//        Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));
//        when(repository.findByEmailIgnoreCase(email)).thenReturn(client);
//        //When
//        Client actualClient = service.findByEmail("ALEK@PL");
//        //Then
//        assertEquals(client, actualClient);
//    }
//
//
//    @Test
//    public void withdraw_correctAmount_balanceChangedCorrectly() {
//        //Given
//        final String email = "alek@gmail.con";
//        final Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));
//        when(repository.findByEmailIgnoreCase(email)).thenReturn(client);
//        //When
//        service.withdraw(email, 50);
//        //Then
//        Client expectedClient = new Client("Alek", email, singletonList(new Account(50, "PLN")));
//        verify(repository).save(expectedClient);
//    }
//
//    @Test
//    public void withdraw_correctFloatingPointAmount_balanceChangedCorrectly() {
//        //Given
//        final String email = "alek@gmail.con";
//        final Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));
//        when(repository.findByEmailIgnoreCase(email)).thenReturn(client);
//        //When
//        service.withdraw(email, 50.5);
//        //Then
//        Client expectedClient = new Client("Alek", email, singletonList(new Account(49.5, "PLN")));
//        verify(repository).save(expectedClient);
//    }
//
//    @Test
//    public void withdraw_allBalance_balanceSetToZero() {
//        //Given
//        final String email = "alek@gmail.con";
//        final Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));
//        when(repository.findByEmailIgnoreCase(email)).thenReturn(client);
//
//        //When
//        service.withdraw(email, 100);
//
//        //Then
//        Client expectedClient = new Client("Alek", email, singletonList(new Account(0, "PLN")));
//        verify(repository).save(expectedClient);
//    }
//
//    @Test
//    public void withdraw_negativeAmount_throwsIllegalArgumentException() {
//        //Given
//        final String email = "alek@gmail.con";
//        final int amount = -100;
//
//        //When/Then
//        Assertions.assertThrows(IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_zeroAmount_throwsIllegalArgumentException() {
//        //Given
//        final String email = "alek@gmail.con";
//        final int amount = 0;
//        //When/Then
//        Assertions.assertThrows(IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_amountBiggerThenAmount_throwsNoSufficientFundsException() {
//        //Given
//        final String email = "alek@gmail.con";
//        final Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));
//        when(repository.findByEmailIgnoreCase(email)).thenReturn(client);
//        final int amount = 1000;
//        //When/Then
//        Assertions.assertThrows(NotSufficientFundException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_incorrectEmail_throwsNoSuchElementException() {
//        //Given
//        final String email = "incorrect_email@gmail.com";
//        //When/Then
//        final int amount = 1000;
//        Assertions.assertThrows(NoSuchElementException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_nullEmail_throwsIllegalArgumentException() {
//        //Given
//        final String email = null;
//        //When/Then
//        final int amount = 1000;
//        Assertions.assertThrows(IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//}
