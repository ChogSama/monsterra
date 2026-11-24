package com.monsterra.service;

import com.monsterra.model.monster.Monster;

import java.util.Objects;

/**
 * Communicates the result of an evolution attempt on a Monster.
 */
public class EvolutionResult {
    private final boolean success;
    private final Monster evolvedMonster;
    private final String message;

    public EvolutionResult(boolean success, Monster evolvedMonster, String message) {
        this.success = success;
        this.evolvedMonster = evolvedMonster;
        this.message = message != null ? message : "";
    }

    public static EvolutionResult success(Monster monster, String message) {
        return new EvolutionResult(true, monster, message);
    }

    public static EvolutionResult failure(String message) {
        return new EvolutionResult(false, null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public Monster getEvolvedMonster() {
        return evolvedMonster;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvolutionResult that = (EvolutionResult) o;
        return success == that.success &&
                Objects.equals(evolvedMonster, that.evolvedMonster) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, evolvedMonster, message);
    }

    @Override
    public String toString() {
        return "EvolutionResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", monster=" + (evolvedMonster != null ? evolvedMonster.getNickname() : "null") +
                '}';
    }
}
