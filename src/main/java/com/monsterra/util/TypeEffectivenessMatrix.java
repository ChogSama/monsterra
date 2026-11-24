package com.monsterra.util;

import com.monsterra.model.monster.Type;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Utility for evaluating elemental type effectiveness and damage multipliers.
 */
public final class TypeEffectivenessMatrix {

    private static final Map<Type, Map<Type, Double>> MATRIX = new EnumMap<>(Type.class);

    static {
        for (Type t : Type.values()) {
            MATRIX.put(t, new EnumMap<>(Type.class));
        }

        // Fire
        set(Type.FIRE, Type.GRASS, 2.0);
        set(Type.FIRE, Type.WATER, 0.5);
        set(Type.FIRE, Type.FIRE, 0.5);

        // Water
        set(Type.WATER, Type.FIRE, 2.0);
        set(Type.WATER, Type.EARTH, 2.0);
        set(Type.WATER, Type.GRASS, 0.5);
        set(Type.WATER, Type.WATER, 0.5);

        // Grass
        set(Type.GRASS, Type.WATER, 2.0);
        set(Type.GRASS, Type.EARTH, 2.0);
        set(Type.GRASS, Type.FIRE, 0.5);
        set(Type.GRASS, Type.GRASS, 0.5);

        // Electric
        set(Type.ELECTRIC, Type.WATER, 2.0);
        set(Type.ELECTRIC, Type.AIR, 2.0);
        set(Type.ELECTRIC, Type.EARTH, 0.0); // Ground/Earth immune to Electric
        set(Type.ELECTRIC, Type.ELECTRIC, 0.5);

        // Earth
        set(Type.EARTH, Type.ELECTRIC, 2.0);
        set(Type.EARTH, Type.FIRE, 2.0);
        set(Type.EARTH, Type.AIR, 0.0); // Air immune to Earth
        set(Type.EARTH, Type.GRASS, 0.5);

        // Air
        set(Type.AIR, Type.GRASS, 2.0);
        set(Type.AIR, Type.ELECTRIC, 0.5);
        set(Type.AIR, Type.EARTH, 1.0);

        // Light
        set(Type.LIGHT, Type.DARK, 2.0);
        set(Type.LIGHT, Type.LIGHT, 0.5);

        // Dark
        set(Type.DARK, Type.LIGHT, 2.0);
        set(Type.DARK, Type.DARK, 0.5);
    }

    private TypeEffectivenessMatrix() {
        // Prevent instantiation of utility class
    }

    private static void set(Type attacker, Type defender, double multiplier) {
        MATRIX.get(attacker).put(defender, multiplier);
    }

    /**
     * Returns the type effectiveness multiplier for a single attacking and defending type.
     *
     * @param attacking attacking elemental type
     * @param defending defending elemental type
     * @return damage multiplier (e.g., 2.0, 1.0, 0.5, 0.0)
     */
    public static double getMultiplier(Type attacking, Type defending) {
        if (attacking == null || defending == null) {
            return 1.0;
        }
        return MATRIX.get(attacking).getOrDefault(defending, 1.0);
    }

    /**
     * Calculates the cumulative damage multiplier when defending against a list of types (dual-type monsters).
     *
     * @param attacking attacking elemental type
     * @param defendingTypes list of defending elemental types
     * @return compounded multiplier
     */
    public static double getMultiplier(Type attacking, List<Type> defendingTypes) {
        if (attacking == null || defendingTypes == null || defendingTypes.isEmpty()) {
            return 1.0;
        }
        double multiplier = 1.0;
        for (Type defending : defendingTypes) {
            multiplier *= getMultiplier(attacking, defending);
        }
        return multiplier;
    }
}
