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
public class ExchangeRequest {
    @NotNull
    @DecimalMin("0,00")
    private BigDecimal amount;
    @NotBlank(message = "please provide from currency")
    private String fromCurrency;
    @NotBlank(message = "please provide to currency")
    private String toCurrency;
}
