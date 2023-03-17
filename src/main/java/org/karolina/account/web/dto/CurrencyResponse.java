package org.karolina.account.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyResponse {
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
    private boolean success;
    private long timestamp;

}
