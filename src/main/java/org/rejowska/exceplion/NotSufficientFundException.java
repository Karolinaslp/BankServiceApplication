package org.rejowska.exceplion;

public class NotSufficientFundException extends RuntimeException {
    public NotSufficientFundException(String message) {
        super(message);
    }
}
