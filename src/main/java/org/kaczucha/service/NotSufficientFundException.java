package org.kaczucha.service;

public class NotSufficientFundException extends RuntimeException {
    public NotSufficientFundException(String message) {
        super(message);
    }
}
