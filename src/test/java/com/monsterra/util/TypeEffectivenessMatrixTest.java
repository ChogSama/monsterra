package com.monsterra.util;

import com.monsterra.model.monster.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TypeEffectivenessMatrixTest {

    @Test
    void testSingleTypeEffectiveness() {
        // Fire advantages and disadvantages
        assertEquals(2.0, TypeEffectivenessMatrix.getMultiplier(Type.FIRE, Type.GRASS));
        assertEquals(0.5, TypeEffectivenessMatrix.getMultiplier(Type.FIRE, Type.WATER));
        assertEquals(0.5, TypeEffectivenessMatrix.getMultiplier(Type.FIRE, Type.FIRE));

        // Water
        assertEquals(2.0, TypeEffectivenessMatrix.getMultiplier(Type.WATER, Type.FIRE));
        assertEquals(0.5, TypeEffectivenessMatrix.getMultiplier(Type.WATER, Type.GRASS));

        // Electric vs Earth (Ground) immunity
        assertEquals(0.0, TypeEffectivenessMatrix.getMultiplier(Type.ELECTRIC, Type.EARTH));
        assertEquals(2.0, TypeEffectivenessMatrix.getMultiplier(Type.ELECTRIC, Type.WATER));

        // Light vs Dark
        assertEquals(2.0, TypeEffectivenessMatrix.getMultiplier(Type.LIGHT, Type.DARK));
        assertEquals(2.0, TypeEffectivenessMatrix.getMultiplier(Type.DARK, Type.LIGHT));

        // Neutral
        assertEquals(1.0, TypeEffectivenessMatrix.getMultiplier(Type.AIR, Type.EARTH));
    }

    @Test
    void testDualTypeEffectiveness() {
        // Fire vs Water/Grass = 0.5 * 2.0 = 1.0
        assertEquals(1.0, TypeEffectivenessMatrix.getMultiplier(Type.FIRE, List.of(Type.WATER, Type.GRASS)));

        // Electric vs Water/Air = 2.0 * 2.0 = 4.0 (Super effective against both)
        assertEquals(4.0, TypeEffectivenessMatrix.getMultiplier(Type.ELECTRIC, List.of(Type.WATER, Type.AIR)));

        // Fire vs Water/Fire = 0.5 * 0.5 = 0.25
        assertEquals(0.25, TypeEffectivenessMatrix.getMultiplier(Type.FIRE, List.of(Type.WATER, Type.FIRE)));
    }

    @Test
    void testNullSafety() {
        assertEquals(1.0, TypeEffectivenessMatrix.getMultiplier(null, Type.FIRE));
        assertEquals(1.0, TypeEffectivenessMatrix.getMultiplier(Type.FIRE, (Type) null));
        assertEquals(1.0, TypeEffectivenessMatrix.getMultiplier(Type.FIRE, (List<Type>) null));
    }
}
