package org.kaczucha;

import org.kaczucha.domain.Account;
import org.kaczucha.domain.Client;
import org.kaczucha.exceptions.ClientAlreadyExistsException;
import org.kaczucha.repository.ClientJpaRepository;
import org.kaczucha.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BankServiceApplication implements CommandLineRunner {
    private final BankService bankService;
    private final ClientJpaRepository repository;

    @Autowired
    public BankServiceApplication(BankService bankService, ClientJpaRepository repository) {
        this.bankService = bankService;
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BankServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {

        List<Client> list = repository.findByNameIgnoreCase("Karol");
        list.forEach(System.out::println);

        Page<Client> page = repository.findByNameIgnoreCase("Karol", PageRequest.of(0, 1, Sort.by("email").descending()));
        page.getContent().forEach(System.out::println);
        int totalPages = page.getTotalPages();

        for (int i = 1; i < totalPages; i++) {
            Page<Client> page1 = repository.findByNameIgnoreCase("Karol", PageRequest.of(i, 1, Sort.by("email")));
            page1.getContent().forEach(System.out::println);
        }
//        try (final Scanner scanner = new Scanner(System.in)) {
//            while (true) {
//                System.out.println("1 - add user");
//                System.out.println("2 - find user");
//                System.out.println("3 - exit app");
//                final String next = scanner.next();
//                if (next.equals("1")) {
//                    addUser(scanner);
//                }
//                if (next.equals("2")) {
//                    printUser(scanner);
//                }
//                if (next.equals("3")) {
//                    break;
//                }
//            }
//        }
    }

    private void printUser(Scanner scanner) {
        System.out.println("Enter email");
        final String email = scanner.next();
        System.out.println(bankService.findByEmail(email));
    }

    private void addUser(Scanner scanner) {
        System.out.println("Enter name");
        final String name = scanner.next();
        System.out.println("Enter email");
        final String email = scanner.next();
        System.out.println("Enter balance");
        final double balance = scanner.nextDouble();
        final Account account = new Account(balance, "PLN");
        final List<Account> accounts = List.of(account);
        try {
            bankService.save(new Client(name, email, accounts));
        } catch (ClientAlreadyExistsException e) {
            System.out.println("Client with this email already exist");
        }
    }
}
