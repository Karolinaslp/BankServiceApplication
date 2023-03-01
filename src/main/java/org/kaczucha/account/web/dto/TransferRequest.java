package org.kaczucha.account.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferRequest {
    private double amount;
    private String currency;
    private long fromAccountId;
    private long toAccountId;
}
