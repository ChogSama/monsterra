package com.monsterra.model.battle;

import java.util.Objects;

/**
 * Represents a domain event generated during turn execution in Battle.
 */
public class BattleEvent {
    public static final String TYPE_DAMAGE_DEALT = "DAMAGE_DEALT";
    public static final String TYPE_MONSTER_DEFEATED = "MONSTER_DEFEATED";
    public static final String TYPE_SKILL_USED = "SKILL_USED";
    public static final String TYPE_MONSTER_SWITCHED = "MONSTER_SWITCHED";
    public static final String TYPE_STATUS_APPLIED = "STATUS_APPLIED";
    public static final String TYPE_STATUS_REMOVED = "STATUS_REMOVED";
    public static final String TYPE_ITEM_USED = "ITEM_USED";
    public static final String TYPE_RUN_ATTEMPTED = "RUN_ATTEMPTED";
    public static final String TYPE_BATTLE_WON = "BATTLE_WON";
    public static final String TYPE_BATTLE_LOST = "BATTLE_LOST";

    private final String eventType;
    private final String sourceMonsterId;
    private final String targetMonsterId;
    private final String description;

    public BattleEvent(String eventType, String sourceMonsterId, String targetMonsterId, String description) {
        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException("Event type cannot be null or empty.");
        }
        this.eventType = eventType;
        this.sourceMonsterId = sourceMonsterId;
        this.targetMonsterId = targetMonsterId;
        this.description = description != null ? description : "";
    }

    public String getEventType() {
        return eventType;
    }

    public String getSourceMonsterId() {
        return sourceMonsterId;
    }

    public String getTargetMonsterId() {
        return targetMonsterId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BattleEvent that = (BattleEvent) o;
        return Objects.equals(eventType, that.eventType) &&
                Objects.equals(sourceMonsterId, that.sourceMonsterId) &&
                Objects.equals(targetMonsterId, that.targetMonsterId) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, sourceMonsterId, targetMonsterId, description);
    }

    @Override
    public String toString() {
        return "BattleEvent{" +
                "type='" + eventType + '\'' +
                ", source='" + sourceMonsterId + '\'' +
                ", target='" + targetMonsterId + '\'' +
                ", desc='" + description + '\'' +
                '}';
    }
}
