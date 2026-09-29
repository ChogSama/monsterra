# GAMEPLAY RULES & PROGRESSION

> Owner: A — Game & Gameplay Lead

## 1. Purpose

Converts the Gameplay State Machine into explicit,
testable gameplay rules, progression rules, persistence rules,
and cross-team contracts.

The specification must answer:

- What can the player do?
- When can the player do it?
- What conditions are required?
- What happens when an action succeeds?
- What happens when an action fails?
- How does the player progress?
- How do Monsters progress?
- How are Gyms and Regions unlocked?
- What happens after Victory or Defeat?
- Which gameplay state must be persisted?

---

## 2. Rule Definition Standard

Every gameplay rule should define:

| Field | Description |
|---|---|
| Rule ID | Unique identifier |
| Trigger | Event that activates the rule |
| Preconditions | Conditions required |
| Action | What the game does |
| Result | State/data changes |
| Failure / Edge Cases | Exceptional behavior |

Example:

~~~text
Rule ID:
    CAP-001

Trigger:
    Player uses Capture Item during Wild Battle

Preconditions:
    - Battle type = WILD
    - Enemy is capturable
    - Player owns Capture Item

Action:
    Calculate capture probability
    Resolve capture attempt

Success:
    - Monster added to Collection
    - Capture Item quantity decreases
    - Battle ends

Failure:
    - Capture Item quantity decreases
    - Battle continues
~~~

---

# 3. Rule Categories

## 3.1 Core Gameplay Rules

- Movement
- Encounter
- Battle entry
- Battle flow
- Capture
- Victory
- Defeat
- Team
- Items
- NPC interaction

## 3.2 Progression Rules

- EXP
- Level Up
- Skill unlock
- Evolution
- Gym progression
- Badge progression
- Region unlock
- Boss progression
- Game completion

## 3.3 Persistence Rules

- Save
- Load
- Auto Save
- Persistent GameState
- Temporary gameplay state

---

# 4. Rule ID Convention

~~~text
MOVE-xxx    Player Movement
ENC-xxx     Encounter
BAT-xxx     Battle
CAP-xxx     Capture
EXP-xxx     Experience
LVL-xxx     Level
EVO-xxx     Evolution
TEAM-xxx    Team
GYM-xxx     Gym
PROG-xxx    General Progression
REG-xxx     Region
ITEM-xxx    Item
NPC-xxx     NPC
SAVE-xxx    Save / Persistence
~~~

Rule IDs must remain stable once referenced by implementation or tests.

---

# 5. Movement Rules

## MOVE-001 — Player Movement

**Trigger**

Player submits a movement input.

**Preconditions**

- Game State = `EXPLORING`
- Input represents a valid movement direction

**Action**

1. Calculate target tile.
2. Check whether target tile is walkable.

**Result**

If target tile is walkable:

~~~text
Move Player to target tile.
~~~

If target tile is not walkable:

~~~text
Reject movement.
~~~

---

## MOVE-002 — Walkable Tile

| Tile | Walkable | Gameplay Effect |
|---|---:|---|
| Grass | Yes | Can trigger Encounter |
| Road | Yes | No Wild Encounter by default |
| Water | No / Conditional | Requires future ability |
| Mountain | No | Blocks movement |
| NPC | Conditional | Interaction |
| Gym | Yes | Allows Gym entry |
| Item | Yes | Allows Item collection |
| Portal | Yes | Area transition |

The exact walkability of a tile is determined by Map/World data.

---

## MOVE-003 — Area Transition

**Trigger**

Player enters a Portal / Area Transition tile.

**Action**

~~~text
Load target Area.
Update Player position.
Apply target Area rules.
~~~

**Result**

~~~text
Current Area = Target Area
~~~

---

# 6. Encounter Rules

## ENC-001 — Wild Encounter Check

**Trigger**

Player completes movement inside a Wild Zone.

**Preconditions**

- Current Area supports Wild Encounters.
- Movement was successful.
- Player is not in a blocked gameplay state.

**Action**

~~~text
Roll encounter probability.
~~~

**Result**

If roll succeeds:

~~~text
Generate Wild Monster.
Start Wild Battle.
~~~

If roll fails:

~~~text
Continue Exploration.
~~~

---

## ENC-002 — Encounter Rate

Encounter rate is defined by Area data.

Example:

~~~text
Forest:

encounterRate = 15%
~~~

The value must not be hard-coded into UI or Battle logic.

---

## ENC-003 — Encounter Table

Example:

