package org.kaczucha.account.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransferRequest {
    private BigDecimal amount;
    private String currency;
    private long fromAccountId;
    private long toAccountId;
}
