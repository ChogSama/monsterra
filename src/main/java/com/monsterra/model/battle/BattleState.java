package com.monsterra.model.battle;

/**
 * Lifecycle states of an active Battle.
 */
public enum BattleState {
    PLAYER_TURN,
    ENEMY_TURN,
    RESOLVING,
    FINISHED
}
