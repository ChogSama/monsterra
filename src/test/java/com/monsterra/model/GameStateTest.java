package com.monsterra.model;

import com.monsterra.model.item.Inventory;
import com.monsterra.model.item.Item;
import com.monsterra.model.item.ItemType;
import com.monsterra.model.monster.Monster;
import com.monsterra.model.monster.Species;
import com.monsterra.model.monster.Stats;
import com.monsterra.model.monster.Type;
import com.monsterra.model.player.Trainer;
import com.monsterra.model.world.GameSettings;
import com.monsterra.model.world.WorldProgress;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    @Test
    void testGameStateCompositionAndIntegrity() {
        Species testSpecies = new Species("sp-01", "Mon", new Stats(100, 50, 50, 50), List.of(Type.WATER));
        Monster monster = new Monster("m-01", testSpecies, 5);

        Trainer trainer = new Trainer("t-01", "Hero");
        trainer.getTeam().addMonster(monster);
        trainer.addMonsterToCollection(monster);
        trainer.addMoney(500);
        trainer.addBadge("badge-gym-1");

        Item potion = new Item("item-potion", "Potion", ItemType.POTION, 50);
        Inventory inventory = trainer.getInventory();
        inventory.addItem(potion.getId(), 3);

        WorldProgress worldProgress = new WorldProgress("starter_town");
        worldProgress.unlockArea("route_1");
        worldProgress.recordBossDefeated("boss_shadow_golem");

        GameSettings settings = new GameSettings(0.5, 0.7, "FAST", true);

        GameState gameState = new GameState(trainer, inventory, worldProgress, settings, System.currentTimeMillis());

        assertEquals(trainer, gameState.getTrainer());
        assertEquals("Hero", gameState.getTrainer().getName());
        assertEquals(1, gameState.getTrainer().getTeam().size());
        assertEquals(3, gameState.getInventory().getItemCount("item-potion"));
        assertTrue(gameState.getWorldProgress().isAreaUnlocked("route_1"));
        assertTrue(gameState.getWorldProgress().isBossDefeated("boss_shadow_golem"));
        assertEquals("FAST", gameState.getSettings().getTextSpeed());
        assertTrue(gameState.getSettings().isAutoSaveEnabled());
    }

    @Test
    void testInventoryOperations() {
        Inventory inventory = new Inventory();
        assertTrue(inventory.isEmpty());

        inventory.addItem("potion", 5);
        assertEquals(5, inventory.getItemCount("potion"));
        assertTrue(inventory.hasItem("potion", 3));

        assertTrue(inventory.removeItem("potion", 2));
        assertEquals(3, inventory.getItemCount("potion"));

        assertFalse(inventory.removeItem("potion", 10)); // Exceeds stock
        assertEquals(3, inventory.getItemCount("potion"));

        assertTrue(inventory.removeItem("potion", 3)); // Empties item
        assertEquals(0, inventory.getItemCount("potion"));
        assertTrue(inventory.isEmpty());
    }
}
