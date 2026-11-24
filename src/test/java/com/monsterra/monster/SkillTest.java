package com.monsterra.monster;

import com.monsterra.model.monster.Skill;
import com.monsterra.model.monster.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SkillTest {

    @Test
    void testSkillCreationAndProperties() {
        Skill skill = new Skill("ember", "Ember", Type.FIRE, 40, 100, 25, 0, "A small flame attack.");

        assertEquals("ember", skill.getId());
        assertEquals("Ember", skill.getName());
        assertEquals(Type.FIRE, skill.getType());
        assertEquals(40, skill.getPower());
        assertEquals(100, skill.getAccuracy());
        assertEquals(25, skill.getPp());
        assertEquals(25, skill.getMaxPp());
        assertEquals(0, skill.getPriority());
        assertTrue(skill.isUsable());
    }

    @Test
    void testPpConsumptionAndRestore() {
        Skill skill = new Skill("tackle", "Tackle", Type.EARTH, 35, 95, 2, "Standard physical tackle.");

        assertTrue(skill.consumePp());
        assertEquals(1, skill.getPp());
        assertTrue(skill.isUsable());

        assertTrue(skill.consumePp());
        assertEquals(0, skill.getPp());
        assertFalse(skill.isUsable());
        assertFalse(skill.consumePp()); // Cannot consume when 0

        skill.restorePp(1);
        assertEquals(1, skill.getPp());
        assertTrue(skill.isUsable());

        skill.restoreAllPp();
        assertEquals(2, skill.getPp());
    }

    @Test
    void testValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Skill("", "Name", Type.FIRE, 10, 100, 10, ""));
        assertThrows(IllegalArgumentException.class, () -> new Skill("id", "", Type.FIRE, 10, 100, 10, ""));
        assertThrows(IllegalArgumentException.class, () -> new Skill("id", "Name", null, 10, 100, 10, ""));
        assertThrows(IllegalArgumentException.class, () -> new Skill("id", "Name", Type.FIRE, 10, 150, 10, ""));
        assertThrows(IllegalArgumentException.class, () -> new Skill("id", "Name", Type.FIRE, 10, 100, 0, ""));
    }
}
