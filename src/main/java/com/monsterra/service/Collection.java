package com.monsterra.service;

import com.monsterra.model.monster.Monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the player's full collection of Monsters.
 * The Collection holds all Monsters owned by the player, including those on the active Team.
 */
public class Collection {
    public static final int MAX_SIZE = 200;

    private final List<Monster> monsters;

    public Collection() {
        this.monsters = new ArrayList<>();
    }

    public List<Monster> getAll() {
        return Collections.unmodifiableList(monsters);
    }

    public int size() {
        return monsters.size();
    }

    public boolean isEmpty() {
        return monsters.isEmpty();
    }

    public boolean isFull() {
        return monsters.size() >= MAX_SIZE;
    }

    /**
     * Add a Monster to the collection.
     * @throws IllegalStateException if the collection is full
     * @throws IllegalArgumentException if the Monster is already in the collection
     */
    public void add(Monster monster) {
        if (monster == null) {
            throw new IllegalArgumentException("Monster must not be null");
        }
        if (monsters.contains(monster)) {
            throw new IllegalArgumentException("Monster is already in the collection");
        }
        if (isFull()) {
            throw new IllegalStateException("Collection is full (max " + MAX_SIZE + " monsters)");
        }
        monsters.add(monster);
    }

    /**
     * Remove a Monster from the collection.
     * @return true if the Monster was found and removed
     */
    public boolean remove(Monster monster) {
        return monsters.remove(monster);
    }

    /**
     * Check if a Monster is in the collection.
     */
    public boolean contains(Monster monster) {
        return monsters.contains(monster);
    }

    /**
     * Find a Monster by its ID.
     * @return the Monster if found, null otherwise
     */
    public Monster findById(String id) {
        for (Monster m : monsters) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Find all Monsters by nickname.
     */
    public List<Monster> findByNickname(String nickname) {
        List<Monster> result = new ArrayList<>();
        for (Monster m : monsters) {
            if (nickname != null && nickname.equals(m.getNickname())) {
                result.add(m);
            }
        }
        return result;
    }

    /**
     * Find all Monsters of a given species ID.
     */
    public List<Monster> findBySpeciesId(String speciesId) {
        List<Monster> result = new ArrayList<>();
        for (Monster m : monsters) {
            if (speciesId.equals(m.getSpecies().getId())) {
                result.add(m);
            }
        }
        return result;
    }

    /**
     * Return all battle-ready Monsters in the collection.
     */
    public List<Monster> getBattleReady() {
        List<Monster> ready = new ArrayList<>();
        for (Monster m : monsters) {
            if (m.isBattleReady()) {
                ready.add(m);
            }
        }
        return ready;
    }

    /**
     * Heal all Monsters in the collection.
     */
    public void healAll() {
        for (Monster m : monsters) {
            m.healFull();
        }
    }

    @Override
    public String toString() {
        return "Collection{size=" + monsters.size() + "/" + MAX_SIZE + "}";
    }
}
