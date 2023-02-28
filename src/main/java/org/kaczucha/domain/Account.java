package org.kaczucha.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACCOUNTS")
@Data
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "ACCOUNTS_SEQ", allocationSize = 1)
    @Column(name = "ACCOUNT_ID")
    private long id;

    @Column(name = "BALANCE")
    private double balance;

    @Column(name = "CURRENCY", length = 3)
    private String currency;

    public Account(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }
}