~~~text
Forest
    Encounter Rate: 15%

    Leafling      40%
    Buglet        30%
    Emberling     20%
    Stormkit      10%
~~~

Monster data is owned by Monster & Progression Lead.

Area encounter configuration is owned by Game & Gameplay Lead.

---

# 7. Battle Entry Rules

## BAT-001 — Start Battle

**Trigger**

A valid Battle encounter is created.

**Battle Types**

~~~text
WILD
TRAINER
GYM
BOSS
~~~

**Action**

~~~text
Create Battle State.
Initialize Player side.
Initialize Enemy side.
Select initial Player Monster.
Set Battle State = START.
~~~

**Result**

~~~text
Game State = BATTLE
Battle State = START
~~~

---

## BAT-002 — Player Monster Selection

For Wild Battle:

~~~text
Use configured active Monster by default.
~~~

For Trainer/Gym/Boss Battle:

~~~text
Use the first valid Monster in the Player Team,
unless another selection rule is explicitly defined.
~~~

Game & Gameplay Lead owns the gameplay behavior.

Battle System Lead owns battle implementation.

---

## BAT-003 — Battle Availability

| Action | Wild | Trainer | Gym | Boss |
|---|---:|---:|---:|---:|
| Attack | Yes | Yes | Yes | Yes |
| Switch Monster | Yes | Yes | Yes | Yes |
| Use Item | Yes | Yes | Yes | Configurable |
| Capture | Yes | No | No | No |
| Run | Yes | No | No | Configurable |

---

# 8. Capture Rules

## CAP-001 — Capture Eligibility

A Monster can be captured only when:

~~~text
Battle Type = WILD
~~~

The following are not capturable by default:

- Trainer Monsters
- Gym Monsters
- Boss Monsters

---

## CAP-002 — Capture Attempt

**Trigger**

Player uses a Capture Item.

**Preconditions**

- Battle Type = `WILD`
- Enemy is capturable
- Player owns Capture Item

**Action**

~~~text
Consume one Capture Item.
Calculate Capture Chance.
Resolve capture attempt.
~~~

**Success**

~~~text
Add Monster to Collection.
End Battle.
Apply capture result.
~~~

**Failure**

~~~text
Consume Capture Item.
Battle continues.
~~~

---

## CAP-003 — Capture Probability

Prototype formula:

~~~text
Capture Chance =
    Base Rate
    × HP Modifier
    × Status Modifier
    × Item Modifier
~~~

Exact coefficients are configuration/data and may be tuned during balancing.

---

## CAP-004 — Collection Full

Default prototype behavior:

~~~text
If Active Team is full:
    Send captured Monster to Storage if Storage is available.

Otherwise:
    Reject capture.
~~~

---

# 9. Victory Rules

## BAT-004 — Battle Victory

**Trigger**

All opposing Monsters are defeated.

**Action**

~~~text
End Battle.
Calculate rewards.
Grant EXP.
Grant Money when applicable.
Grant Item rewards when applicable.
Check Level Up.
Check Evolution.
Update Progress.
Trigger Auto Save.
Return to Exploration or Progression State.
~~~

---

## BAT-005 — Reward Types

| Battle Type | EXP | Money | Item |
|---|---:|---:|---:|
| Wild | Yes | No | Chance |
| Trainer | Yes | Yes | Chance |
| Gym | Yes | Yes | Guaranteed / Configured |
| Boss | Yes | Yes | Special / Configured |

Exact reward values are data/configuration.

---

# 10. Defeat Rules

## BAT-006 — Wild Battle Defeat

When all Player Monsters are defeated:

~~~text
Player Defeat
    ↓
Exit Battle
    ↓
Return to previous safe area / configured recovery location
    ↓
Restore Player Team according to recovery rules
~~~

---

## BAT-007 — Gym Defeat

~~~text
No Badge is awarded.
Gym remains available.
Player exits Gym Battle.
Player can retry later.
~~~

---

## BAT-008 — Boss Defeat

~~~text
Boss remains undefeated.
Progress is not awarded.
Player can retry.
~~~

---

# 11. Experience Rules

## EXP-001 — Grant Experience

Trigger:

~~~text
Battle Victory
~~~

Action:

~~~text
Calculate EXP reward.
Grant EXP to eligible Player Monsters.
~~~

EXP reward may depend on:

- Battle Type
- Enemy Monster
- Enemy Level
- Balancing configuration

---

## EXP-002 — EXP Overflow

If gained EXP exceeds the threshold:

~~~text
Apply Level Up.
Carry remaining EXP forward.
Continue checking for additional Level Ups if supported.
~~~

