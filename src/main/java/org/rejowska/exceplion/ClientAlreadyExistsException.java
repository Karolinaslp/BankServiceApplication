package org.rejowska.exceplion;

public class ClientAlreadyExistsException extends Exception {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
