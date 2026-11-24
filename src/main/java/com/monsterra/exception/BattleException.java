package com.monsterra.exception;

/**
 * Thrown when an illegal operation or internal error occurs during a Battle.
 */
public class BattleException extends GameException {
    public BattleException(String message) {
        super(message);
    }

    public BattleException(String message, Throwable cause) {
        super(message, cause);
    }
}
