package com.monsterra.monster;

import com.monsterra.model.monster.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    private Species flametail;
    private Monster monster;

    @BeforeEach
    void setUp() {
        flametail = SpeciesRegistry.getSpecies("flametail");
        monster = new Monster(flametail, 1);
    }

    @Test
    void creation_setsInitialValues() {
        assertEquals(flametail, monster.getSpecies());
        assertEquals(1, monster.getLevel());
        assertEquals(0, monster.getExperience());
        assertEquals(flametail.getBaseStats().getMaxHP(), monster.getCurrentHP());
        assertTrue(monster.isBattleReady());
        assertNotNull(monster.getId());
    }

    @Test
    void nickname_canBeSet() {
        assertNull(monster.getNickname());
        monster.setNickname("Blaze");
        assertEquals("Blaze", monster.getNickname());
    }

    @Test
    void gainExperience_addsExp() {
        monster.gainExperience(50);
        assertEquals(50, monster.getExperience());
    }

    @Test
    void gainExperience_rejectsNegative() {
        assertThrows(IllegalArgumentException.class, () -> monster.gainExperience(-1));
    }

    @Test
    void takeDamage_reducesHP() {
        int maxHP = monster.getStats().getMaxHP();
        int damage = monster.takeDamage(10);
        assertEquals(10, damage);
        assertEquals(maxHP - 10, monster.getCurrentHP());
    }

    @Test
    void takeDamage_clampedToCurrentHP() {
        int maxHP = monster.getStats().getMaxHP();
        int damage = monster.takeDamage(maxHP + 100);
        assertEquals(maxHP, damage);
        assertEquals(0, monster.getCurrentHP());
        assertFalse(monster.isBattleReady());
    }

    @Test
    void takeDamage_rejectsNegative() {
        assertThrows(IllegalArgumentException.class, () -> monster.takeDamage(-1));
    }

    @Test
    void healFull_restoresHP() {
        monster.takeDamage(20);
        monster.healFull();
        assertEquals(monster.getStats().getMaxHP(), monster.getCurrentHP());
    }

    @Test
    void addSkill_addsToSkills() {
        Skill skill = SpeciesRegistry.getSkill("ember");
        monster.addSkill(skill);
        assertEquals(1, monster.getSkills().size());
        assertTrue(monster.getSkills().contains(skill));
    }

    @Test
    void addSkill_maxFourSkills() {
        monster.addSkill(SpeciesRegistry.getSkill("ember"));
        monster.addSkill(SpeciesRegistry.getSkill("flame_burst"));
        monster.addSkill(SpeciesRegistry.getSkill("tackle"));
        monster.addSkill(SpeciesRegistry.getSkill("aqua_jet"));
        assertThrows(IllegalStateException.class, () -> monster.addSkill(SpeciesRegistry.getSkill("inferno")));
    }

    @Test
    void addSkill_noDuplicates() {
        Skill skill = SpeciesRegistry.getSkill("ember");
        monster.addSkill(skill);
        monster.addSkill(skill); // duplicate
        assertEquals(1, monster.getSkills().size());
    }

    @Test
    void statusEffects_canBeAddedAndTicked() {
        StatusEffect burn = new StatusEffect(StatusEffect.Type.BURN, 3, 1);
        monster.addStatusEffect(burn);
        assertEquals(1, monster.getStatusEffects().size());

        monster.tickStatusEffects();
        // Duration decremented to 2, still present
        assertEquals(1, monster.getStatusEffects().size());

        monster.tickStatusEffects();
        monster.tickStatusEffects();
        // Duration reached 0, removed
        assertEquals(0, monster.getStatusEffects().size());
    }

    @Test
    void level_validation() {
        assertThrows(IllegalArgumentException.class, () -> monster.setLevel(0));
        assertThrows(IllegalArgumentException.class, () -> monster.setLevel(-1));
    }

    @Test
    void hp_validation() {
        int maxHP = monster.getStats().getMaxHP();
        assertThrows(IllegalArgumentException.class, () -> monster.setCurrentHP(-1));
        assertThrows(IllegalArgumentException.class, () -> monster.setCurrentHP(maxHP + 1));
    }

    @Test
    void equalsAndHashCode_basedOnId() {
        Monster sameSpecies = new Monster(flametail, 1);
        assertNotEquals(monster, sameSpecies); // different IDs
        assertEquals(monster, monster);
    }
}
