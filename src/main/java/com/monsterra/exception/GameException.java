package com.monsterra.exception;

/**
 * Base unchecked exception for all Monsterra domain and gameplay errors.
 */
public class GameException extends RuntimeException {
    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}
