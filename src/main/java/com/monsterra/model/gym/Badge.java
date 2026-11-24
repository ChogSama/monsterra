package com.monsterra.model.gym;

import java.util.Objects;

/**
 * Represents a Gym Badge rewarded upon defeating a Gym Leader.
 */
public class Badge {
    private final String id;
    private final String name;
    private final String description;
    private final String statBoost;

    public Badge(String id, String name, String description, String statBoost) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Badge ID cannot be null or empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Badge name cannot be null or empty.");
        }
        this.id = id;
        this.name = name;
        this.description = description != null ? description : "";
        this.statBoost = statBoost != null ? statBoost : "";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatBoost() {
        return statBoost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(id, badge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Badge{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
