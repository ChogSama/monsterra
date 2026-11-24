package com.monsterra.progression;

import com.monsterra.model.monster.Monster;
import com.monsterra.model.monster.Species;
import com.monsterra.model.monster.Stats;
import com.monsterra.model.monster.Type;
import com.monsterra.service.EvolutionResult;
import com.monsterra.service.LevelUpResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressionTest {

    @Test
    void testLevelUpResult() {
        Stats prevStats = new Stats(80, 40, 35, 50);
        Stats newStats = new Stats(88, 44, 38, 55);

        LevelUpResult result = new LevelUpResult(5, 6, prevStats, newStats);

        assertEquals(5, result.getPreviousLevel());
        assertEquals(6, result.getNewLevel());
        assertEquals(prevStats, result.getPreviousStats());
        assertEquals(newStats, result.getNewStats());
    }

    @Test
    void testEvolutionResultSuccessAndFailure() {
        Species baseSpecies = new Species("sp-01", "BaseMon", new Stats(100, 50, 50, 50), List.of(Type.FIRE));
        Species evoSpecies = new Species("sp-02", "EvoMon", new Stats(150, 75, 75, 75), List.of(Type.FIRE));

        Monster monster = new Monster("m-01", baseSpecies, 16);
        monster.setSpecies(evoSpecies);

        EvolutionResult success = EvolutionResult.success(monster, "BaseMon evolved into EvoMon!");
        assertTrue(success.isSuccess());
        assertEquals(monster, success.getEvolvedMonster());
        assertEquals("BaseMon evolved into EvoMon!", success.getMessage());

        EvolutionResult failure = EvolutionResult.failure("Monster has not reached the required level.");
        assertFalse(failure.isSuccess());
        assertNull(failure.getEvolvedMonster());
        assertEquals("Monster has not reached the required level.", failure.getMessage());
    }
}