---

# 12. Level Rules

## LVL-001 — Level Up

Trigger:

~~~text
Monster EXP >= Level Requirement
~~~

Action:

~~~text
Increase Monster Level.
Update Monster Stats.
Check Skill Unlock.
Check Evolution.
~~~

---

## LVL-002 — Level Up State

Level Up may trigger:

- Stat increase
- New Skill
- Evolution availability

The exact stat-growth formula is owned by Monster & Progression Lead.

---

# 13. Evolution Rules

## EVO-001 — Evolution Check

Trigger:

- Monster Level Up
- Evolution Item used
- Another configured Evolution trigger

Action:

~~~text
Check Evolution Requirements.
~~~

Result:

~~~text
If requirements are not satisfied:
    Continue normally.

If requirements are satisfied:
    Evolution becomes available.
~~~

---

## EVO-002 — Optional Evolution

Default:

~~~text
Evolution = Optional
~~~

Flow:

~~~text
Show Evolution Preview
    ↓
Player Confirm?
    ├── YES → Evolve
    └── NO  → Cancel
~~~

---

## EVO-003 — Evolution Result

On confirmation:

~~~text
Replace / transform Monster form.
Update Monster data.
Preserve required persistent properties.
Apply Evolution stats.
Update available Skills if configured.
~~~

Evolution data is owned by Monster & Progression Lead.

Evolution trigger and gameplay behavior are owned by Game & Gameplay Lead.

---

# 14. Team Rules

## TEAM-001 — Maximum Team Size

~~~text
Maximum Active Team Size = 6
~~~

---

## TEAM-002 — Captured Monster Placement

If:

~~~text
Active Team < 6
~~~

then:

~~~text
Add captured Monster to Active Team.
~~~

If:

~~~text
Active Team = 6
~~~

then:

~~~text
Send captured Monster to Collection / Storage.
~~~

---

## TEAM-003 — Battle Eligibility

A Monster can participate in Battle only if:

~~~text
Monster belongs to Active Team
AND
Monster is not currently unavailable.
~~~

---

# 15. Gym Progression Rules

## GYM-001 — Gym Availability

Example:

~~~text
Gym 1:
    Requirement = Tutorial Completed

Gym 2:
    Requirement = Badge 1

Gym 3:
    Requirement = Badge 2

Gym 4:
    Requirement = Badge 3
~~~

---

## GYM-002 — Gym Victory

Trigger:

Player defeats Gym Leader.

Action:

~~~text
Award Badge.
Mark Gym as Defeated.
Evaluate Region Unlock.
Grant configured rewards.
Save Progress.
~~~

---

## GYM-003 — Badge

Badges are persistent progression state.

Example:

~~~text
badges = [
    BADGE_01,
    BADGE_02
]
~~~

Badges must not be inferred solely from Battle history.

---

# 16. Region Progression Rules

## REG-001 — Region Unlock

A Region can be unlocked when its configured requirement is satisfied.

Example:

~~~text
Region 1
    ↓
Gym 1
    ↓
Badge 1
    ↓
Region 2
~~~

---

## REG-002 — Region State

Each Region has an explicit state:

~~~text
LOCKED
UNLOCKED
COMPLETED
~~~

The game must not rely only on Player position to determine Region progression.

---

# 17. Boss Progression

## PROG-001 — Boss Unlock

Boss availability is determined by configured progression requirements.

Example:

~~~text
Boss Requirement:
    Badge 4
    AND
    Region 4 unlocked
~~~

---

## PROG-002 — Boss Victory

Trigger:

Player defeats Boss.

Action:

~~~text
Mark Boss as defeated.
Grant configured rewards.
Evaluate Game Completion.
Save Progress.
~~~

---

# 18. NPC Rules

## NPC-001 — NPC Types

~~~text
GUIDE
TRAINER
SHOP
GYM_LEADER
~~~

---

## NPC-002 — NPC Interaction

~~~text
GUIDE
    → Dialogue

TRAINER
    → Trainer Battle

SHOP
    → Shop UI

GYM_LEADER
    → Gym Battle
~~~

---

# 19. Item Rules

## ITEM-001 — Item Categories

~~~text
CaptureItem
HealingItem
EvolutionItem
KeyItem
~~~

---

## ITEM-002 — Healing Item

Preconditions:

- Player owns Item.
- Target Monster is valid.
- Monster can receive healing.

Action:

~~~text
Restore configured HP amount.
Decrease Item quantity.
~~~

---

## ITEM-003 — Evolution Item

