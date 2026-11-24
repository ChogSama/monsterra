package com.monsterra.service;

import com.monsterra.model.monster.Stats;

import java.util.Objects;

/**
 * Encapsulates level and stat changes resulting from a Monster leveling up.
 */
public class LevelUpResult {
    private final int previousLevel;
    private final int newLevel;
    private final Stats previousStats;
    private final Stats newStats;

    public LevelUpResult(int previousLevel, int newLevel, Stats previousStats, Stats newStats) {
        this.previousLevel = previousLevel;
        this.newLevel = newLevel;
        this.previousStats = previousStats;
        this.newStats = newStats;
    }

    public int getPreviousLevel() {
        return previousLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public Stats getPreviousStats() {
        return previousStats;
    }

    public Stats getNewStats() {
        return newStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUpResult that = (LevelUpResult) o;
        return previousLevel == that.previousLevel &&
                newLevel == that.newLevel &&
                Objects.equals(previousStats, that.previousStats) &&
                Objects.equals(newStats, that.newStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousLevel, newLevel, previousStats, newStats);
    }

    @Override
    public String toString() {
        return "LevelUpResult{" +
                "from=" + previousLevel +
                ", to=" + newLevel +
                ", prevStats=" + previousStats +
                ", newStats=" + newStats +
                '}';
    }
}
