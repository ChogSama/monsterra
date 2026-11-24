package com.monsterra.model.gym;

import java.util.Objects;

/**
 * Represents a Gym challenge location in the world.
 */
public class Gym {
    private final String id;
    private final String name;
    private final GymLeader leader;
    private final Badge badge;
    private final int requiredLevel;

    public Gym(String id, String name, GymLeader leader, Badge badge, int requiredLevel) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Gym ID cannot be null or empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Gym name cannot be null or empty.");
        }
        if (leader == null) {
            throw new IllegalArgumentException("Gym must have a Gym Leader.");
        }

        this.id = id;
        this.name = name;
        this.leader = leader;
        this.badge = badge != null ? badge : leader.getBadgeReward();
        this.requiredLevel = Math.max(1, requiredLevel);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GymLeader getLeader() {
        return leader;
    }

    public Badge getBadge() {
        return badge;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gym gym = (Gym) o;
        return Objects.equals(id, gym.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Gym{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", leader=" + leader.getName() +
                '}';
    }
}
