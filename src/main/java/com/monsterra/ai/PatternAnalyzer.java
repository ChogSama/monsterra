package com.monsterra.ai;

import com.monsterra.model.battle.BattleEvent;

import java.util.List;

/**
 * Interface for analyzing past battle events to model player behavior tendencies.
 */
public interface PatternAnalyzer {

    /**
     * Analyzes historical battle events and constructs a player behavior pattern model.
     *
     * @param history list of past combat events
     * @return recognized player behavior tendencies
     */
    PlayerPattern analyze(List<BattleEvent> history);
}
