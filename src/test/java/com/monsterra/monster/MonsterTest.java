package com.monsterra.monster;

import com.monsterra.model.monster.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {

    private Species flametailSpecies;
    private Skill emberSkill;
    private Skill flameBurstSkill;

    @BeforeEach
    void setUp() {
        emberSkill = new Skill("ember", "Ember", Type.FIRE, 40, 100, 20, "Ember attack");
        flameBurstSkill = new Skill("flame_burst", "Flame Burst", Type.FIRE, 70, 95, 15, "Burst of flame");

        Stats baseStats = new Stats(80, 50, 45, 60, 50, 65);
        flametailSpecies = new Species(
                "flametail",
                "Flametail",
                baseStats,
                List.of(Type.FIRE),
                List.of(emberSkill),
                16,
                "pyroclast",
                "A fire-breathing fox creature."
        );
    }

    @Test
    void testMonsterInitialization() {
        Monster monster = new Monster("m-01", flametailSpecies, 5);

        assertEquals("m-01", monster.getId());
        assertEquals("Flametail", monster.getNickname());
        assertEquals(flametailSpecies, monster.getSpecies());
        assertEquals(5, monster.getLevel());
        assertEquals(80, monster.getCurrentHp());
        assertEquals(80, monster.getMaxHp());
        assertFalse(monster.isFainted());
        assertTrue(monster.canFight());
        assertEquals(StatusEffect.NONE, monster.getStatusEffect());
        assertEquals(1, monster.getSkills().size());
        assertEquals(emberSkill, monster.getSkills().getFirst());
    }

    @Test
    void testDamageAndFaint() {
        Monster monster = new Monster("m-01", flametailSpecies, 5);

        int damage = monster.takeDamage(30);
        assertEquals(30, damage);
        assertEquals(50, monster.getCurrentHp());
        assertTrue(monster.canFight());

        monster.takeDamage(100); // Exceeds remaining 50
        assertEquals(0, monster.getCurrentHp());
        assertTrue(monster.isFainted());
        assertFalse(monster.canFight());
    }

    @Test
    void testHealingAndFullRestore() {
        Monster monster = new Monster("m-01", flametailSpecies, 5);
        monster.takeDamage(50);
        assertEquals(30, monster.getCurrentHp());

        int healed = monster.heal(25);
        assertEquals(25, healed);
        assertEquals(55, monster.getCurrentHp());

        monster.setStatusEffect(StatusEffect.BURN);
        monster.fullRestore();

        assertEquals(80, monster.getCurrentHp());
        assertEquals(StatusEffect.NONE, monster.getStatusEffect());
    }

    @Test
    void testSkillManagementAndMaxLimit() {
        Monster monster = new Monster("m-01", flametailSpecies, 5);

        assertTrue(monster.learnSkill(flameBurstSkill));
        assertEquals(2, monster.getSkills().size());

        // Cannot learn duplicate
        assertFalse(monster.learnSkill(flameBurstSkill));

        Skill s3 = new Skill("s3", "Skill 3", Type.FIRE, 20, 100, 10, "");
        Skill s4 = new Skill("s4", "Skill 4", Type.FIRE, 30, 100, 10, "");
        Skill s5 = new Skill("s5", "Skill 5", Type.FIRE, 40, 100, 10, "");

        assertTrue(monster.learnSkill(s3));
        assertTrue(monster.learnSkill(s4));
        assertEquals(Monster.MAX_SKILLS, monster.getSkills().size());

        // Should reject 5th skill
        assertFalse(monster.learnSkill(s5));

        // Forget skill and replace
        assertTrue(monster.forgetSkill("s3"));
        assertEquals(3, monster.getSkills().size());
        assertTrue(monster.learnSkill(s5));
    }

    @Test
    void testSpeciesEvolutionEligibility() {
        assertTrue(flametailSpecies.canEvolveAt(16));
        assertTrue(flametailSpecies.canEvolveAt(20));
        assertFalse(flametailSpecies.canEvolveAt(15));
    }
}
