package com.monsterra.model.monster;

import java.util.*;

/**
 * Provides sample species and skill data for development and testing.
 * This is the shared data source used by Battle, AI, and other modules.
 */
public class SpeciesRegistry {

    private static final Map<String, Species> SPECIES = new HashMap<>();
    private static final Map<String, Skill> SKILLS = new HashMap<>();

    static {
        // --- Skills ---
        SKILLS.put("flame_burst", new Skill("flame_burst", "Flame Burst", Type.FIRE, 80, 95, 10, 0, "burn_chance_10"));
        SKILLS.put("inferno", new Skill("inferno", "Inferno", Type.FIRE, 120, 85, 20, 0, "burn_chance_20"));
        SKILLS.put("ember", new Skill("ember", "Ember", Type.FIRE, 40, 100, 5, 0, ""));
        SKILLS.put("aqua_jet", new Skill("aqua_jet", "Aqua Jet", Type.WATER, 70, 100, 10, 1, ""));
        SKILLS.put("tidal_wave", new Skill("tidal_wave", "Tidal Wave", Type.WATER, 110, 80, 20, 0, ""));
        SKILLS.put("drip", new Skill("drip", "Drip", Type.WATER, 30, 100, 5, 0, ""));
        SKILLS.put("vine_whip", new Skill("vine_whip", "Vine Whip", Type.GRASS, 75, 95, 10, 0, ""));
        SKILLS.put("leaf_storm", new Skill("leaf_storm", "Leaf Storm", Type.GRASS, 120, 85, 20, 0, ""));
        SKILLS.put("thunder_shock", new Skill("thunder_shock", "Thunder Shock", Type.ELECTRIC, 60, 100, 8, 0, "paralyze_chance_10"));
        SKILLS.put("thunderbolt", new Skill("thunderbolt", "Thunderbolt", Type.ELECTRIC, 100, 90, 18, 0, "paralyze_chance_15"));
        SKILLS.put("gust", new Skill("gust", "Gust", Type.AIR, 45, 100, 6, 1, ""));
        SKILLS.put("air_slicer", new Skill("air_slicer", "Air Slicer", Type.AIR, 90, 95, 15, 0, ""));
        SKILLS.put("tackle", new Skill("tackle", "Tackle", Type.EARTH, 40, 100, 5, 0, ""));
        SKILLS.put("earthquake", new Skill("earthquake", "Earthquake", Type.EARTH, 100, 90, 18, 0, ""));
        SKILLS.put("light_beam", new Skill("light_beam", "Light Beam", Type.LIGHT, 85, 95, 15, 0, ""));
        SKILLS.put("dark_pulse", new Skill("dark_pulse", "Dark Pulse", Type.DARK, 85, 95, 15, 0, ""));

        // --- Species ---
        // Flametail (FIRE) -> evolves to Infernoon at level 20
        SPECIES.put("flametail", new Species(
                "flametail", "Flametail",
                new Stats(45, 50, 35, 45, 30, 40),
                List.of(Type.FIRE),
                List.of("ember", "flame_burst", "tackle"),
                List.of(new EvolutionRule.Builder()
                        .targetSpeciesId("infernoon")
                        .requiredLevel(20)
                        .build())
        ));

        // Infernoon (FIRE) — evolved form of Flametail
        SPECIES.put("infernoon", new Species(
                "infernoon", "Infernoon",
                new Stats(65, 75, 55, 70, 50, 60),
                List.of(Type.FIRE),
                List.of("ember", "flame_burst", "inferno", "tackle"),
                List.of()
        ));

        // Stormfang (ELECTRIC + AIR)
        SPECIES.put("stormfang", new Species(
                "stormfang", "Stormfang",
                new Stats(40, 45, 30, 55, 35, 55),
                List.of(Type.ELECTRIC, Type.AIR),
                List.of("thunder_shock", "gust", "tackle"),
                List.of(new EvolutionRule.Builder()
                        .targetSpeciesId("voltwing")
                        .requiredLevel(25)
                        .build())
        ));

        // Voltwing (ELECTRIC + AIR) — evolved form of Stormfang
        SPECIES.put("voltwing", new Species(
                "voltwing", "Voltwing",
                new Stats(60, 65, 50, 80, 55, 80),
                List.of(Type.ELECTRIC, Type.AIR),
                List.of("thunder_shock", "thunderbolt", "gust", "air_slicer"),
                List.of()
        ));

        // Aquashell (WATER)
        SPECIES.put("aquashell", new Species(
                "aquashell", "Aquashell",
                new Stats(55, 35, 55, 40, 50, 30),
                List.of(Type.WATER),
                List.of("drip", "aqua_jet", "tackle"),
                List.of()
        ));

        // Thornback (GRASS)
        SPECIES.put("thornback", new Species(
                "thornback", "Thornback",
                new Stats(50, 45, 50, 40, 45, 35),
                List.of(Type.GRASS),
                List.of("vine_whip", "tackle"),
                List.of()
        ));

        // Lumina (LIGHT)
        SPECIES.put("lumina", new Species(
                "lumina", "Lumina",
                new Stats(45, 40, 40, 60, 50, 50),
                List.of(Type.LIGHT),
                List.of("light_beam", "tackle"),
                List.of()
        ));

        // Shadowclaw (DARK)
        SPECIES.put("shadowclaw", new Species(
                "shadowclaw", "Shadowclaw",
                new Stats(48, 55, 40, 50, 35, 52),
                List.of(Type.DARK),
                List.of("dark_pulse", "tackle"),
                List.of()
        ));
    }

    /**
     * Get a Species by its ID.
     */
    public static Species getSpecies(String id) {
        return SPECIES.get(id);
    }

    /**
     * Get all registered Species.
     */
    public static Map<String, Species> getAllSpecies() {
        return Map.copyOf(SPECIES);
    }

    /**
     * Get a Skill by its ID.
     */
    public static Skill getSkill(String id) {
        return SKILLS.get(id);
    }

    /**
     * Get all registered Skills.
     */
    public static Map<String, Skill> getAllSkills() {
        return Map.copyOf(SKILLS);
    }
}
