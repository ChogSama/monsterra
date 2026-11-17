package com.monsterra.model.monster;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Monster species (the definition/static data for a species).
 * Species is shared data — multiple Monster instances belong to the same Species.
 */
public class Species {
    private final String id;
    private final String name;
    private final Stats baseStats;
    private final List<Type> baseTypes;
    private final List<String> learnableSkillIds;
    private final List<EvolutionRule> evolutionRules;

    public Species(String id, String name, Stats baseStats, List<Type> baseTypes,
                   List<String> learnableSkillIds, List<EvolutionRule> evolutionRules) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Species id must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Species name must not be blank");
        }
        if (baseStats == null) {
            throw new IllegalArgumentException("Base stats must not be null");
        }
        if (baseTypes == null || baseTypes.isEmpty() || baseTypes.size() > 2) {
            throw new IllegalArgumentException("Species must have 1-2 types");
        }
        this.id = id;
        this.name = name;
        this.baseStats = baseStats;
        this.baseTypes = List.copyOf(baseTypes);
        this.learnableSkillIds = learnableSkillIds != null ? List.copyOf(learnableSkillIds) : List.of();
        this.evolutionRules = evolutionRules != null ? List.copyOf(evolutionRules) : List.of();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Stats getBaseStats() {
        return baseStats;
    }

    public List<Type> getBaseTypes() {
        return baseTypes;
    }

    public List<String> getLearnableSkillIds() {
        return learnableSkillIds;
    }

    public List<EvolutionRule> getEvolutionRules() {
        return evolutionRules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return id.equals(species.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Species{" + name + " (id=" + id + "), types=" + baseTypes + "}";
    }
}
