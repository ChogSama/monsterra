package com.monsterra.service;

import com.monsterra.model.monster.Monster;
import com.monsterra.model.monster.Stats;

/**
 * Handles EXP calculation, level-up logic, and stat progression.
 * This service is responsible for:
 * - Computing EXP required for the next level
 * - Checking if a Monster should level up
 * - Applying level-up stat growth
 */
public class ProgressionService {

    public static final int MAX_LEVEL = 100;

    /**
     * Calculate the EXP required to reach the next level.
     * Uses a simple formula: level * 100 (can be replaced with growth rate curves).
     */
    public int expToNextLevel(int currentLevel) {
        if (currentLevel < 1 || currentLevel > MAX_LEVEL) {
            throw new IllegalArgumentException("Level must be between 1 and " + MAX_LEVEL);
        }
        return currentLevel * 100;
    }

    /**
     * Calculate the total EXP needed to reach a given level from level 1.
     */
    public int totalExpToLevel(int targetLevel) {
        if (targetLevel < 1 || targetLevel > MAX_LEVEL) {
            throw new IllegalArgumentException("Level must be between 1 and " + MAX_LEVEL);
        }
        int total = 0;
        for (int lvl = 1; lvl < targetLevel; lvl++) {
            total += expToNextLevel(lvl);
        }
        return total;
    }

    /**
     * Check if a Monster can level up based on its current EXP.
     */
    public boolean canLevelUp(Monster monster) {
        if (monster.getLevel() >= MAX_LEVEL) {
            return false;
        }
        int required = expToNextLevel(monster.getLevel());
        return monster.getExperience() >= required;
    }

    /**
     * Perform a single level-up on the Monster.
     * Increases level, updates stats, and resets EXP overflow.
     * Returns true if the level-up was performed.
     */
    public boolean levelUp(Monster monster) {
        if (!canLevelUp(monster)) {
            return false;
        }

        int required = expToNextLevel(monster.getLevel());
        int newLevel = monster.getLevel() + 1;
        int expOverflow = monster.getExperience() - required;

        monster.setLevel(newLevel);
        monster.setExperience(expOverflow);

        // Grow stats: each stat grows by a small percentage per level
        Stats currentStats = monster.getStats();
        Stats baseStats = monster.getSpecies().getBaseStats();

        // Stat growth formula: baseStat * (1 + level * 0.02)
        double growthFactor = 1.0 + newLevel * 0.02;
        Stats newStats = baseStats.scaledBy(growthFactor);
        monster.setStats(newStats);

        // Heal to new max HP on level up
        monster.healFull();

        return true;
    }

    /**
     * Process all pending level-ups for a Monster.
     * Returns the number of levels gained.
     */
    public int processAllLevelUps(Monster monster) {
        int levelsGained = 0;
        while (canLevelUp(monster)) {
            levelUp(monster);
            levelsGained++;
        }
        return levelsGained;
    }

    /**
     * Award EXP to a Monster and process any level-ups.
     * Returns the number of levels gained.
     */
    public int awardExp(Monster monster, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("EXP must not be negative");
        }
        if (monster.getLevel() >= MAX_LEVEL) {
            return 0;
        }

        monster.gainExperience(exp);
        return processAllLevelUps(monster);
    }

    /**
     * Calculate EXP gained from defeating a Monster.
     * Simple formula: defeated monster's level * 10.
     */
    public int calculateExpGained(Monster defeatedMonster) {
        return defeatedMonster.getLevel() * 10;
    }
}
