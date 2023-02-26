package org.kaczucha.exceptions;

public class NotSufficientFundException extends RuntimeException {
    public NotSufficientFundException(String message) {
        super(message);
    }
}
