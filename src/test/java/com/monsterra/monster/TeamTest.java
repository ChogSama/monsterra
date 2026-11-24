package com.monsterra.monster;

import com.monsterra.model.monster.Monster;
import com.monsterra.model.monster.Species;
import com.monsterra.model.monster.Stats;
import com.monsterra.model.monster.Type;
import com.monsterra.model.player.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    private Species testSpecies;

    @BeforeEach
    void setUp() {
        testSpecies = new Species("sp-01", "MonSpecies", new Stats(100, 50, 50, 50), List.of(Type.WATER));
    }

    @Test
    void testTeamAddAndLimit() {
        Team team = new Team();
        assertTrue(team.isEmpty());
        assertEquals(0, team.size());

        for (int i = 1; i <= 6; i++) {
            Monster m = new Monster("m-" + i, testSpecies, 10);
            assertTrue(team.addMonster(m));
            assertEquals(i, team.size());
        }

        assertTrue(team.isFull());

        // 7th monster must be rejected
        Monster m7 = new Monster("m-7", testSpecies, 10);
        assertFalse(team.addMonster(m7));
        assertEquals(Team.MAX_TEAM_SIZE, team.size());

        // Cannot add duplicate monster
        Monster m1 = team.getMembers().getFirst();
        assertFalse(team.addMonster(m1));
    }

    @Test
    void testActiveMonsterSwitching() {
        Team team = new Team();
        Monster m1 = new Monster("m-1", testSpecies, 10);
        Monster m2 = new Monster("m-2", testSpecies, 10);
        team.addMonster(m1);
        team.addMonster(m2);

        assertEquals(m1, team.getActiveMonster());

        assertTrue(team.switchActiveMonster(1));
        assertEquals(m2, team.getActiveMonster());

        // Faint m1 and attempt switch to fainted
        m1.takeDamage(100);
        assertTrue(m1.isFainted());
        assertFalse(team.switchActiveMonster(0)); // Cannot switch to fainted
        assertEquals(m2, team.getActiveMonster());
    }

    @Test
    void testAliveMonsterChecks() {
        Team team = new Team();
        Monster m1 = new Monster("m-1", testSpecies, 10);
        Monster m2 = new Monster("m-2", testSpecies, 10);
        team.addMonster(m1);
        team.addMonster(m2);

        assertTrue(team.hasAliveMonster());
        assertEquals(2, team.getAliveMonsters().size());

        m1.takeDamage(100);
        assertTrue(team.hasAliveMonster());
        assertEquals(1, team.getAliveMonsters().size());
        assertEquals(m2, team.getFirstAliveMonster().orElse(null));

        m2.takeDamage(100);
        assertFalse(team.hasAliveMonster());
        assertTrue(team.getFirstAliveMonster().isEmpty());
    }

    @Test
    void testRemoveMonster() {
        Team team = new Team();
        Monster m1 = new Monster("m-1", testSpecies, 10);
        Monster m2 = new Monster("m-2", testSpecies, 10);
        team.addMonster(m1);
        team.addMonster(m2);

        assertTrue(team.removeMonster(m1));
        assertEquals(1, team.size());
        assertEquals(m2, team.getActiveMonster());
        assertFalse(team.containsMonster(m1));
    }
}
