package com.monsterra.model.player;

import com.monsterra.model.item.Inventory;
import com.monsterra.model.monster.Monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a human Trainer or NPC Trainer who owns Monsters, an active Team, and an Inventory.
 */
public class Trainer {
    private final String id;
    private String name;
    private final Team team;
    private final List<Monster> collection;
    private final Inventory inventory;
    private int money;
    private final List<String> badges;

    public Trainer(String id, String name, Team team, List<Monster> collection, Inventory inventory, int money, List<String> badges) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Trainer ID cannot be null or empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Trainer name cannot be null or empty.");
        }

        this.id = id;
        this.name = name;
        this.team = team != null ? team : new Team();
        this.collection = new ArrayList<>();
        if (collection != null) {
            this.collection.addAll(collection);
        }
        // Ensure team members are also in collection
        for (Monster m : this.team.getMembers()) {
            if (!this.collection.contains(m)) {
                this.collection.add(m);
            }
        }
        this.inventory = inventory != null ? inventory : new Inventory();
        this.money = Math.max(0, money);
        this.badges = badges != null ? new ArrayList<>(badges) : new ArrayList<>();
    }

    public Trainer(String id, String name) {
        this(id, name, new Team(), new ArrayList<>(), new Inventory(), 0, new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
    }

    public Team getTeam() {
        return team;
    }

    public List<Monster> getCollection() {
        return Collections.unmodifiableList(collection);
    }

    public boolean addMonsterToCollection(Monster monster) {
        if (monster == null || collection.contains(monster)) {
            return false;
        }
        return collection.add(monster);
    }

    public boolean removeMonsterFromCollection(Monster monster) {
        if (monster == null) {
            return false;
        }
        team.removeMonster(monster);
        return collection.remove(monster);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = Math.max(0, money);
    }

    public void addMoney(int amount) {
        if (amount > 0) {
            this.money += amount;
        }
    }

    public boolean spendMoney(int amount) {
        if (amount > 0 && this.money >= amount) {
            this.money -= amount;
            return true;
        }
        return false;
    }

    public List<String> getBadges() {
        return Collections.unmodifiableList(badges);
    }

    public boolean addBadge(String badgeId) {
        if (badgeId != null && !badgeId.isBlank() && !badges.contains(badgeId)) {
            return badges.add(badgeId);
        }
        return false;
    }

    public boolean hasBadge(String badgeId) {
        return badgeId != null && badges.contains(badgeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(id, trainer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", teamSize=" + team.size() +
                ", collectionSize=" + collection.size() +
                ", money=" + money +
                ", badges=" + badges.size() +
                '}';
    }
}