Preconditions:

- Player owns Item.
- Target Monster can evolve using this Item.

Action:

~~~text
Consume Item.
Start Evolution flow.
~~~

---

## ITEM-004 — Key Item

Key Items unlock configured gameplay content.

Examples:

- Gym Key
- Quest Item
- Area Unlock Item
- Story Item

---

# 20. Inventory Rules

Inventory stores:

~~~text
Item ID
Quantity
~~~

Example:

~~~text
Potion = 5
Capture Orb = 12
Fire Stone = 1
~~~

Quantity must never become negative.

---

# 21. Save Rules

## SAVE-001 — Persistent Player State

Must save:

~~~text
Player
    - Name
    - Position
    - Current Area
    - Money
~~~

---

## SAVE-002 — Persistent Monster State

Must save:

~~~text
Collection
Team
Monster Level
EXP
HP
Skills
Evolution
Relevant Monster Status
~~~

---

## SAVE-003 — Persistent Inventory

Must save:

~~~text
Item IDs
Item Quantities
~~~

---

## SAVE-004 — Persistent Progression

Must save:

~~~text
Badges
Defeated Gyms
Unlocked Regions
Defeated Bosses
Story / Progression Flags
~~~

---

## SAVE-005 — Settings

Must save when applicable:

~~~text
Volume
Difficulty
Auto Save
Other persistent player settings
~~~

---

## SAVE-006 — Temporary State

Do not persist by default:

~~~text
Current Battle Turn
Temporary AI Decision
Temporary Encounter Roll
Transient UI State
Temporary Animation State
~~~

Saving during Battle is a separate feature and must be explicitly specified
if required.

---

# 22. Auto Save Rules

Auto Save should occur after important progression events.

Default triggers:

- Gym Victory
- Boss Victory
- Region Unlock
- Evolution
- Capture
- Important Item Acquisition
- Major Story Progression

Auto Save must not interrupt active gameplay unnecessarily.

---

# 23. Game Completion

## PROG-003 — Game Completion

Trigger:

~~~text
Final Boss defeated
AND
all required final progression conditions are satisfied.
~~~

Action:

~~~text
Set Game Completion State = COMPLETE.
Save Progress.
Transition to Game Complete state.
~~~

---

# 24. Core Gameplay State Flow

~~~text
MAIN MENU
    │
    ├── NEW GAME
    │
    └── CONTINUE
            │
            ▼
        TUTORIAL
            │
            ▼
        EXPLORING
            │
            ├── NPC
            │    ├── Guide
            │    ├── Trainer
            │    ├── Shop
            │    └── Gym Leader
            │
            ├── ITEM
            │
            ├── AREA TRANSITION
            │
            └── WILD ZONE
                    │
                    ▼
              ENCOUNTER CHECK
                    │
               ┌────┴────┐
               │         │
              NO        YES
               │         │
               │       BATTLE
               │         │
               │    ┌────┴────┐
               │    │         │
               │  VICTORY   DEFEAT
               │    │         │
               │    ▼         ▼
               │   EXP      Recovery
               │    │
               │    ▼
               │ LEVEL UP
               │    │
               │    ▼
               │ EVOLUTION?
               │
               └───────┬────────
                       ▼
                   EXPLORING
                       │
                       ▼
                  GYM AVAILABLE
                       │
                       ▼
                   GYM BATTLE
                       │
                  ┌────┴────┐
                  │         │
                 WIN       LOSE
                  │         │
                  ▼         ▼
                BADGE      RETRY
                  │
                  ▼
             REGION UNLOCK
                  │
                  ▼
              EXPLORING
                  │
                  ▼
               FINAL BOSS
                  │
                  ▼
             GAME COMPLETE
~~~

---

# 25. Gameplay Rule Ownership

| Gameplay Rule | Owner | Collaborator |
|---|---|---|
| Player Movement | Game & Gameplay Lead | UI/Architecture Lead |
| Map Progression | Game & Gameplay Lead | UI/Architecture Lead |
| Encounter | Game & Gameplay Lead | Monster & Progression Lead |
| Capture | Game & Gameplay Lead | Monster & Progression Lead / Battle System Lead |
| Battle Flow | Game & Gameplay Lead | Battle System Lead |
| Damage | Battle System Lead | Game & Gameplay Lead |
| Skill Mechanics | Battle System Lead | Monster & Progression Lead |
| Type Effectiveness | Battle System Lead | Monster & Progression Lead |
| Monster Stats | Monster & Progression Lead | Game & Gameplay Lead |
| EXP Calculation | Monster & Progression Lead | Game & Gameplay Lead |
| Evolution Data | Monster & Progression Lead | Game & Gameplay Lead |
| Evolution Trigger | Game & Gameplay Lead | Monster & Progression Lead |
| Gym Progression | Game & Gameplay Lead | Battle System Lead / Monster & Progression Lead |
| Boss Flow | Game & Gameplay Lead | Battle System Lead / AI & Data Lead |
| AI Decision | AI & Data Lead | Battle System Lead |
| Save State | UI/Architecture Lead | Game & Gameplay Lead |
| UI Flow | UI/Architecture Lead | Game & Gameplay Lead |

