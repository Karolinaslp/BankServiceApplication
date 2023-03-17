package org.karolina.account.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransferRequest {
    @NotNull
    @DecimalMin("0,00")
    private BigDecimal amount;
    @NotBlank(message = "please provide currency")
    private String currency;
    @NotNull
    private long fromAccountId;
    @NotNull
    private long toAccountId;
}
