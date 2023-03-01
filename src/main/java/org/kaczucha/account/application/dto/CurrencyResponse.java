package org.kaczucha.account.application.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class CurrencyResponse {
    private String base;
    private LocalDate date;
    private Map<String, Double> rates;
    private boolean success;
    private long timestamp;

}
