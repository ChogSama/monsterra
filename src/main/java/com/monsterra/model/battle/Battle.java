package com.monsterra.model.battle;

import com.monsterra.model.monster.Monster;
import com.monsterra.model.player.Trainer;

import java.util.Objects;

/**
 * Represents the ongoing state and participants of an active combat encounter.
 */
public class Battle {
    private final String id;
    private final BattleType type;
    private final Trainer playerTrainer;
    private final Trainer enemyTrainer;
    private final Monster wildMonster;

    private Monster playerActiveMonster;
    private Monster enemyActiveMonster;
    private BattleState state;
    private int turnNumber;

    public Battle(String id, BattleType type, Trainer playerTrainer, Trainer enemyTrainer, Monster wildMonster) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Battle ID cannot be null or empty.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Battle type cannot be null.");
        }
        if (playerTrainer == null) {
            throw new IllegalArgumentException("Player trainer cannot be null.");
        }

        this.id = id;
        this.type = type;
        this.playerTrainer = playerTrainer;
        this.enemyTrainer = enemyTrainer;
        this.wildMonster = wildMonster;

        this.playerActiveMonster = playerTrainer.getTeam().getActiveMonster();
        if (this.playerActiveMonster == null && playerTrainer.getTeam().hasAliveMonster()) {
            this.playerActiveMonster = playerTrainer.getTeam().getFirstAliveMonster().orElse(null);
        }

        if (enemyTrainer != null) {
            this.enemyActiveMonster = enemyTrainer.getTeam().getActiveMonster();
            if (this.enemyActiveMonster == null && enemyTrainer.getTeam().hasAliveMonster()) {
                this.enemyActiveMonster = enemyTrainer.getTeam().getFirstAliveMonster().orElse(null);
            }
        } else {
            this.enemyActiveMonster = wildMonster;
        }

        this.state = BattleState.PLAYER_TURN;
        this.turnNumber = 1;
    }

    public static Battle createWildBattle(String id, Trainer playerTrainer, Monster wildMonster) {
        return new Battle(id, BattleType.WILD, playerTrainer, null, wildMonster);
    }

    public static Battle createTrainerBattle(String id, BattleType type, Trainer playerTrainer, Trainer enemyTrainer) {
        return new Battle(id, type, playerTrainer, enemyTrainer, null);
    }

    public String getId() {
        return id;
    }

    public BattleType getType() {
        return type;
    }

    public Trainer getPlayerTrainer() {
        return playerTrainer;
    }

    public Trainer getEnemyTrainer() {
        return enemyTrainer;
    }

    public Monster getWildMonster() {
        return wildMonster;
    }

    public Monster getPlayerActiveMonster() {
        return playerActiveMonster;
    }

    public void setPlayerActiveMonster(Monster playerActiveMonster) {
        this.playerActiveMonster = playerActiveMonster;
    }

    public Monster getEnemyActiveMonster() {
        return enemyActiveMonster;
    }

    public void setEnemyActiveMonster(Monster enemyActiveMonster) {
        this.enemyActiveMonster = enemyActiveMonster;
    }

    public BattleState getState() {
        return state;
    }

    public void setState(BattleState state) {
        if (state != null) {
            this.state = state;
        }
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void incrementTurnNumber() {
        this.turnNumber++;
    }

    public boolean isWild() {
        return type == BattleType.WILD;
    }

    public boolean isFinished() {
        return state == BattleState.FINISHED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Battle battle = (Battle) o;
        return Objects.equals(id, battle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Battle{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", turn=" + turnNumber +
                '}';
    }
}
