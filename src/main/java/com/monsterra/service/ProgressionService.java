package com.monsterra.service;

import com.monsterra.model.monster.Monster;

import java.util.List;

/**
 * Service interface governing experience awards, level ups, stat scaling, and Monster evolution.
 */
public interface ProgressionService {

    /**
     * Awards experience points to a target Monster.
     *
     * @param monster the recipient Monster
     * @param exp the amount of experience points to grant
     */
    void grantExperience(Monster monster, int exp);

    /**
     * Evaluates whether the Monster has accumulated enough EXP to level up and applies all
     * consecutive level-ups if applicable.
     *
     * @param monster the Monster to check and level up
     * @return a list of level-up results for each level gained (empty if no level gained)
     */
    List<LevelUpResult> processLevelUp(Monster monster);

    /**
     * Checks if the Monster currently satisfies all conditions required to evolve.
     *
     * @param monster the Monster to evaluate
     * @return true if eligible for evolution, false otherwise
     */
    boolean canEvolve(Monster monster);

    /**
     * Executes the evolution of a Monster if eligible.
     *
     * @param monster the Monster to evolve
     * @return result describing the evolution outcome
     */
    EvolutionResult evolve(Monster monster);
}
