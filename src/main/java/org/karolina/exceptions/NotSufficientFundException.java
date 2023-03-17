package org.karolina.exceptions;

public class NotSufficientFundException extends RuntimeException {
    public NotSufficientFundException(String message) {
        super(message);
    }
}
