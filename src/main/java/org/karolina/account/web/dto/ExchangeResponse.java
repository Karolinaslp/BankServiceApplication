package org.karolina.account.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeResponse {
    private String date;
    private Map<String, Long> info;
    private ExchangeQuery query;
    private double result;
    private boolean success;

    @JsonSerialize
    public static class ExchangeQuery {
        private BigDecimal amount;
        private String from;
        private String to;
    }
}
