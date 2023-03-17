package org.karolina.account.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    @NotNull
    private Long userId;

    @NotNull
    @DecimalMin("0,00")
    private BigDecimal balance;

    @NotBlank(message = "please provide currency")
    private String currency;
}
