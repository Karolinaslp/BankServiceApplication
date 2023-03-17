package org.karolina.account.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "TRANSACTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_seq")
    @SequenceGenerator(name = "transactions_seq", allocationSize = 1)
    @Column(name = "TRANSACTIONS_ID")
    private long id;

    @Column(name = "AMOUNT", scale = 2)
    private BigDecimal amount;

    @Column(name = "CURRENCY", length = 3)
    private String currency;

    @Column(name = "TRANSACTION_DATE")
    private OffsetDateTime transactionDate;

    @Column(name = "FROM_ACCOUNT_ID")
    private long fromAccountId;

    @Column(name = "TO_ACCOUNT_ID")
    private long toAccountId;
}
