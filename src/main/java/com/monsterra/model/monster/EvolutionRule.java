package com.monsterra.model.monster;

import java.util.Objects;

/**
 * Describes the conditions under which a Species can evolve into another Species.
 */
public class EvolutionRule {
    private final String targetSpeciesId;
    private final Integer requiredLevel;
    private final String requiredItem;
    private final String requiredLocation;
    private final String requiredTime;
    private final String requiredCondition;

    private EvolutionRule(Builder builder) {
        this.targetSpeciesId = builder.targetSpeciesId;
        this.requiredLevel = builder.requiredLevel;
        this.requiredItem = builder.requiredItem;
        this.requiredLocation = builder.requiredLocation;
        this.requiredTime = builder.requiredTime;
        this.requiredCondition = builder.requiredCondition;
    }

    public String getTargetSpeciesId() {
        return targetSpeciesId;
    }

    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    public String getRequiredItem() {
        return requiredItem;
    }

    public String getRequiredLocation() {
        return requiredLocation;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public String getRequiredCondition() {
        return requiredCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvolutionRule that = (EvolutionRule) o;
        return Objects.equals(targetSpeciesId, that.targetSpeciesId) &&
                Objects.equals(requiredLevel, that.requiredLevel) &&
                Objects.equals(requiredItem, that.requiredItem) &&
                Objects.equals(requiredLocation, that.requiredLocation) &&
                Objects.equals(requiredTime, that.requiredTime) &&
                Objects.equals(requiredCondition, that.requiredCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetSpeciesId, requiredLevel, requiredItem, requiredLocation, requiredTime, requiredCondition);
    }

    @Override
    public String toString() {
        return "EvolutionRule{target=" + targetSpeciesId +
                (requiredLevel != null ? ", level>=" + requiredLevel : "") +
                (requiredItem != null ? ", item=" + requiredItem : "") +
                "}";
    }

    public static class Builder {
        private String targetSpeciesId;
        private Integer requiredLevel;
        private String requiredItem;
        private String requiredLocation;
        private String requiredTime;
        private String requiredCondition;

        public Builder targetSpeciesId(String targetSpeciesId) {
            this.targetSpeciesId = targetSpeciesId;
            return this;
        }

        public Builder requiredLevel(int requiredLevel) {
            this.requiredLevel = requiredLevel;
            return this;
        }

        public Builder requiredItem(String requiredItem) {
            this.requiredItem = requiredItem;
            return this;
        }

        public Builder requiredLocation(String requiredLocation) {
            this.requiredLocation = requiredLocation;
            return this;
        }

        public Builder requiredTime(String requiredTime) {
            this.requiredTime = requiredTime;
            return this;
        }

        public Builder requiredCondition(String requiredCondition) {
            this.requiredCondition = requiredCondition;
            return this;
        }

        public EvolutionRule build() {
            if (targetSpeciesId == null || targetSpeciesId.isBlank()) {
                throw new IllegalArgumentException("targetSpeciesId must not be blank");
            }
            return new EvolutionRule(this);
        }
    }
}
