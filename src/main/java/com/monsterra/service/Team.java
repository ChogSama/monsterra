package com.monsterra.service;

import com.monsterra.model.monster.Monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the active battle team of Monsters.
 * A Team holds up to 6 Monsters that are currently in the active party.
 */
public class Team {
    public static final int MAX_SIZE = 6;

    private final List<Monster> members;

    public Team() {
        this.members = new ArrayList<>();
    }

    public List<Monster> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public int size() {
        return members.size();
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }

    public boolean isFull() {
        return members.size() >= MAX_SIZE;
    }

    /**
     * Add a Monster to the team.
     * @throws IllegalStateException if the team is full
     * @throws IllegalArgumentException if the Monster is already on the team
     */
    public void add(Monster monster) {
        if (monster == null) {
            throw new IllegalArgumentException("Monster must not be null");
        }
        if (members.contains(monster)) {
            throw new IllegalArgumentException("Monster is already on the team");
        }
        if (isFull()) {
            throw new IllegalStateException("Team is full (max " + MAX_SIZE + " members)");
        }
        members.add(monster);
    }

    /**
     * Remove a Monster from the team.
     * @return true if the Monster was found and removed
     */
    public boolean remove(Monster monster) {
        return members.remove(monster);
    }

    /**
     * Remove a Monster at the given index.
     */
    public Monster removeAt(int index) {
        return members.remove(index);
    }

    /**
     * Get the Monster at the given index.
     */
    public Monster getAt(int index) {
        return members.get(index);
    }

    /**
     * Swap two Monsters in the team.
     */
    public void swap(int index1, int index2) {
        if (index1 < 0 || index1 >= members.size()) {
            throw new IndexOutOfBoundsException("Index1: " + index1);
        }
        if (index2 < 0 || index2 >= members.size()) {
            throw new IndexOutOfBoundsException("Index2: " + index2);
        }
        Collections.swap(members, index1, index2);
    }

    /**
     * Return all battle-ready Monsters (HP > 0).
     */
    public List<Monster> getBattleReady() {
        List<Monster> ready = new ArrayList<>();
        for (Monster m : members) {
            if (m.isBattleReady()) {
                ready.add(m);
            }
        }
        return ready;
    }

    /**
     * Heal all team members to full HP.
     */
    public void healAll() {
        for (Monster m : members) {
            m.healFull();
        }
    }

    /**
     * Clear all status effects from all team members.
     */
    public void clearAllStatusEffects() {
        for (Monster m : members) {
            m.clearStatusEffects();
        }
    }

    @Override
    public String toString() {
        return "Team{size=" + members.size() + "/" + MAX_SIZE + "}";
    }
}
