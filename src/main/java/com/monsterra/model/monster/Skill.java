package com.monsterra.model.monster;

import java.util.Objects;

/**
 * Combat ability that can be learned and executed by Monsters.
 */
public class Skill {
    private final String id;
    private final String name;
    private final Type type;
    private final int power;
    private final int accuracy;
    private int pp;
    private final int maxPp;
    private final int priority;
    private final String description;

    public Skill(String id, String name, Type type, int power, int accuracy, int maxPp, int priority, String description) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Skill ID cannot be null or empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Skill name cannot be null or empty.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Skill type cannot be null.");
        }
        if (accuracy < 0 || accuracy > 100) {
            throw new IllegalArgumentException("Skill accuracy must be between 0 and 100.");
        }
        if (maxPp <= 0) {
            throw new IllegalArgumentException("Skill max PP must be positive.");
        }

        this.id = id;
        this.name = name;
        this.type = type;
        this.power = Math.max(0, power);
        this.accuracy = accuracy;
        this.maxPp = maxPp;
        this.pp = maxPp;
        this.priority = priority;
        this.description = description != null ? description : "";
    }

    public Skill(String id, String name, Type type, int power, int accuracy, int maxPp, String description) {
        this(id, name, type, power, accuracy, maxPp, 0, description);
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

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = Math.clamp(pp, 0, this.maxPp);
    }

    public int getMaxPp() {
        return maxPp;
    }

    public int getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUsable() {
        return pp > 0;
    }

    public boolean consumePp() {
        if (pp > 0) {
            pp--;
            return true;
        }
        return false;
    }

    public void restorePp(int amount) {
        if (amount > 0) {
            this.pp = Math.min(this.maxPp, this.pp + amount);
        }
    }

    public void restoreAllPp() {
        this.pp = this.maxPp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", power=" + power +
                ", accuracy=" + accuracy +
                ", pp=" + pp + "/" + maxPp +
                '}';
    }
}
