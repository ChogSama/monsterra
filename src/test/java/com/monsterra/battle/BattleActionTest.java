package com.monsterra.battle;

import com.monsterra.model.battle.ActionType;
import com.monsterra.model.battle.BattleAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BattleActionTest {

    @Test
    void testSkillActionCreation() {
        BattleAction action = BattleAction.createSkillAction("mon-01", "mon-02", "flame_burst");

        assertEquals(ActionType.SKILL, action.getType());
        assertEquals("mon-01", action.getActorMonsterId());
        assertEquals("mon-02", action.getTargetMonsterId());
        assertEquals("flame_burst", action.getSkillId());
        assertNull(action.getItemId());
    }

    @Test
    void testSwitchActionCreation() {
        BattleAction action = BattleAction.createSwitchAction("mon-01", "mon-03");

        assertEquals(ActionType.SWITCH, action.getType());
        assertEquals("mon-01", action.getActorMonsterId());
        assertEquals("mon-03", action.getTargetMonsterId());
        assertNull(action.getSkillId());
        assertNull(action.getItemId());
    }

    @Test
    void testItemActionCreation() {
        BattleAction action = BattleAction.createItemAction("mon-01", "mon-01", "potion_super");

        assertEquals(ActionType.ITEM, action.getType());
        assertEquals("mon-01", action.getActorMonsterId());
        assertEquals("potion_super", action.getItemId());
    }

    @Test
    void testRunActionCreation() {
        BattleAction action = BattleAction.createRunAction("mon-01");

        assertEquals(ActionType.RUN, action.getType());
        assertEquals("mon-01", action.getActorMonsterId());
    }

    @Test
    void testActionEquality() {
        BattleAction a1 = BattleAction.createSkillAction("mon-01", "mon-02", "skill-1");
        BattleAction a2 = BattleAction.createSkillAction("mon-01", "mon-02", "skill-1");
        BattleAction a3 = BattleAction.createSkillAction("mon-01", "mon-02", "skill-2");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, a3);
    }
}
