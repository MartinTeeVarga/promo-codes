package com.czequered.promocodes.service;

/**
 * @author Martin Varga
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
    }

    public InvalidTokenException(String s) {
        super(s);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
