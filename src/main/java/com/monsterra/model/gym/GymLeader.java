package com.monsterra.model.gym;

import com.monsterra.model.player.Team;

import java.util.Objects;

/**
 * Represents a Gym Leader NPC in the game.
 */
public class GymLeader {
    private final String id;
    private final String name;
    private final String title;
    private final Team team;
    private final Badge badgeReward;

    public GymLeader(String id, String name, String title, Team team, Badge badgeReward) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Gym Leader ID cannot be null or empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Gym Leader name cannot be null or empty.");
        }
        if (team == null || team.isEmpty()) {
            throw new IllegalArgumentException("Gym Leader must have at least one Monster in their team.");
        }

        this.id = id;
        this.name = name;
        this.title = title != null ? title : "";
        this.team = team;
        this.badgeReward = badgeReward;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Team getTeam() {
        return team;
    }

    public Badge getBadgeReward() {
        return badgeReward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymLeader gymLeader = (GymLeader) o;
        return Objects.equals(id, gymLeader.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GymLeader{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
