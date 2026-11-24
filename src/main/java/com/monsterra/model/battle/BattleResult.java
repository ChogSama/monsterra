package com.monsterra.model.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Encapsulates the state and events resulting from resolving a turn or ending a battle.
 */
public class BattleResult {
    private final BattleState state;
    private final BattleOutcome outcome;
    private final List<BattleEvent> events;

    public BattleResult(BattleState state, BattleOutcome outcome, List<BattleEvent> events) {
        this.state = state != null ? state : BattleState.FINISHED;
        this.outcome = outcome != null ? outcome : BattleOutcome.ONGOING;
        this.events = events != null ? List.copyOf(events) : Collections.emptyList();
    }

    public BattleResult(BattleState state, BattleOutcome outcome) {
        this(state, outcome, new ArrayList<>());
    }

    public BattleState getState() {
        return state;
    }

    public BattleOutcome getOutcome() {
        return outcome;
    }

    public List<BattleEvent> getEvents() {
        return events;
    }

    public boolean isFinished() {
        return state == BattleState.FINISHED || outcome != BattleOutcome.ONGOING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BattleResult that = (BattleResult) o;
        return state == that.state &&
                outcome == that.outcome &&
                Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, outcome, events);
    }

    @Override
    public String toString() {
        return "BattleResult{" +
                "state=" + state +
                ", outcome=" + outcome +
                ", eventCount=" + events.size() +
                '}';
    }
}
