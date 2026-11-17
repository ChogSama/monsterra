package com.monsterra.progression;

import com.monsterra.model.monster.*;
import com.monsterra.service.EvolutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EvolutionServiceTest {

    private EvolutionService service;
    private Species flametail;
    private Species infernoon;

    @BeforeEach
    void setUp() {
        service = new EvolutionService(SpeciesRegistry.getAllSpecies());
        flametail = SpeciesRegistry.getSpecies("flametail");
        infernoon = SpeciesRegistry.getSpecies("infernoon");
    }

    @Test
    void canEvolve_belowRequiredLevel() {
        Monster m = new Monster(flametail, 10); // needs level 20
        assertFalse(service.canEvolve(m));
    }

    @Test
    void canEvolve_atRequiredLevel() {
        Monster m = new Monster(flametail, 20);
        assertTrue(service.canEvolve(m));
    }

    @Test
    void canEvolve_noEvolutionRules() {
        Monster m = new Monster(infernoon, 50); // no evolution rules
        assertFalse(service.canEvolve(m));
    }

    @Test
    void getEvolutionTarget_returnsCorrectSpecies() {
        Monster m = new Monster(flametail, 20);
        assertEquals(infernoon, service.getEvolutionTarget(m));
    }

    @Test
    void getEvolutionTarget_belowLevel_returnsNull() {
        Monster m = new Monster(flametail, 10);
        assertNull(service.getEvolutionTarget(m));
    }

    @Test
    void evolve_preservesNickname() {
        Monster m = new Monster(flametail, 20);
        m.setNickname("Blaze");
        Monster evolved = service.evolve(m);
        assertEquals("Blaze", evolved.getNickname());
    }

    @Test
    void evolve_preservesExp() {
        Monster m = new Monster(flametail, 20);
        m.gainExperience(50);
        Monster evolved = service.evolve(m);
        assertEquals(50, evolved.getExperience());
    }

    @Test
    void evolve_preservesLevel() {
        Monster m = new Monster(flametail, 20);
        Monster evolved = service.evolve(m);
        assertEquals(20, evolved.getLevel());
    }

    @Test
    void evolve_changesSpecies() {
        Monster m = new Monster(flametail, 20);
        Monster evolved = service.evolve(m);
        assertEquals(infernoon, evolved.getSpecies());
    }

    @Test
    void evolve_preservesSkills() {
        Monster m = new Monster(flametail, 20);
        m.addSkill(SpeciesRegistry.getSkill("ember"));
        m.addSkill(SpeciesRegistry.getSkill("flame_burst"));
        Monster evolved = service.evolve(m);
        assertEquals(2, evolved.getSkills().size());
        assertTrue(evolved.getSkills().contains(SpeciesRegistry.getSkill("ember")));
    }

    @Test
    void evolve_marksEvolved() {
        Monster m = new Monster(flametail, 20);
        Monster evolved = service.evolve(m);
        assertTrue(evolved.isEvolved());
    }

    @Test
    void evolve_healsToFullHP() {
        Monster m = new Monster(flametail, 20);
        m.takeDamage(10);
        Monster evolved = service.evolve(m);
        assertEquals(evolved.getStats().getMaxHP(), evolved.getCurrentHP());
    }

    @Test
    void evolve_throwsWhenCannotEvolve() {
        Monster m = new Monster(flametail, 10);
        assertThrows(IllegalStateException.class, () -> service.evolve(m));
    }

    @Test
    void evolve_noEvolutionRules_throws() {
        Monster m = new Monster(infernoon, 50);
        assertThrows(IllegalStateException.class, () -> service.evolve(m));
    }

    @Test
    void evolve_newId() {
        Monster m = new Monster(flametail, 20);
        Monster evolved = service.evolve(m);
        assertNotEquals(m.getId(), evolved.getId());
    }

    @Test
    void evolve_updatesTypes() {
        Monster m = new Monster(flametail, 20);
        Monster evolved = service.evolve(m);
        assertEquals(infernoon.getBaseTypes(), evolved.getTypes());
    }

    @Test
    void evolve_updatesStats() {
        Monster m = new Monster(flametail, 20);
        Monster evolved = service.evolve(m);
        // Infernoon has higher base stats than Flametail
        assertTrue(evolved.getStats().getMaxHP() > flametail.getBaseStats().getMaxHP());
    }
}
