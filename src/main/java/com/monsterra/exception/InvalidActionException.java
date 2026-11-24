package com.monsterra.exception;

/**
 * Thrown when a player or AI submits an invalid or illegal battle action.
 */
public class InvalidActionException extends BattleException {
    public InvalidActionException(String message) {
        super(message);
    }

    public InvalidActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
