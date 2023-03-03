package org.kaczucha.account.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRequest {
    private BigDecimal amount;
    private String fromCurrency;
    private String toCurrency;
}