> Game & Gameplay Lead owns gameplay rules and gameplay intent.
> Game & Gameplay Lead does not need to implement every gameplay system.

Leaders implement their respective modules according to Game & Gameplay Lead's contracts.

---

# 26. Cross-Team Gameplay Contracts

## 26.1 Game & Gameplay Lead → Battle System Lead: Battle

Battle System Lead receives:

~~~text
Battle Type
Player Team
Enemy Team
Battle Rules
Available Actions
Victory Condition
Defeat Condition
Reward Trigger
Capture Availability
Run Availability
~~~

Battle System Lead exposes:

~~~text
Battle Start
Battle Action
Battle Result
Victory Event
Defeat Event
~~~

---

## 26.2 Game & Gameplay Lead → Monster & Progression Lead: Monster

Monster & Progression Lead receives:

~~~text
Level Rules
EXP Rules
Evolution Trigger
Team Size
Collection Rules
~~~

Monster & Progression Lead exposes:

~~~text
Monster State
EXP Update
Level Up Event
Evolution Availability
Evolution Result
~~~

---

## 26.3 Game & Gameplay Lead → AI & Data Lead: AI

AI & Data Lead receives:

~~~text
Battle Context
Enemy State
Player State
Available Actions
Difficulty Configuration
Boss Behavior Configuration
~~~

AI & Data Lead returns:

~~~text
AI Decision
~~~

AI & Data Lead must not own overall Battle State transitions.

---

## 26.4 Game & Gameplay Lead → UI/Architecture Lead: Architecture / UI

UI/Architecture Lead receives:

~~~text
Game States
State Transitions
Persistent GameState
Required Screens
Gameplay Events
Gameplay Commands
~~~

UI/Architecture Lead presents and wires these states without redefining gameplay rules.

---

# 27. Shared Gameplay State

The gameplay domain must represent at least:

~~~text
GameState
├── GamePhase
├── Player
├── CurrentArea
├── PlayerPosition
├── Team
├── Collection
├── Inventory
├── Money
├── Badges
├── DefeatedGyms
├── UnlockedRegions
├── DefeatedBosses
└── ProgressionFlags
~~~

Battle-specific transient state remains separate:

~~~text
BattleState
├── BattleType
├── PlayerSide
├── EnemySide
├── CurrentTurn
├── AvailableActions
├── BattleResult
└── Temporary Effects
~~~

This separation prevents temporary Battle state from accidentally becoming
persistent World state.

---

# 28. Gameplay Events

Recommended events:

~~~text
PlayerMoved
EncounterStarted
BattleStarted
BattleWon
BattleLost
MonsterCaptured
MonsterLevelUp
EvolutionAvailable
MonsterEvolved
ItemObtained
ItemUsed
GymDefeated
BadgeObtained
RegionUnlocked
BossDefeated
GameCompleted
GameSaved
~~~

Event names are implementation contracts and should remain stable after
integration begins.

---

# 29. Gameplay Commands

Recommended commands:

~~~text
MovePlayer
InteractWithNPC
StartBattle
SelectBattleAction
SwitchMonster
UseItem
AttemptCapture
RunFromBattle
ConfirmEvolution
CancelEvolution
EnterGym
SaveGame
LoadGame
~~~

Commands represent player/system intent.

Events represent resulting state changes.

---

# 30. Rule Validation

Every important rule must be testable.

Example:

~~~text
Given:
    Player is in Forest.

When:
    Player completes a valid movement.

Then:
    Encounter probability is evaluated.
~~~

Example:

~~~text
Given:
    Player has 6 Monsters in Active Team.

When:
    Player captures a new Wild Monster.

Then:
    New Monster is sent to Collection/Storage.
~~~

Example:

~~~text
Given:
    Player has completed Gym 1.

When:
    Gym 1 progression is committed.

Then:
    Region 2 becomes UNLOCKED.
~~~