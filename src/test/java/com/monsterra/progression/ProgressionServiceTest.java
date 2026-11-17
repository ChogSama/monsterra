package com.monsterra.progression;

import com.monsterra.model.monster.*;
import com.monsterra.service.EvolutionService;
import com.monsterra.service.ProgressionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProgressionServiceTest {

    private ProgressionService service;
    private Species flametail;

    @BeforeEach
    void setUp() {
        service = new ProgressionService();
        flametail = SpeciesRegistry.getSpecies("flametail");
    }

    @Test
    void expToNextLevel_returnsCorrectValue() {
        assertEquals(100, service.expToNextLevel(1));
        assertEquals(200, service.expToNextLevel(2));
        assertEquals(10000, service.expToNextLevel(100));
    }

    @Test
    void expToNextLevel_rejectsInvalidLevel() {
        assertThrows(IllegalArgumentException.class, () -> service.expToNextLevel(0));
        assertThrows(IllegalArgumentException.class, () -> service.expToNextLevel(101));
    }

    @Test
    void totalExpToLevel_level1_isZero() {
        assertEquals(0, service.totalExpToLevel(1));
    }

    @Test
    void totalExpToLevel_level3_isSum() {
        // Level 1->2: 100, Level 2->3: 200
        assertEquals(300, service.totalExpToLevel(3));
    }

    @Test
    void canLevelUp_notEnoughExp() {
        Monster m = new Monster(flametail, 1);
        m.gainExperience(50); // need 100
        assertFalse(service.canLevelUp(m));
    }

    @Test
    void canLevelUp_enoughExp() {
        Monster m = new Monster(flametail, 1);
        m.gainExperience(100);
        assertTrue(service.canLevelUp(m));
    }

    @Test
    void canLevelUp_maxLevel() {
        Monster m = new Monster(flametail, 100);
        m.setExperience(999999);
        assertFalse(service.canLevelUp(m));
    }

    @Test
    void levelUp_increasesLevelAndStats() {
        Monster m = new Monster(flametail, 1);
        m.gainExperience(100);
        int baseHP = m.getStats().getMaxHP();

        assertTrue(service.levelUp(m));
        assertEquals(2, m.getLevel());
        assertEquals(0, m.getExperience());
        assertTrue(m.getStats().getMaxHP() > baseHP, "Stats should grow on level up");
        assertEquals(m.getStats().getMaxHP(), m.getCurrentHP()); // healed on level up
    }

    @Test
    void levelUp_notEnoughExp_returnsFalse() {
        Monster m = new Monster(flametail, 1);
        m.gainExperience(50);
        assertFalse(service.levelUp(m));
        assertEquals(1, m.getLevel());
    }

    @Test
    void processAllLevelUps_multipleLevels() {
        Monster m = new Monster(flametail, 1);
        // Level 1->2: 100, Level 2->3: 200, Level 3->4: 300 = 600 total
        m.gainExperience(600);
        int levels = service.processAllLevelUps(m);
        assertEquals(3, levels);
        assertEquals(4, m.getLevel());
        assertEquals(0, m.getExperience());
    }

    @Test
    void processAllLevelUps_expOverflow() {
        Monster m = new Monster(flametail, 1);
        // Level 1->2: 100, Level 2->3: 200 = 300 total, overflow = 50
        m.gainExperience(350);
        service.processAllLevelUps(m);
        assertEquals(3, m.getLevel());
        assertEquals(50, m.getExperience());
    }

    @Test
    void awardExp_triggersLevelUp() {
        Monster m = new Monster(flametail, 1);
        int levels = service.awardExp(m, 100);
        assertEquals(1, levels);
        assertEquals(2, m.getLevel());
    }

    @Test
    void awardExp_noLevelUp() {
        Monster m = new Monster(flametail, 1);
        int levels = service.awardExp(m, 50);
        assertEquals(0, levels);
        assertEquals(1, m.getLevel());
        assertEquals(50, m.getExperience());
    }

    @Test
    void awardExp_rejectsNegative() {
        Monster m = new Monster(flametail, 1);
        assertThrows(IllegalArgumentException.class, () -> service.awardExp(m, -1));
    }

    @Test
    void awardExp_maxLevel_noOp() {
        Monster m = new Monster(flametail, 100);
        int levels = service.awardExp(m, 1000);
        assertEquals(0, levels);
    }

    @Test
    void calculateExpGained_basedOnLevel() {
        Monster m = new Monster(flametail, 10);
        assertEquals(100, service.calculateExpGained(m));
    }
}
