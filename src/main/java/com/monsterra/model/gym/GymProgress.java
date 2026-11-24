package com.monsterra.model.gym;

import java.util.Objects;

/**
 * Tracks player progression status for a specific Gym.
 */
public class GymProgress {
    private final String gymId;
    private boolean defeated;
    private boolean badgeObtained;
    private long completedTimestamp;

    public GymProgress(String gymId, boolean defeated, boolean badgeObtained, long completedTimestamp) {
        if (gymId == null || gymId.isBlank()) {
            throw new IllegalArgumentException("Gym ID cannot be null or empty.");
        }
        this.gymId = gymId;
        this.defeated = defeated;
        this.badgeObtained = badgeObtained;
        this.completedTimestamp = completedTimestamp;
    }

    public GymProgress(String gymId) {
        this(gymId, false, false, 0L);
    }

    public String getGymId() {
        return gymId;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
        if (defeated && completedTimestamp == 0L) {
            this.completedTimestamp = System.currentTimeMillis();
        }
    }

    public boolean isBadgeObtained() {
        return badgeObtained;
    }

    public void setBadgeObtained(boolean badgeObtained) {
        this.badgeObtained = badgeObtained;
    }

    public long getCompletedTimestamp() {
        return completedTimestamp;
    }

    public void setCompletedTimestamp(long completedTimestamp) {
        this.completedTimestamp = completedTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymProgress that = (GymProgress) o;
        return Objects.equals(gymId, that.gymId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gymId);
    }

    @Override
    public String toString() {
        return "GymProgress{" +
                "gymId='" + gymId + '\'' +
                ", defeated=" + defeated +
                ", badgeObtained=" + badgeObtained +
                '}';
    }
}
