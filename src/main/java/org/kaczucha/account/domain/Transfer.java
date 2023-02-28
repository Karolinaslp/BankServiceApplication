package org.kaczucha.account.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@Entity
@Table(name = "TRANSACTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {
    @Id
    @GeneratedValue
    @Column(name = "TRANSACTIONS_ID")
    private long id;

    @Column(name = "AMOUNT")
    private double amount;
    @Column(name = "CURRENCY")
    private String currency;

    @CreatedDate
    @Column(name = "TRANSACTION_DATE")
    private OffsetDateTime transactionDate;

    @Column(name = "FROM_ACCOUNT_ID")
    private long fromAccountId;

    @Column(name = "TO_ACCOUNT_ID")
    private long toAccountId;
}
