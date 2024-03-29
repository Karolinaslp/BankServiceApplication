package org.karolina.account.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "ACCOUNTS_SEQ", allocationSize = 1)
    @Column(name = "ACCOUNT_ID")
    private long id;

    @Column(name = "BALANCE", scale = 2)
    private BigDecimal balance;

    @Column(name = "CURRENCY", length = 3)
    private String currency;

    @Column(name = "USER_ID")
    private Long userId;

    public Account(BigDecimal balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }
}
