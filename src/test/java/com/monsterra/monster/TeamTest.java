package com.monsterra.monster;

import com.monsterra.model.monster.*;
import com.monsterra.service.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team team;
    private Species flametail;

    @BeforeEach
    void setUp() {
        team = new Team();
        flametail = SpeciesRegistry.getSpecies("flametail");
    }

    @Test
    void newTeam_isEmpty() {
        assertTrue(team.isEmpty());
        assertEquals(0, team.size());
        assertFalse(team.isFull());
    }

    @Test
    void add_monsterToTeam() {
        Monster m = new Monster(flametail, 1);
        team.add(m);
        assertEquals(1, team.size());
        assertFalse(team.isEmpty());
    }

    @Test
    void add_duplicateThrows() {
        Monster m = new Monster(flametail, 1);
        team.add(m);
        assertThrows(IllegalArgumentException.class, () -> team.add(m));
    }

    @Test
    void add_nullThrows() {
        assertThrows(IllegalArgumentException.class, () -> team.add(null));
    }

    @Test
    void add_fullTeamThrows() {
        for (int i = 0; i < Team.MAX_SIZE; i++) {
            team.add(new Monster(flametail, 1));
        }
        assertTrue(team.isFull());
        assertThrows(IllegalStateException.class, () -> team.add(new Monster(flametail, 1)));
    }

    @Test
    void remove_monsterFromTeam() {
        Monster m = new Monster(flametail, 1);
        team.add(m);
        assertTrue(team.remove(m));
        assertEquals(0, team.size());
    }

    @Test
    void swap_members() {
        Monster m1 = new Monster(flametail, 1);
        Monster m2 = new Monster(SpeciesRegistry.getSpecies("aquashell"), 1);
        team.add(m1);
        team.add(m2);
        team.swap(0, 1);
        assertEquals(m2, team.getAt(0));
        assertEquals(m1, team.getAt(1));
    }

    @Test
    void getBattleReady_onlyAliveMonsters() {
        Monster m1 = new Monster(flametail, 1);
        Monster m2 = new Monster(flametail, 1);
        m2.takeDamage(100); // kill
        team.add(m1);
        team.add(m2);
        assertEquals(1, team.getBattleReady().size());
        assertTrue(team.getBattleReady().contains(m1));
    }

    @Test
    void healAll_restoresAllHP() {
        Monster m1 = new Monster(flametail, 1);
        m1.takeDamage(10);
        team.add(m1);
        team.healAll();
        assertEquals(m1.getStats().getMaxHP(), m1.getCurrentHP());
    }
}
