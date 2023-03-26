//package org.karolina.account.application;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.karolina.account.domain.Account;
//import org.karolina.client.domain.Client;
//
//import java.util.NoSuchElementException;
//
//import static java.util.Collections.singletonList;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class AccountServiceTest {
//
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
