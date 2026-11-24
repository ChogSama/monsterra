package com.monsterra.battle.engine;

import com.monsterra.model.battle.Battle;
import com.monsterra.model.battle.BattleAction;

/**
 * Interface for verifying the validity and legality of proposed battle actions.
 */
public interface BattleActionValidator {

    /**
     * Checks if a battle action is valid according to current battle state and gameplay rules.
     *
     * @param battle the active battle
     * @param action the proposed action
     * @return true if the action is legal, false otherwise
     */
    boolean isValid(Battle battle, BattleAction action);
}
