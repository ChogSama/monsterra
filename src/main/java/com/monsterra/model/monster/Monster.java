package com.monsterra.model.monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents an individual Monster instance owned by a Trainer or encountered in the wild.
 */
public class Monster {
    public static final int MAX_SKILLS = 4;

    private final String id;
    private Species species;
    private String nickname;
    private int level;
    private int experience;
    private int currentHp;
    private Stats stats;
    private final List<Skill> skills;
    private StatusEffect statusEffect;

    public Monster(String id, Species species, String nickname, int level, int experience, Stats stats, List<Skill> initialSkills) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Monster ID cannot be null or empty.");
        }
        if (species == null) {
            throw new IllegalArgumentException("Species cannot be null.");
        }
        if (level < 1) {
            throw new IllegalArgumentException("Monster level must be at least 1.");
        }

        this.id = id;
        this.species = species;
        this.nickname = (nickname != null && !nickname.isBlank()) ? nickname : species.getName();
        this.level = level;
        this.experience = Math.max(0, experience);
        this.stats = stats != null ? stats : species.getBaseStats();
        this.currentHp = this.stats.getMaxHp();
        this.skills = new ArrayList<>();
        if (initialSkills != null) {
            for (Skill skill : initialSkills) {
                if (this.skills.size() < MAX_SKILLS && skill != null) {
                    this.skills.add(skill);
                }
            }
        }
        this.statusEffect = StatusEffect.NONE;
    }

    public Monster(String id, Species species, int level) {
        this(id, species, species.getName(), level, 0, species.getBaseStats(), species.getLearnableSkills());
    }

    public String getId() {
        return id;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        if (species != null) {
            this.species = species;
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level >= 1) {
            this.level = level;
        }
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }

    public void addExperience(int amount) {
        if (amount > 0) {
            this.experience += amount;
        }
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = Math.clamp(currentHp, 0, stats.getMaxHp());
    }

    public int getMaxHp() {
        return stats.getMaxHp();
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        if (stats != null) {
            this.stats = stats;
            if (this.currentHp > stats.getMaxHp()) {
                this.currentHp = stats.getMaxHp();
            }
        }
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public boolean learnSkill(Skill skill) {
        if (skill == null || skills.contains(skill)) {
            return false;
        }
        if (skills.size() < MAX_SKILLS) {
            return skills.add(skill);
        }
        return false;
    }

    public boolean replaceSkill(int slotIndex, Skill newSkill) {
        if (slotIndex >= 0 && slotIndex < skills.size() && newSkill != null) {
            skills.set(slotIndex, newSkill);
            return true;
        }
        return false;
    }

    public boolean forgetSkill(String skillId) {
        return skills.removeIf(skill -> skill.getId().equals(skillId));
    }

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    public void setStatusEffect(StatusEffect statusEffect) {
        this.statusEffect = statusEffect != null ? statusEffect : StatusEffect.NONE;
    }

    public boolean isFainted() {
        return currentHp <= 0;
    }

    public boolean canFight() {
        return !isFainted();
    }

    public int takeDamage(int damage) {
        if (damage <= 0) {
            return 0;
        }
        int effectiveDamage = Math.min(currentHp, damage);
        currentHp -= effectiveDamage;
        return effectiveDamage;
    }

    public int heal(int amount) {
        if (amount <= 0 || isFainted()) {
            return 0;
        }
        int initialHp = currentHp;
        currentHp = Math.min(stats.getMaxHp(), currentHp + amount);
        return currentHp - initialHp;
    }

    public void fullRestore() {
        currentHp = stats.getMaxHp();
        statusEffect = StatusEffect.NONE;
        for (Skill skill : skills) {
            skill.restoreAllPp();
        }
    }

    public List<Type> getTypes() {
        return species.getTypes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return Objects.equals(id, monster.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Monster{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", species=" + species.getName() +
                ", level=" + level +
                ", hp=" + currentHp + "/" + stats.getMaxHp() +
                ", status=" + statusEffect +
                '}';
    }
}
