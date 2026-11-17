package com.monsterra.model.monster;

import java.util.Objects;

/**
 * Represents a temporary status effect applied to a Monster during battle.
 */
public class StatusEffect {
    public enum Type {
        BURN,
        POISON,
        STUN,
        SLOW,
        SLEEP,
        PARALYSIS,
        FREEZE
    }

    private final Type type;
    private final int duration;
    private final int intensity;

    public StatusEffect(Type type, int duration, int intensity) {
        if (type == null) {
            throw new IllegalArgumentException("Status effect type must not be null");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        if (intensity < 0) {
            throw new IllegalArgumentException("Intensity must not be negative");
        }
        this.type = type;
        this.duration = duration;
        this.intensity = intensity;
    }

    public Type getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public int getIntensity() {
        return intensity;
    }

    /**
     * Create a new instance with decremented duration.
     * Returns null if duration reaches zero.
     */
    public StatusEffect tick() {
        int newDuration = duration - 1;
        if (newDuration <= 0) {
            return null;
        }
        return new StatusEffect(type, newDuration, intensity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusEffect that = (StatusEffect) o;
        return duration == that.duration && intensity == that.intensity && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, duration, intensity);
    }

    @Override
    public String toString() {
        return "StatusEffect{" + type + ", duration=" + duration + ", intensity=" + intensity + "}";
    }
}
