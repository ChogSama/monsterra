package com.monsterra.model.monster;

import java.util.Objects;

/**
 * Value object representing combat statistics of a Monster.
 */
public class Stats {
    private final int maxHp;
    private final int attack;
    private final int defense;
    private final int specialAttack;
    private final int specialDefense;
    private final int speed;

    public Stats(int maxHp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        if (maxHp < 0 || attack < 0 || defense < 0 || specialAttack < 0 || specialDefense < 0 || speed < 0) {
            throw new IllegalArgumentException("Stat values cannot be negative.");
        }
        this.maxHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }

    public Stats(int maxHp, int attack, int defense, int speed) {
        this(maxHp, attack, defense, attack, defense, speed);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public Stats add(Stats other) {
        if (other == null) {
            return this;
        }
        return new Stats(
                this.maxHp + other.maxHp,
                this.attack + other.attack,
                this.defense + other.defense,
                this.specialAttack + other.specialAttack,
                this.specialDefense + other.specialDefense,
                this.speed + other.speed
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return maxHp == stats.maxHp &&
                attack == stats.attack &&
                defense == stats.defense &&
                specialAttack == stats.specialAttack &&
                specialDefense == stats.specialDefense &&
                speed == stats.speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxHp, attack, defense, specialAttack, specialDefense, speed);
    }

    @Override
    public String toString() {
        return "Stats{" +
                "maxHp=" + maxHp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", specialAttack=" + specialAttack +
                ", specialDefense=" + specialDefense +
                ", speed=" + speed +
                '}';
    }
}
