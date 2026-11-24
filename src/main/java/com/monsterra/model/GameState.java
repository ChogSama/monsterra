package com.monsterra.model;

import com.monsterra.model.item.Inventory;
import com.monsterra.model.player.Trainer;
import com.monsterra.model.world.GameSettings;
import com.monsterra.model.world.WorldProgress;

import java.util.Objects;

/**
 * Root persistent snapshot of the complete player progress and game session.
 */
public class GameState {
    private Trainer trainer;
    private Inventory inventory;
    private WorldProgress worldProgress;
    private GameSettings settings;
    private long saveTimestamp;

    public GameState(Trainer trainer, Inventory inventory, WorldProgress worldProgress, GameSettings settings, long saveTimestamp) {
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer cannot be null in GameState.");
        }
        this.trainer = trainer;
        this.inventory = inventory != null ? inventory : trainer.getInventory();
        this.worldProgress = worldProgress != null ? worldProgress : new WorldProgress("area_starter");
        this.settings = settings != null ? settings : new GameSettings();
        this.saveTimestamp = saveTimestamp > 0 ? saveTimestamp : System.currentTimeMillis();
    }

    public GameState(Trainer trainer, WorldProgress worldProgress) {
        this(trainer, trainer.getInventory(), worldProgress, new GameSettings(), System.currentTimeMillis());
    }

    public GameState(Trainer trainer) {
        this(trainer, new WorldProgress("area_starter"));
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        if (trainer != null) {
            this.trainer = trainer;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        if (inventory != null) {
            this.inventory = inventory;
        }
    }

    public WorldProgress getWorldProgress() {
        return worldProgress;
    }

    public void setWorldProgress(WorldProgress worldProgress) {
        if (worldProgress != null) {
            this.worldProgress = worldProgress;
        }
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void setSettings(GameSettings settings) {
        if (settings != null) {
            this.settings = settings;
        }
    }

    public long getSaveTimestamp() {
        return saveTimestamp;
    }

    public void setSaveTimestamp(long saveTimestamp) {
        this.saveTimestamp = saveTimestamp;
    }

    public void updateSaveTimestamp() {
        this.saveTimestamp = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return Objects.equals(trainer, gameState.trainer) &&
                Objects.equals(worldProgress, gameState.worldProgress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainer, worldProgress);
    }

    @Override
    public String toString() {
        return "GameState{" +
                "trainer=" + trainer.getName() +
                ", currentArea=" + worldProgress.getCurrentAreaId() +
                ", saveTimestamp=" + saveTimestamp +
                '}';
    }
}
