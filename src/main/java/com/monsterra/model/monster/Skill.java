package com.monsterra.model.monster;

import java.util.Objects;

/**
 * Immutable value object representing a Skill definition.
 * Skills are shared definitions — multiple Monsters may reference the same Skill.
 */
public class Skill {
    private final String id;
    private final String name;
    private final Type type;
    private final int power;
    private final int accuracy;
    private final int resourceCost;
    private final int priority;
    private final String effects;

    public Skill(String id, String name, Type type, int power, int accuracy,
                 int resourceCost, int priority, String effects) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Skill id must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Skill name must not be blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("Skill type must not be null");
        }
        if (power < 0) {
            throw new IllegalArgumentException("Power must not be negative");
        }
        if (accuracy < 0 || accuracy > 100) {
            throw new IllegalArgumentException("Accuracy must be between 0 and 100");
        }
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.resourceCost = resourceCost;
        this.priority = priority;
        this.effects = effects != null ? effects : "";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getResourceCost() {
        return resourceCost;
    }

    public int getPriority() {
        return priority;
    }

    public String getEffects() {
        return effects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id.equals(skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Skill{" + name + " (id=" + id + "), type=" + type +
                ", power=" + power + ", accuracy=" + accuracy + "}";
    }
}
