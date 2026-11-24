package com.monsterra.ai;

import com.monsterra.model.battle.BattleAction;
import com.monsterra.model.monster.Monster;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Read-only snapshot of observable combat state passed to the AI to make decisions.
 */
public class BattleSnapshot {
    private final Monster playerMonster;
    private final Monster enemyMonster;
    private final List<BattleAction> legalActions;
    private final int currentTurn;

    public BattleSnapshot(Monster playerMonster, Monster enemyMonster, List<BattleAction> legalActions, int currentTurn) {
        this.playerMonster = playerMonster;
        this.enemyMonster = enemyMonster;
        this.legalActions = legalActions != null ? List.copyOf(legalActions) : Collections.emptyList();
        this.currentTurn = Math.max(1, currentTurn);
    }

    public Monster getPlayerMonster() {
        return playerMonster;
    }

    public Monster getEnemyMonster() {
        return enemyMonster;
    }

    public List<BattleAction> getLegalActions() {
        return legalActions;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BattleSnapshot that = (BattleSnapshot) o;
        return currentTurn == that.currentTurn &&
                Objects.equals(playerMonster, that.playerMonster) &&
                Objects.equals(enemyMonster, that.enemyMonster) &&
                Objects.equals(legalActions, that.legalActions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerMonster, enemyMonster, legalActions, currentTurn);
    }

    @Override
    public String toString() {
        return "BattleSnapshot{" +
                "turn=" + currentTurn +
                ", playerMonster=" + (playerMonster != null ? playerMonster.getNickname() : "none") +
                ", enemyMonster=" + (enemyMonster != null ? enemyMonster.getNickname() : "none") +
                ", legalActionCount=" + legalActions.size() +
                '}';
    }
}
