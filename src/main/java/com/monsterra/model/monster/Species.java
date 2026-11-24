package com.monsterra.model.monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Defines the static attributes shared by all Monsters of a given species.
 */
public class Species {
    private final String id;
    private final String name;
    private final Stats baseStats;
    private final List<Type> types;
    private final List<Skill> learnableSkills;
    private final int evolutionLevel;
    private final String targetSpeciesId;
    private final String description;

    public Species(String id, String name, Stats baseStats, List<Type> types,
                   List<Skill> learnableSkills, int evolutionLevel, String targetSpeciesId, String description) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Species ID cannot be null or empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Species name cannot be null or empty.");
        }
        if (baseStats == null) {
            throw new IllegalArgumentException("Base stats cannot be null.");
        }
        if (types == null || types.isEmpty()) {
            throw new IllegalArgumentException("Species must have at least one elemental type.");
        }

        this.id = id;
        this.name = name;
        this.baseStats = baseStats;
        this.types = List.copyOf(types);
        this.learnableSkills = learnableSkills != null ? List.copyOf(learnableSkills) : Collections.emptyList();
        this.evolutionLevel = evolutionLevel;
        this.targetSpeciesId = targetSpeciesId;
        this.description = description != null ? description : "";
    }

    public Species(String id, String name, Stats baseStats, List<Type> types) {
        this(id, name, baseStats, types, new ArrayList<>(), 0, null, "");
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

    public List<Type> getTypes() {
        return types;
    }

    public List<Skill> getLearnableSkills() {
        return learnableSkills;
    }

    public int getEvolutionLevel() {
        return evolutionLevel;
    }

    public String getTargetSpeciesId() {
        return targetSpeciesId;
    }

    public String getDescription() {
        return description;
    }

    public boolean canEvolveAt(int currentLevel) {
        return targetSpeciesId != null && evolutionLevel > 0 && currentLevel >= evolutionLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return Objects.equals(id, species.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Species{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", types=" + types +
                ", baseStats=" + baseStats +
                '}';
    }
}
