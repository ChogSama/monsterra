package com.monsterra.ai;

import com.monsterra.model.monster.Type;

import java.util.Objects;

/**
 * Summary of observed player behavioral patterns and tendencies.
 */
public class PlayerPattern {
    private final Type preferredType;
    private final String preferredSkillId;
    private final double switchFrequency;
    private final double aggressionScore;

    public PlayerPattern(Type preferredType, String preferredSkillId, double switchFrequency, double aggressionScore) {
        this.preferredType = preferredType;
        this.preferredSkillId = preferredSkillId;
        this.switchFrequency = Math.clamp(switchFrequency, 0.0, 1.0);
        this.aggressionScore = Math.clamp(aggressionScore, 0.0, 1.0);
    }

    public Type getPreferredType() {
        return preferredType;
    }

    public String getPreferredSkillId() {
        return preferredSkillId;
    }

    public double getSwitchFrequency() {
        return switchFrequency;
    }

    public double getAggressionScore() {
        return aggressionScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPattern that = (PlayerPattern) o;
        return Double.compare(that.switchFrequency, switchFrequency) == 0 &&
                Double.compare(that.aggressionScore, aggressionScore) == 0 &&
                preferredType == that.preferredType &&
                Objects.equals(preferredSkillId, that.preferredSkillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preferredType, preferredSkillId, switchFrequency, aggressionScore);
    }

    @Override
    public String toString() {
        return "PlayerPattern{" +
                "preferredType=" + preferredType +
                ", preferredSkillId='" + preferredSkillId + '\'' +
                ", switchFreq=" + switchFrequency +
                ", aggression=" + aggressionScore +
                '}';
    }
}
