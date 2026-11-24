package com.monsterra.monster;

import com.monsterra.model.monster.Stats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {

    @Test
    void testStatsCreationAndGetters() {
        Stats stats = new Stats(100, 50, 40, 60, 55, 70);

        assertEquals(100, stats.getMaxHp());
        assertEquals(50, stats.getAttack());
        assertEquals(40, stats.getDefense());
        assertEquals(60, stats.getSpecialAttack());
        assertEquals(55, stats.getSpecialDefense());
        assertEquals(70, stats.getSpeed());
    }

    @Test
    void testNegativeStatsRejection() {
        assertThrows(IllegalArgumentException.class, () -> new Stats(-10, 50, 40, 60, 55, 70));
        assertThrows(IllegalArgumentException.class, () -> new Stats(100, -5, 40, 60, 55, 70));
        assertThrows(IllegalArgumentException.class, () -> new Stats(100, 50, -1, 60, 55, 70));
    }

    @Test
    void testStatsAddition() {
        Stats base = new Stats(100, 50, 40, 30, 30, 50);
        Stats bonus = new Stats(10, 5, 4, 3, 3, 5);

        Stats combined = base.add(bonus);

        assertEquals(110, combined.getMaxHp());
        assertEquals(55, combined.getAttack());
        assertEquals(44, combined.getDefense());
        assertEquals(33, combined.getSpecialAttack());
        assertEquals(33, combined.getSpecialDefense());
        assertEquals(55, combined.getSpeed());
    }

    @Test
    void testStatsEquality() {
        Stats s1 = new Stats(100, 50, 40, 60, 55, 70);
        Stats s2 = new Stats(100, 50, 40, 60, 55, 70);
        Stats s3 = new Stats(120, 50, 40, 60, 55, 70);

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
    }
}
