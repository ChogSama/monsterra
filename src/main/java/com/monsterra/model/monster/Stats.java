package com.monsterra.model.monster;

import java.util.Objects;

/**
 * Immutable value object representing the combat stats of a Monster.
 * Contains the six core stats: HP, Attack, Defense, Special Attack, Special Defense, Speed.
 */
public class Stats {
    private final int maxHP;
    private final int attack;
    private final int defense;
    private final int specialAttack;
    private final int specialDefense;
    private final int speed;

    public Stats(int maxHP, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        if (maxHP < 0 || attack < 0 || defense < 0 || specialAttack < 0 || specialDefense < 0 || speed < 0) {
            throw new IllegalArgumentException("Stats must not be negative");
        }
        this.maxHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }

    public int getMaxHP() {
        return maxHP;
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

    /**
     * Create a new Stats instance with all values scaled by a factor.
     */
    public Stats scaledBy(double factor) {
        return new Stats(
                (int) Math.round(maxHP * factor),
                (int) Math.round(attack * factor),
                (int) Math.round(defense * factor),
                (int) Math.round(specialAttack * factor),
                (int) Math.round(specialDefense * factor),
                (int) Math.round(speed * factor)
        );
    }

    /**
     * Create a new Stats instance with the given deltas added to each stat.
     */
    public Stats withDelta(int hpDelta, int attackDelta, int defenseDelta,
                           int specialAttackDelta, int specialDefenseDelta, int speedDelta) {
        return new Stats(
                maxHP + hpDelta,
                attack + attackDelta,
                defense + defenseDelta,
                specialAttack + specialAttackDelta,
                specialDefense + specialDefenseDelta,
                speed + speedDelta
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return maxHP == stats.maxHP && attack == stats.attack && defense == stats.defense &&
                specialAttack == stats.specialAttack && specialDefense == stats.specialDefense &&
                speed == stats.speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxHP, attack, defense, specialAttack, specialDefense, speed);
    }

    @Override
    public String toString() {
        return "Stats{hp=" + maxHP + ", atk=" + attack + ", def=" + defense +
                ", spa=" + specialAttack + ", spdef=" + specialDefense + ", spd=" + speed + "}";
    }
}
