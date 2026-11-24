package com.monsterra.ai;

import com.monsterra.model.battle.BattleAction;

/**
 * Strategy interface for selecting an action on behalf of an enemy Monster or Trainer.
 */
public interface BattleAI {

    /**
     * Evaluates the observable battle state and selects a battle action.
     *
     * @param snapshot read-only view of the current battle state
     * @return chosen battle action
     */
    BattleAction chooseAction(BattleSnapshot snapshot);
}
