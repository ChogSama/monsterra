package com.monsterra.battle.engine;

import com.monsterra.model.battle.Battle;
import com.monsterra.model.battle.BattleAction;
import com.monsterra.model.battle.BattleResult;

/**
 * Authority interface for executing and resolving combat encounters and turns.
 */
public interface BattleEngine {

    /**
     * Initializes and starts a new battle.
     *
     * @param battle the battle instance to start
     * @return the initialized battle
     */
    Battle startBattle(Battle battle);

    /**
     * Resolves a combat turn given the actions chosen by the player and enemy.
     *
     * @param battle the active battle
     * @param playerAction action submitted by the player
     * @param enemyAction action submitted by the enemy / AI
     * @return result of resolving the turn
     */
    BattleResult resolveTurn(
            Battle battle,
            BattleAction playerAction,
            BattleAction enemyAction
    );

    /**
     * Retrieves the current result or terminal outcome of the battle.
     *
     * @param battle the active battle
     * @return current battle result
     */
    BattleResult getBattleResult(Battle battle);
}
