package org.rejowska.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rejowska.domain.Client;
import org.rejowska.exceplion.NotSufficientFundException;
import org.rejowska.repository.InMemoryClientRepository;
import org.rejowska.exceplion.ClientAlreadyExistsException;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankServiceTest {
    private BankService service;
    private ArrayList<Client> clients;

    @BeforeEach
    public void setup() {
        clients = new ArrayList<>();
        service = new BankService(new InMemoryClientRepository(clients));
    }

    @Test
    public void transfer_allParamsOk_foundsTransferred() {
        //Given
        final String emailFrom = "alek@pl";
        final String emailTo = "bartek@pl";
        final Client clientFrom = new Client("Alek", emailFrom, 1000);
        final Client clientTo = new Client("Bartek", emailTo, 500);
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = 100;
        //When
        service.transfer(emailFrom, emailTo, amount);
        //Then
        final Client actualFromClient = service.findByEmail(emailFrom);
        final Client actualToClient = service.findByEmail(emailTo);
        final Client expectedFromClient = new Client("Alek", emailFrom, 900);
        final Client expectedToClient = new Client("Bartek", emailTo, 600);

        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(expectedFromClient)
                .isEqualTo(actualFromClient);

        softAssertions
                .assertThat(expectedToClient)
                .isEqualTo(actualToClient);
        softAssertions.assertAll();
    }

    @Test
    public void transfer_allParamsOk_fundsTransferred() {
        //Given
        final String emailFrom = "alek@pl";
        final String emailTo = "bartek@pl";
        final Client clientFrom = new Client("Alek", emailFrom, 1000);
        final Client clientTo = new Client("Bartek", emailTo, 500);
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = 1000;
        //When
        service.transfer(emailFrom, emailTo, amount);
        //Then
        final Client actualFromClient = service.findByEmail(emailFrom);
        final Client actualToClient = service.findByEmail(emailTo);
        final Client expectedFromClient = new Client("Alek", emailFrom, 0);
        final Client expectedToClient = new Client("Bartek", emailTo, 1500);

        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(expectedFromClient)
                .isEqualTo(actualFromClient);

        softAssertions
                .assertThat(expectedToClient)
                .isEqualTo(actualToClient);
        softAssertions.assertAll();
    }

    /**
     * Throw exception when if client m do not have enough funds
     */
    @Test
    public void transfer_notEnoughFunds_throwNotSufficientFundException() {
        //Given
        final String emailFrom = "alek@pl";
        final String emailTo = "bartek@pl";
        final Client clientFrom = new Client("Alek", emailFrom, 100);
        final Client clientTo = new Client("Bartek", emailTo, 500);
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = 1000;
        //When/Then
        Assertions.assertThrows(
                NotSufficientFundException.class,
                () -> service.transfer(emailFrom, emailTo, amount)
        );
    }

    /**
     * Throw exception if transferred amount das a negative value
     */
    @Test
    public void transfer_negativeAmount_throwIllegalArgumentException() {
        //Given
        final String emailFrom = "alek@pl";
        final String emailTo = "bartek@pl";
        final Client clientFrom = new Client("Alek", emailFrom, 100);
        final Client clientTo = new Client("Bartek", emailTo, 500);
        clients.add(clientFrom);
        clients.add(clientTo);
        final double amount = -1000;
        //When/Then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(emailFrom, emailTo, amount)
        );
    }

    /**
     * Throw exception if try to transfer money to the same client
     */
    @Test
    public void transfer_toSameClient_thrownException() {
        //Given
        final String email = "alek@pl";
        final Client client = new Client("Alek", email, 100);
        clients.add(client);
        //When/Then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(email, email, 10)
        );
    }

    /**
     * Verify the existence of the client to
     */
    @Test
    public void transfer_fundsToNonExistingClient_throwsNoSuchElementException() {
        //Given
        String emailFrom = "karolina@gmail.com";
        Client clientFrom = new Client("Karolina", emailFrom, 5000);
        clients.add(clientFrom);
        String emailTo = "michal@gmail.com";
        double amount = 500;
        //When
        Assertions.assertThrows(NoSuchElementException.class, () -> service.transfer(emailFrom, emailTo, amount));
    }

    /**
     * Verify the existence of client the client from
     */
    @Test
    public void transfer_fundsFromNonExistingClient_throwsNoSuchElementException() {
        //Given
        String emailTo = "karolina@gmail.com";
        Client clientTo = new Client("Karolina", emailTo, 5000);
        clients.add(clientTo);
        String emailFrom = "michal@gmail.com";
        double amount = 4000;
        //When/Then
        Assertions.assertThrows(NoSuchElementException.class, () -> service.transfer(emailFrom, emailTo, amount));
    }

    /**
     * Throw exception if saved clients email already exists
     */
    @Test
    public void client_saveTwoDifferentClientWithTheSameMail_thrownException() {
        //Given
        Client client = new Client("Bartek", "a@a", 100);
        Client client1 = new Client("Pawel", "a@a", 100);
        clients.add(client);
        //When/Then
        Assertions.assertThrows(ClientAlreadyExistsException.class, () -> service.save(client)
        );
    }

    /**
     * Ignore case when saving the client
     */
    @Test
    public void save_clientWithUpperCaseEmailOk_clientSaved() throws ClientAlreadyExistsException {
        //Given
        String email = "UPPER@eMail.PL";
        Client client = new Client("Alek", email, 100);
        //When
        service.save(client);
        //Then
        Client actualClient = service.findByEmail(email);
        Client exceptedClient = new Client("Alek", "upper@email.pl", 100);

        assertEquals(exceptedClient, actualClient);
    }

    /**
     * Throw exception when try to save already existing client
     */
    @Test
    public void save_alreadyExistingClient_throwClientAlreadyExistsException() throws ClientAlreadyExistsException {
        //Given
        Client client = new Client("Alek", "alek@pl", 100);
        service.save(client);
        //When/Then
        Assertions.assertThrows(ClientAlreadyExistsException.class, () -> service.save(client));
    }


    /**
     * Do not allow save client with null name
     */
    @Test
    public void save_clientWithNullName_throwsIllegalArgumentException() {
        //Given
        String name = null;
        Client client = new Client(name, "Alek@pl", 100);
        //When/Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
    }

    /**
     * Do not allow save client with null email
     */
    @Test
    public void save_clientWithNullEmail_throwsIllegalArgumentException() {
        //Given
        String email = null;
        Client client = new Client("Alek", email, 100);
        //When/Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
    }

    /**
     * Do not allow save client with negative balance
     */
    @Test
    public void save_ClientWithNegativeBalance_throwsIllegalArgumentException() {
        //Given
        double balance = -100;
        Client client = new Client("Alek", "alek@pl", balance);
        //When/Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.save(client));
    }

    /**
     * Find client by writing upper case email
     */
    @Test
    public void findByEmail_upperCaseInputEmailOk_clientFound() {
        //Given
        Client expectedClient = new Client("Alek", "alek@pl", 100);
        clients.add(expectedClient);
        //When
        Client actualClient = service.findByEmail("ALEK@PL");
        //Then
        assertEquals(expectedClient, actualClient);
    }

    /**
     * Throw exception if client not exist in repository
     */
    @Test
    public void findByEmail_nonExistingEmailInput_throwsNoSuchElementException() {
        //Given/When/Then
        Assertions.assertThrows(NoSuchElementException.class,
                () -> service.findByEmail("invalid_email.pl")
        );
    }

    @Test
    public void withdraw_correctAmount_balanceChangedCorrectly() {
        //Given
        final String email = "alek@gmail.con";
        final Client client = new Client("Alek", email, 100);
        clients.add(client);
        //When
        service.withdraw(email, 50);
        //Then
        Client expectedClient = new Client("Alek", email, 50);
        Assertions.assertTrue(clients.contains(expectedClient));
    }

    @Test
    public void withdraw_correctFloatingPointAmount_balanceChangedCorrectly() {
        //Given
        final String email = "alek@gmail.con";
        final Client client = new Client("Alek", email, 100);
        clients.add(client);
        //When
        service.withdraw(email, 50.5);
        //Then
        Client expectedClient = new Client("Alek", email, 49.5);
        final Client actualClient = clients.get(0);
        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void withdraw_allBalance_balanceSetToZero() {
        //Given
        final String email = "alek@gmail.con";
        final Client client = new Client("Alek", email, 100);
        clients.add(client);
        service.withdraw(email, 100);
        //When/Then
        Client expectedClient = new Client("Alek", email, 0);
        final Client actualClient = clients.get(0);
        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void withdraw_negativeAmount_throwsIllegalArgumentException() {
        //Given
        final String email = "alek@gmail.con";
        final Client client = new Client("Alek", email, 100);
        clients.add(client);
        //When
        final int amount = -100;
        //Then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(email, amount)
        );
    }

    @Test
    public void withdraw_zeroAmount_throwsIllegalArgumentException() {
        //Given
        final String email = "alek@gmail.con";
        final Client client = new Client("Alek", email, 100);
        clients.add(client);
        final int amount = 0;
        //When/Then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(email, amount)
        );
    }

    @Test
    public void withdraw_amountBiggerThenAmount_throwsNoSufficientFundsException() {
        //Given
        final String email = "alek@gmail.con";
        final Client client = new Client("Alek", email, 100);
        clients.add(client);
        final int amount = 1000;
        //When/Then
        Assertions.assertThrows(NotSufficientFundException.class,
                () -> service.withdraw(email, amount)
        );
    }

    @Test
    public void withdraw_incorrectEmail_throwsNoSuchElementException() {
        //Given
        final String email = "incorrect_email@gmail.com";
        //When/Then
        final int amount = 1000;
        Assertions.assertThrows(NoSuchElementException.class,
                () -> service.withdraw(email, amount)
        );
    }

    @Test
    public void withdraw_upperCaseEmail_balanceChangedCorrectly() {
        //Given
        final String email = "ALEK@GMAIL.COM";
        final Client client = new Client("Alek", "alek@gmail.com", 100);
        clients.add(client);
        //When
        service.withdraw(email, 50);
        //Then
        Client expectedClient = new Client("Alek", "alek@gmail.com", 50);
        final Client actualClient = clients.get(0);
        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void withdraw_nullEmail_throwsIllegalArgumentException() {
        //Given
        final String email = null;
        //When/Then
        final int amount = 1000;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(email, amount)
        );
    }

}

