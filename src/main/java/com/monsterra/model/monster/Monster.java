package com.monsterra.model.monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a specific Monster instance (an individual, not a species).
 * Each Monster has its own level, EXP, HP, stats, skills, and status effects.
 */
public class Monster {
    private final String id;
    private String nickname;
    private final Species species;
    private int level;
    private int experience;
    private int currentHP;
    private Stats stats;
    private List<Type> types;
    private List<Skill> skills;
    private final List<StatusEffect> statusEffects;
    private boolean evolved;

    public Monster(Species species, int level) {
        this.id = UUID.randomUUID().toString();
        this.nickname = null;
        this.species = species;
        this.level = level;
        this.experience = 0;
        this.stats = species.getBaseStats();
        this.types = new ArrayList<>(species.getBaseTypes());
        this.skills = new ArrayList<>();
        this.statusEffects = new ArrayList<>();
        this.currentHP = species.getBaseStats().getMaxHP();
        this.evolved = false;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Species getSpecies() {
        return species;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Level must be at least 1");
        }
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Experience must not be negative");
        }
        this.experience = experience;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        if (currentHP < 0 || currentHP > stats.getMaxHP()) {
            throw new IllegalArgumentException("HP must be between 0 and max HP (" + stats.getMaxHP() + ")");
        }
        this.currentHP = currentHP;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        if (stats == null) {
            throw new IllegalArgumentException("Stats must not be null");
        }
        this.stats = stats;
    }

    public List<Type> getTypes() {
        return Collections.unmodifiableList(types);
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public void addSkill(Skill skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill must not be null");
        }
        if (skills.size() >= 4) {
            throw new IllegalStateException("Monster can have at most 4 skills");
        }
        if (!skills.contains(skill)) {
            skills.add(skill);
        }
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    public List<StatusEffect> getStatusEffects() {
        return Collections.unmodifiableList(statusEffects);
    }

    public void addStatusEffect(StatusEffect effect) {
        if (effect == null) {
            throw new IllegalArgumentException("Status effect must not be null");
        }
        statusEffects.add(effect);
    }

    public void clearStatusEffects() {
        statusEffects.clear();
    }

    /**
     * Tick all status effects (decrement duration).
     */
    public void tickStatusEffects() {
        statusEffects.removeIf(effect -> {
            StatusEffect next = effect.tick();
            if (next == null) {
                return true;
            }
            return false;
        });
    }

    public boolean isBattleReady() {
        return currentHP > 0;
    }

    public boolean isEvolved() {
        return evolved;
    }

    public void markEvolved() {
        this.evolved = true;
    }

    /**
     * Add EXP to this Monster. Returns the amount of EXP actually added.
     */
    public int gainExperience(int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("EXP must not be negative");
        }
        this.experience += exp;
        return exp;
    }

    /**
     * Heal the Monster to full HP.
     */
    public void healFull() {
        this.currentHP = stats.getMaxHP();
    }

    /**
     * Damage the Monster by the given amount.
     * Returns the actual damage dealt (clamped to current HP).
     */
    public int takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage must not be negative");
        }
        int actualDamage = Math.min(damage, currentHP);
        this.currentHP -= actualDamage;
        return actualDamage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return id.equals(monster.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String label = nickname != null ? nickname : species.getName();
        return "Monster{" + label + " (id=" + id + "), level=" + level +
                ", hp=" + currentHP + "/" + stats.getMaxHP() + ", exp=" + experience + "}";
    }
}
