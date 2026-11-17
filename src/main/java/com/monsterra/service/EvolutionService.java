package com.monsterra.service;

import com.monsterra.model.monster.*;

import java.util.List;
import java.util.Map;

/**
 * Orchestrates Monster evolution.
 * Checks evolution conditions and performs the evolution transformation.
 * Evolution preserves individual data (nickname, EXP, skills) while updating species-related data.
 */
public class EvolutionService {

    private final Map<String, Species> speciesRegistry;

    public EvolutionService(Map<String, Species> speciesRegistry) {
        if (speciesRegistry == null) {
            throw new IllegalArgumentException("Species registry must not be null");
        }
        this.speciesRegistry = Map.copyOf(speciesRegistry);
    }

    /**
     * Check if a Monster can evolve based on its current state.
     */
    public boolean canEvolve(Monster monster) {
        List<EvolutionRule> rules = monster.getSpecies().getEvolutionRules();
        if (rules.isEmpty()) {
            return false;
        }

        for (EvolutionRule rule : rules) {
            if (isRuleSatisfied(monster, rule)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the target species for evolution, if the Monster can evolve.
     */
    public Species getEvolutionTarget(Monster monster) {
        List<EvolutionRule> rules = monster.getSpecies().getEvolutionRules();
        for (EvolutionRule rule : rules) {
            if (isRuleSatisfied(monster, rule)) {
                return speciesRegistry.get(rule.getTargetSpeciesId());
            }
        }
        return null;
    }

    /**
     * Perform evolution on the Monster.
     * Returns the evolved Monster (a new instance with the new species).
     * The original Monster is replaced.
     */
    public Monster evolve(Monster monster) {
        Species targetSpecies = getEvolutionTarget(monster);
        if (targetSpecies == null) {
            throw new IllegalStateException("Monster cannot evolve: " + monster);
        }

        // Create a new monster with the evolved species, preserving level and EXP
        Monster evolved = new Monster(targetSpecies, monster.getLevel());

        // Preserve individual data
        if (monster.getNickname() != null) {
            evolved.setNickname(monster.getNickname());
        }
        evolved.setExperience(monster.getExperience());

        // Recalculate stats for the new species at the current level
        Stats baseStats = targetSpecies.getBaseStats();
        double growthFactor = 1.0 + monster.getLevel() * 0.02;
        evolved.setStats(baseStats.scaledBy(growthFactor));

        // Preserve skills (up to 4)
        for (var skill : monster.getSkills()) {
            try {
                evolved.addSkill(skill);
            } catch (IllegalStateException e) {
                // Skip if evolved monster already has 4 skills
                break;
            }
        }

        // Update types from new species
        evolved.healFull();
        evolved.markEvolved();

        return evolved;
    }

    /**
     * Check if a specific evolution rule is satisfied.
     */
    private boolean isRuleSatisfied(Monster monster, EvolutionRule rule) {
        // Check level requirement
        if (rule.getRequiredLevel() != null) {
            if (monster.getLevel() < rule.getRequiredLevel()) {
                return false;
            }
        }

        // Check item requirement (placeholder — would check inventory)
        // Check location requirement (placeholder — would check world state)
        // Check time requirement (placeholder — would check time context)
        // Check custom condition (placeholder — would evaluate condition expression)

        return true;
    }
}
