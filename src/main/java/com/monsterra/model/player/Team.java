package com.monsterra.model.player;

import com.monsterra.model.monster.Monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the active battle team of a Trainer (maximum 6 Monsters).
 */
public class Team {
    public static final int MAX_TEAM_SIZE = 6;

    private final List<Monster> members;
    private int activeIndex;

    public Team() {
        this.members = new ArrayList<>();
        this.activeIndex = 0;
    }

    public Team(List<Monster> initialMembers) {
        this();
        if (initialMembers != null) {
            for (Monster m : initialMembers) {
                if (m != null && this.members.size() < MAX_TEAM_SIZE && !this.members.contains(m)) {
                    this.members.add(m);
                }
            }
        }
    }

    public boolean addMonster(Monster monster) {
        if (monster == null || isFull() || containsMonster(monster)) {
            return false;
        }
        return members.add(monster);
    }

    public boolean removeMonster(Monster monster) {
        if (monster == null || !members.contains(monster)) {
            return false;
        }
        int removedIndex = members.indexOf(monster);
        boolean removed = members.remove(monster);
        if (removed && activeIndex >= members.size()) {
            activeIndex = Math.max(0, members.size() - 1);
        }
        return removed;
    }

    public Monster removeMonster(int index) {
        if (index < 0 || index >= members.size()) {
            throw new IndexOutOfBoundsException("Invalid team index: " + index);
        }
        Monster removed = members.remove(index);
        if (activeIndex >= members.size()) {
            activeIndex = Math.max(0, members.size() - 1);
        }
        return removed;
    }

    public boolean switchActiveMonster(int index) {
        if (index < 0 || index >= members.size()) {
            return false;
        }
        Monster target = members.get(index);
        if (target.isFainted()) {
            return false;
        }
        this.activeIndex = index;
        return true;
    }

    public boolean switchActiveMonster(Monster monster) {
        if (monster == null) {
            return false;
        }
        int index = members.indexOf(monster);
        return switchActiveMonster(index);
    }

    public Monster getActiveMonster() {
        if (members.isEmpty()) {
            return null;
        }
        if (activeIndex >= members.size()) {
            activeIndex = 0;
        }
        return members.get(activeIndex);
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public boolean isFull() {
        return members.size() >= MAX_TEAM_SIZE;
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }

    public int size() {
        return members.size();
    }

    public boolean containsMonster(Monster monster) {
        return members.contains(monster);
    }

    public boolean containsMonsterId(String monsterId) {
        if (monsterId == null) return false;
        return members.stream().anyMatch(m -> m.getId().equals(monsterId));
    }

    public Optional<Monster> findMonsterById(String monsterId) {
        if (monsterId == null) return Optional.empty();
        return members.stream().filter(m -> m.getId().equals(monsterId)).findFirst();
    }

    public boolean hasAliveMonster() {
        return members.stream().anyMatch(Monster::canFight);
    }

    public Optional<Monster> getFirstAliveMonster() {
        return members.stream().filter(Monster::canFight).findFirst();
    }

    public List<Monster> getAliveMonsters() {
        return members.stream().filter(Monster::canFight).toList();
    }

    public List<Monster> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public void swapSlots(int index1, int index2) {
        if (index1 >= 0 && index1 < members.size() && index2 >= 0 && index2 < members.size()) {
            Collections.swap(members, index1, index2);
            if (activeIndex == index1) {
                activeIndex = index2;
            } else if (activeIndex == index2) {
                activeIndex = index1;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(members, team.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members);
    }

    @Override
    public String toString() {
        return "Team{" +
                "members=" + members.size() + "/" + MAX_TEAM_SIZE +
                ", active=" + (getActiveMonster() != null ? getActiveMonster().getNickname() : "none") +
                '}';
    }
}
