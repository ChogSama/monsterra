package com.monsterra.model.battle;

import java.util.Objects;

/**
 * Unified representation of a battle decision submitted by either the Player or the AI.
 */
public class BattleAction {
    private final ActionType type;
    private final String actorMonsterId;
    private final String targetMonsterId;
    private final String skillId;
    private final String itemId;

    public BattleAction(ActionType type, String actorMonsterId, String targetMonsterId, String skillId, String itemId) {
        if (type == null) {
            throw new IllegalArgumentException("Action type cannot be null.");
        }
        this.type = type;
        this.actorMonsterId = actorMonsterId;
        this.targetMonsterId = targetMonsterId;
        this.skillId = skillId;
        this.itemId = itemId;
    }

    public static BattleAction createSkillAction(String actorMonsterId, String targetMonsterId, String skillId) {
        return new BattleAction(ActionType.SKILL, actorMonsterId, targetMonsterId, skillId, null);
    }

    public static BattleAction createSwitchAction(String actorMonsterId, String targetMonsterId) {
        return new BattleAction(ActionType.SWITCH, actorMonsterId, targetMonsterId, null, null);
    }

    public static BattleAction createItemAction(String actorMonsterId, String targetMonsterId, String itemId) {
        return new BattleAction(ActionType.ITEM, actorMonsterId, targetMonsterId, null, itemId);
    }

    public static BattleAction createRunAction(String actorMonsterId) {
        return new BattleAction(ActionType.RUN, actorMonsterId, null, null, null);
    }

    public ActionType getType() {
        return type;
    }

    public String getActorMonsterId() {
        return actorMonsterId;
    }

    public String getTargetMonsterId() {
        return targetMonsterId;
    }

    public String getSkillId() {
        return skillId;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BattleAction that = (BattleAction) o;
        return type == that.type &&
                Objects.equals(actorMonsterId, that.actorMonsterId) &&
                Objects.equals(targetMonsterId, that.targetMonsterId) &&
                Objects.equals(skillId, that.skillId) &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, actorMonsterId, targetMonsterId, skillId, itemId);
    }

    @Override
    public String toString() {
        return "BattleAction{" +
                "type=" + type +
                ", actor='" + actorMonsterId + '\'' +
                ", target='" + targetMonsterId + '\'' +
                ", skill='" + skillId + '\'' +
                ", item='" + itemId + '\'' +
                '}';
    }
}
