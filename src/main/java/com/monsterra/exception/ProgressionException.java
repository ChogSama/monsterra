package com.monsterra.exception;

/**
 * Thrown when an illegal progression or evolution operation is attempted.
 */
public class ProgressionException extends GameException {
    public ProgressionException(String message) {
        super(message);
    }

    public ProgressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
