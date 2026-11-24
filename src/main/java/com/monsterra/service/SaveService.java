package com.monsterra.service;

import com.monsterra.model.GameState;

/**
 * Service contract for persistent saving, loading, and recovery of game state.
 */
public interface SaveService {

    /**
     * Persists the given game state snapshot.
     *
     * @param gameState the complete game state snapshot to persist
     */
    void saveGame(GameState gameState);

    /**
     * Restores game state from persistent storage.
     *
     * @param saveId the identifier of the save file or slot
     * @return the restored game state
     */
    GameState loadGame(String saveId);

    /**
     * Checks if any persistent save data exists.
     *
     * @return true if save data exists, false otherwise
     */
    boolean hasSaveData();

    /**
     * Deletes the specified save data slot or profile.
     *
     * @param saveId the identifier of the save to delete
     */
    void deleteSave(String saveId);
}
