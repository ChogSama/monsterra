# MONSTERRA — Gameplay Module Contracts

## 0. Purpose and Scope

This document defines the behavioral contracts and communication boundaries between MONSTERRA gameplay modules.

The objectives are to:

- Establish a single source of truth for gameplay rules.
- Allow Dev Team to implement their modules independently.
- Prevent duplicate gameplay logic across the Battle Engine, AI, and UI.
- Define valid inputs, expected outputs, preconditions, postconditions, and failure behavior.
- Ensure that battle results, progression, encounters, and persistence remain consistent.

This document specifies **behavioral contracts**, not a mandatory one-class-per-contract architecture. The team may adjust interface names and implementation details while preserving the agreed behavior.

### Design Principles

1. Gameplay rules are defined by Game & Gameplay Lead and implemented by the responsible module.
2. The Battle Engine is the authority for validating and resolving Battle Actions.
3. Monster & Progression owns Monster data and progression calculations.
4. AI proposes actions but never executes gameplay mutations.
5. UI renders application state and submits user commands; it does not calculate gameplay outcomes.
6. Persistence validates data before restoring a playable GameState.
7. Rejected commands must not produce unintended gameplay state changes.
8. Operations that grant rewards or modify progression must be protected against duplicate application.

### Terminology

| Term | Definition |
|---|---|
| GameState | The authoritative persistent and runtime state of a game session. |
| BattleState | The current state of a specific Battle. |
| BattleAction | A requested action, such as using a Skill, switching Monster, using an Item, or running. |
| Legal Actions | Actions permitted by the current BattleState and gameplay rules. |
| Encounter | A potential or confirmed interaction with a wild Monster or another opponent. |
| Progression | EXP, Level, Stats, Skill learning, and Evolution. |
| Domain Event | A notification that a gameplay fact has occurred. |
| Command | A request to perform an operation that may change state. |

---

## 1. Module Ownership

### Game & Gameplay Lead

Responsibilities:

- Define gameplay rules and their expected outcomes.
- Approve Battle Action policies by Battle type.
- Define reward, capture, progression, and save/load behavior.
- Resolve conflicts between module assumptions.
- Approve changes to shared contracts.

Game & Gameplay Lead owns the rules, not necessarily the implementation of every rule.

### Battle System Lead

Responsibilities:

- Implement Battle initialization and turn resolution.
- Validate Battle Actions against current state and rules.
- Calculate battle outcomes and emit battle events.
- Prevent invalid actions and duplicate battle rewards.
- Expose BattleState and Legal Actions to AI and UI.
- Keep the Battle Engine independent of JavaFX and persistence details.

### Monster & Progression Lead

Responsibilities:

- Manage Monster instances, Species, Stats, Skills, and status conditions.
- Apply EXP rewards and Level Ups.
- Evaluate Skill learning and Evolution eligibility.
- Execute confirmed Evolution.
- Manage ownership changes resulting from Capture.
- Validate Monster data required by gameplay.

### AI & Data Lead

Responsibilities:

- Analyze BattleState and available Battle history.
- Evaluate legal actions.
- Select an action according to the configured difficulty.
- Use fallback behavior when history or analysis is insufficient.
- Return a proposed action without mutating game state.

### UI & Architecture Lead

Responsibilities:

- Define application architecture and service boundaries.
- Implement Controllers and JavaFX views.
- Integrate gameplay commands and results.
- Implement Repository and persistence mechanisms.
- Display errors and gameplay events.
- Ensure UI components do not duplicate domain logic.

### Ownership Rules

| Concern | Authoritative owner | Consumers |
|---|---|---|
| Gameplay rules | Game & Gameplay Lead | Dev Team |
| Battle validation and resolution | Battle System Lead | Game & Gameplay, AI & Data, UI & Architecture Leads |
| Monster data and progression | Monster & Progression Lead | Game & Gameplay, Battle System, UI & Architecture Leads |
| AI action selection | AI & Data Lead | Battle System Lead |
| Session orchestration | UI & Architecture Lead, following Game & Gameplay Lead's rules | Battle System, Monster & Progression, AI & Data Leads |
| UI rendering | UI & Architecture Lead | Gameplay services |
| Save/load implementation | UI & Architecture Lead | Game services |
| Persistent state validity | Game & Gameplay Lead defines rules; UI & Architecture Lead validates integration, with domain validation delegated to Battle System/Monster & Progression Lead | All relevant modules |

No module may silently redefine a rule owned by another module.

---

## 2. Gameplay Session Contract

### 2.1. Supported Flow

The expected application flow is:

`Main Menu → New Game / Continue → Tutorial (if required) → Exploring → Battle → Exploring`

Other terminal or intermediate states may include:

- Main Menu
- Exploring
- Battle
- Paused
- Game Over

The application architecture may represent these states differently, but their behavior must remain consistent.

### 2.2. New Game

**Input:** `NewGameRequest`

**Output:** A newly initialized `GameSession` or a structured error.

Preconditions:

- Required initial configuration is valid.
- The starting Species, Player defaults, and starting World are configured.

Postconditions on success:

- A new Player and initial GameState are created.
- The initial Team, Collection, Inventory, and World state follow the approved starting rules.
- No previous save is silently overwritten.
- The application enters the initial playable state.

Failure behavior:

- An invalid configuration must not produce a partially initialized playable session.
- Any failure must be returned to the caller and logged where appropriate.

### 2.3. Continue

**Input:** `LoadGameRequest`

**Output:** A validated, restored GameSession or a structured error.

Preconditions:

- A save exists.
- The save can be read and validated.

Postconditions on success:

- Player, Team, Collection, Inventory, World position, and Progress are restored.
- Monster references and Team membership are consistent.
- The resulting state satisfies domain invariants.
- The application enters the correct restored state.

If loading fails, the existing in-memory session must not be partially replaced.

### 2.4. Session State Invariants

- A session cannot enter Exploring while an active Battle still owns control of gameplay.
- Only one active Battle may control a given gameplay session unless concurrent Battles are explicitly introduced.
- Session transitions must occur through application services, not direct UI state mutation.
- A failed command must not trigger an unintended screen transition.
- A successful Battle must complete its result processing before control returns to Exploring.

---

## 3. Battle Contract

### 3.1. Battle Types

The initial supported Battle types are:

- `WILD`
- `TRAINER`
- `GYM`
- `BOSS`

Battle type determines which actions are permitted and which rules apply.

### 3.2. Approved Action Policy

The following is the baseline gameplay policy for the first implementation.

| Action | Wild | Trainer | Gym | Boss |
|---|---|---|---|---|
| Use Skill | Allowed when legal | Allowed when legal | Allowed when legal | Allowed when legal |
| Switch Monster | Allowed when legal | Allowed when legal | Allowed when legal | Allowed when legal |
| Use Item | Allowed when permitted by Item rules | Allowed when permitted by Item rules | Allowed when permitted by Item rules | Allowed when permitted by Item rules |
| Run | Allowed when permitted by Run rules | Not allowed | Not allowed | Not allowed |
| Capture | Allowed when permitted by Capture rules | Not allowed | Not allowed | Not allowed |

Additional constraints:

- A fainted Monster cannot execute a Skill.
- A switch requires a valid, owned, eligible replacement Monster.
- Item use must validate Item availability, target eligibility, and Battle-type restrictions.
- Run attempts in Wild Battles may fail according to the approved Run formula.
- An action that is not legal for the current state must be rejected.
- The AI must use the same legal-action policy as the player.

This baseline is authoritative unless Game & Gameplay Lead explicitly approves a change.

### 3.3. Battle Initialization

**Input:** `BattleRequest`

Required information:

- Battle ID or identifier generated by the Battle service.
- Battle type.
- Player's active Monster and eligible Team members.
- Opponent configuration.
- Battle rules and relevant encounter metadata.

Preconditions:

- The session exists.
- The Player and opponent configuration are valid.
- Both sides have an eligible Monster to enter Battle.
- The requested Battle type is supported.
- The session does not already have an active Battle.

Postconditions:

- A new BattleState is created.
- Initial HP and status values are initialized according to the approved rules.
- Battle history starts empty or contains the initialization event.
- A `BattleStarted` event is produced.
- The session enters Battle.

A failed initialization must not leave the session in a partially active Battle.

### 3.4. Battle Action Execution

The logical flow is:

`Validate → Determine Action Order → Resolve Action(s) → Apply Effects → Check Fainting → Check Battle Outcome → Emit Result`

The exact order of simultaneous effects, status effects, switching, and end-of-turn effects must be documented by Battle System Lead and approved by Game & Gameplay Lead before implementation.

**Input:**

- Battle ID
- BattleAction
- Optional command identifier for duplicate-request protection

**Output:**

- Updated BattleState
- Action resolution result
- Domain events
- Battle outcome, if terminal
- Structured error if the request is rejected

Rules:

1. Validate the Battle ID and active BattleState.
2. Verify that the action is legal for the current state.
3. Resolve the action using the approved turn rules.
4. Update state only through authoritative domain services.
5. Determine whether a Monster fainted or the Battle ended.
6. Record the result and associated events.
7. Apply terminal rewards at most once.

A rejected action must not consume a turn, spend an Item, alter HP, or grant rewards unless an explicitly documented rule says otherwise.

### 3.5. Battle Result

A Battle result must provide sufficient information for the caller to understand:

- Which action was accepted and resolved.
- Whether the action succeeded, failed, or was rejected.
- The updated state or a reference to the authoritative BattleState.
- HP and status changes relevant to the result.
- Monster fainting and replacement requirements.
- Whether the Battle continues or has ended.
- The outcome and winning side, if applicable.
- Events generated during resolution.
- Rewards or reward-processing status when the Battle ends.

Suggested outcomes:

- `IN_PROGRESS`
- `PLAYER_WON`
- `PLAYER_LOST`
- `PLAYER_ESCAPED`
- `CAPTURED`
- `DRAW`, only if explicitly supported by the gameplay rules.

An invalid request is an error, not a Battle outcome.

### 3.6. Battle Service Interface

The following interface is illustrative:

```java
public interface BattleService {

    BattleStartResult startBattle(BattleRequest request);

    BattleActionResult executeAction(
        String battleId,
        BattleAction action
    );

    BattleState getBattleState(String battleId);

    List<BattleAction> getLegalActions(String battleId);
}
```

The team may use UUIDs, records, immutable DTOs, or other compatible types.

The public interface must not expose JavaFX-specific types.

### 3.7. Reward Processing

- Rewards are calculated from the approved Battle outcome and reward rules.
- A completed Battle must have a stable unique identifier.
- Reward processing must be idempotent: retrying the same completion request must not grant EXP, currency, or Items twice.
- Battle resolution and progression application must have an explicit integration boundary.
- The session must not return to Exploring until required terminal processing has reached a consistent state.
- A failed reward operation must be surfaced and handled without falsely reporting full completion.

---

## 4. Monster & Progression Contract

### 4.1. Responsibilities

Monster & Progression Lead owns the implementation of:

- Monster instances and their Species.
- Base Stats and derived Stats.
- Current HP and persistent status data.
- EXP and Level.
- Skill learning and Skill validation.
- Evolution eligibility and execution.
- Collection and Team ownership updates caused by Capture.

Game & Gameplay Lead defines the gameplay behavior that these operations must satisfy.

### 4.2. Experience Distribution

Baseline rule:

- Every eligible Monster that participated in the Battle receives an EXP share.
- Monsters that did not participate receive no EXP in the initial version.
- The reward amount and distribution formula are configured by the approved progression rules.
- Each participating Monster can receive the reward only once for a given Battle completion.
- A Battle that ends without a Player victory grants no victory EXP unless another explicit reward rule applies.

Monster & Progression Lead implements the formula; Game & Gameplay Lead approves the formula and the eligibility rules.

### 4.3. Level Up

When EXP reaches the threshold for the next Level:

1. Increase Level.
2. Recalculate derived Stats using the approved formulas.
3. Process any Level-based Skill learning opportunities.
4. Re-evaluate Evolution eligibility.
5. Continue processing if enough EXP remains for additional Levels.
6. Emit the corresponding progression events.

Multiple Level Ups must be processed in order. The final Level and Stats must be consistent with the accumulated EXP.

The exact handling of unspent EXP, Skill selection, and HP changes must follow the approved progression configuration.

### 4.4. Skill Learning

- Monster & Progression Lead determines whether a Monster can learn a Skill.
- If the Monster has reached the relevant threshold, the progression service returns the available learning result.
- If the Skill capacity is limited, the Player must make any required replacement decision through an explicit command.
- The UI displays the learning options and submits the decision.
- The UI must not directly modify the Monster's Skill list.

A rejected Skill-learning request must not modify the Skill list.

### 4.5. Evolution

Baseline rules:

- Evolution requires the Monster to meet its configured EvolutionRule.
- Evolution requires explicit Player confirmation.
- Declining Evolution does not remove eligibility.
- The Monster may evolve later while the rule remains satisfied, unless the specific EvolutionRule defines an expiry.
- Evolution changes Species or Form according to the EvolutionRule.
- Level and accumulated EXP are retained.
- Current HP is preserved up to the new maximum HP; if the new maximum is lower, current HP is clamped to the valid range.
- Skills are retained unless an explicit Species/Evolution rule requires a change.
- Persistent status conditions are retained unless the EvolutionRule explicitly defines a reset.
- The operation must not create a second Monster instance or duplicate ownership.

Evolution emits `EvolutionCompleted` only after the change succeeds.

### 4.6. Suggested Progression Interface

```java
public interface ProgressionService {

    ProgressionResult awardExperience(
        String battleId,
        List<ExperienceAward> awards
    );

    List<Skill> getAvailableSkills(String monsterId);

    EvolutionEligibility checkEvolution(String monsterId);

    EvolutionResult confirmEvolution(String monsterId);
}
```

The implementation may split these operations across services, provided the contract remains consistent.

---

## 5. Encounter & Capture Contract

### 5.1. Encounter Generation

**Input:**

- Current World position.
- Area identifier.
- Encounter conditions.
- Relevant encounter configuration.

**Output:**

- A valid Encounter, or
- An explicit result indicating that no Encounter occurred.

Rules:

- Encounter generation is allowed only in eligible Areas.
- The generated Monster must belong to the Area's configured encounter pool.
- The generated Monster must satisfy its configured Level and generation constraints.
- The encounter result must not directly mutate the UI.
- Starting a Battle from an Encounter must go through the Battle service.
- A failed Battle initialization must not consume or corrupt the active Encounter unless an explicit rule defines that behavior.

### 5.2. Capture Policy

Baseline rules:

- Capture is allowed only in `WILD` Battles.
- Capture attempts require a valid Capture Item or other explicitly approved capture mechanism.
- The target must be an eligible wild Monster.
- Capture probability is calculated by the authoritative Capture service using target HP, status, Item properties, and configured species modifiers.
- UI and AI may display or estimate capture information only through data provided by the domain service; neither may independently determine the actual outcome.

### 5.3. Team Capacity and Collection

- The active Team has a maximum capacity of six Monsters.
- If the Team has fewer than six Monsters, a successfully captured Monster joins the Team according to the approved placement rule.
- If the Team is full, the captured Monster is placed in the Collection or storage system.
- If storage is also unavailable or full, the capture operation must follow an explicitly configured fallback. The system must not silently delete an existing Monster.
- Ownership is updated through one authoritative service.

### 5.4. Capture Result

The result must include:

- Success or failure.
- Target Monster identifier, when applicable.
- Item consumption result.
- Ownership destination on success.
- Battle continuation or termination status.
- Events produced.

A successful Capture ends the Wild Battle immediately and produces a terminal `CAPTURED` outcome.

A failed Capture consumes the Item if and only if the approved Item rule specifies consumption on failure, then continues the Battle if the target and Battle remain eligible.

### 5.5. Suggested Capture Interface

```java
public interface CaptureService {

    CaptureResult attemptCapture(
        String battleId,
        String targetMonsterId,
        String itemId
    );
}
```

If Capture is represented as a `BattleAction`, Battle System Lead coordinates the operation with the Capture service and remains responsible for Battle-state consistency.

---

## 6. Adaptive AI Contract

### 6.1. Responsibilities

AI & Data Lead implements action selection, not Battle execution.

The AI receives a snapshot of the current BattleState, relevant history, and the authoritative list of Legal Actions. It returns one proposed action from that list.

Battle System Lead remains responsible for validating and executing the selected action.

### 6.2. AI Input

The AI request should include:

- Battle ID or snapshot identifier.
- Battle type and difficulty.
- Current public BattleState.
- Relevant Monster HP, status, and Skill information.
- Opponent information legitimately available to the AI.
- Battle history.
- Legal Actions.
- Any configured strategy constraints.

The AI must not receive hidden or inaccessible information unless the difficulty rules explicitly permit it.

### 6.3. AI Output

The output contains:

- One selected BattleAction.
- Optional evaluation score or explanation.
- Optional diagnostic metadata.

The selected action must belong to the supplied Legal Actions.

### 6.4. Difficulty Levels

| Difficulty | Expected behavior |
|---|---|
| `EASY` | Basic legal action selection with limited randomness. |
| `NORMAL` | Considers HP, Skill effectiveness, and Type Advantage. |
| `HARD` | Considers action history, likely opponent behavior, and tactical switching. |
| `BOSS` | Uses configured Boss strategies, counters, and switching when legal. |

Difficulty changes strategy, not the fundamental Battle rules.

### 6.5. Fallback and Failure

- If history is insufficient, use a basic strategy rather than an unsupported prediction.
- If the evaluator fails, select a deterministic safe action from Legal Actions.
- If the Legal Actions list is empty, report that the AI cannot act; do not fabricate an action.
- If Battle System Lead rejects a proposed action because the state changed, AI & Data Lead must request a fresh snapshot and recompute.
- AI analysis must not mutate HP, Inventory, Monster state, or BattleState.

### 6.6. Suggested AI Interface

```java
public interface BattleAI {

    BattleAction chooseAction(AIContext context);
}
```

`AIContext` should be a read-only snapshot and must include the authoritative Legal Actions list.

---

## 7. Save & Load Contract

### 7.1. Persistent Data

| Category | Required data |
|---|---|
| Player | Stable ID, Trainer name, currency, and required identity data |
| Monster | Stable ID, Species/Form, Level, EXP, Stats-relevant data, current HP, Skills, and persistent status |
| Team | Ordered list of Monster IDs |
| Collection | Owned Monster IDs and storage organization |
| Inventory | Item IDs and quantities |
| World | Area, position, unlocked locations, and persistent world flags |
| Progression | Gym completion, Badges, unlocked regions, and story flags |
| Settings | Audio, speed, difficulty, and other supported preferences |
| Metadata | Save schema version, save timestamp, and integrity metadata where supported |

Static definitions, such as Species templates and Skill definitions, may be loaded from game data rather than duplicated in each save.

### 7.2. Battle Persistence Policy

Baseline rule for the first release:

- An active Battle is not saved as a resumable Battle.
- A save request during Battle is rejected unless the product explicitly supports a safe checkpoint operation.
- If the application closes unexpectedly during Battle, the last completed save is restored.
- Battle rewards are not granted merely because the application was interrupted.

The team must not serialize an incomplete Battle as though it were a completed state.

### 7.3. Save Contract

**Input:** A consistent GameState snapshot.

**Output:** A confirmed save result or a structured failure.

Rules:

- Save must capture a logically consistent state.
- The save operation must not report success before the persistence layer confirms completion.
- Manual Save and Auto Save use the same serialization and validation rules.
- A failed save must preserve the previous valid save whenever the storage strategy permits.
- Concurrent save operations must not produce a partially mixed state.

### 7.4. Load Contract

Before restoring a save, validate:

- Schema version and supported migration path.
- Required fields and identifiers.
- Monster ownership and Team references.
- Team capacity and Inventory quantities.
- Level, EXP, HP, and other domain constraints.
- World position and progression consistency.
- Required configuration references.

If the save is invalid:

- Return a structured error.
- Do not partially replace the active session.
- Do not silently invent missing gameplay state.
- Preserve the original save for recovery where possible.
- Offer a recovery or migration path only when it is explicitly supported.

### 7.5. Suggested Persistence Interface

```java
public interface GameRepository {

    SaveResult save(GameStateSnapshot snapshot);

    LoadResult load();

    boolean hasSave();
}
```

Serialization format, file naming, atomic-write strategy, and migration implementation belong to UI & Architecture Lead.

---

## 8. Gameplay Events

Events describe facts that have already occurred. They are not commands and must not independently repeat the operation that generated them.

| Event | When emitted | Primary consumers |
|---|---|---|
| `BattleStarted` | Battle initialization succeeds | UI, AI |
| `TurnResolved` | A turn is resolved successfully | UI, AI |
| `MonsterFainted` | A Monster reaches the fainted state | Battle, UI, AI |
| `BattleWon` | Player victory is finalized | Progression, UI, Save |
| `BattleLost` | Player defeat is finalized | Gameplay, UI |
| `BattleEscaped` | A permitted Run succeeds | Gameplay, UI |
| `MonsterCaptured` | Capture succeeds and ownership is committed | Collection, UI, Save |
| `ExperienceGained` | EXP is successfully applied | Progression, UI |
| `LevelUp` | A Level increase is committed | Progression, UI |
| `SkillLearned` | A Skill is successfully learned | UI, Save |
| `EvolutionAvailable` | Evolution eligibility is established | Progression, UI |
| `EvolutionCompleted` | Evolution succeeds | Progression, UI, Save |
| `GymCompleted` | Gym completion is committed | World, UI, Save |
| `RegionUnlocked` | A region is unlocked | World, UI, Save |
| `GameSaved` | Persistence confirms a successful save | UI, Logging |

### Event Rules

- Events are emitted only after the corresponding state change succeeds.
- A rejected command must not emit success events.
- Events should include an event ID, timestamp where useful, and relevant domain identifiers.
- Consumers must not assume events arrive exactly once unless the event-delivery mechanism guarantees it.
- State-changing consumers must be idempotent where duplicate delivery is possible.
- Event ordering must preserve dependencies, such as Battle completion before its rewards are finalized.
- UI event handlers update presentation only; they do not repeat domain mutations.

A lightweight result object or callback is sufficient for the first implementation. A dedicated Event Bus is optional.

---

## 9. Cross-module Interaction Matrix

| Sender | Receiver | Data exchanged | Purpose |
|---|---|---|---|
| UI / Controller | Game Service | User command | Request a gameplay operation |
| Game Service | Battle System | BattleRequest / BattleAction | Start and resolve Battles |
| Battle System | Monster & Progression | Monster references / EXP awards | Apply approved progression |
| Battle System | AI | BattleState snapshot / Legal Actions | Request action selection |
| AI | Battle System | Proposed BattleAction | Return a legal action candidate |
| Game Service | Encounter System | World position / Area | Evaluate encounters |
| Encounter System | Battle System | Encounter / BattleRequest | Start a Wild Battle |
| Battle System | Capture Service | Capture request / Battle context | Resolve Capture consistently |
| Capture Service | Monster & Progression | Ownership update request | Add captured Monster to Team or Collection |
| Monster & Progression | Game Service / UI | Progression results / events | Reflect progression changes |
| Game Service | Repository | GameState snapshot | Save the session |
| Repository | Game Service | LoadResult | Restore a validated session |
| Domain Services | UI | Results / domain events | Update presentation |
| Game & Gameplay Lead-defined rules | Dev Team | Approved policies and constraints | Keep gameplay behavior consistent |

### Dependency Rules

- UI depends on application services and presentation DTOs, not Battle internals.
- AI depends on Battle snapshots and Legal Actions, not UI controls.
- Battle depends on domain contracts, not JavaFX.
- Progression calculations remain inside the Monster & Progression module.
- Repository code must not independently invent gameplay defaults.
- Cross-module mutations must pass through the authoritative service for that domain.

---

## 10. Error Handling & Invalid Actions

### 10.1. Error Categories

| Category | Examples | Expected behavior |
|---|---|---|
| Validation | Invalid target, unavailable Skill, insufficient Item quantity | Reject without unintended state changes |
| State Conflict | Battle already ended, stale action request | Reject and return current-state information when safe |
| Domain Rule | Run forbidden in Boss Battle | Reject with a domain-specific reason |
| Not Found | Unknown Battle or Monster ID | Return a structured not-found error |
| Persistence | Disk write failure, corrupted save | Report failure and preserve valid state where possible |
| Configuration | Unknown Species, missing EvolutionRule | Fail safely and provide diagnostics |
| AI Failure | Empty action list, evaluator exception | Use the defined fallback or report inability to act |
| Integration | Progression or Capture operation fails | Do not report full success until state is consistent |

### 10.2. Rejected Action Contract

For any rejected action:

1. Return a structured error code and human-readable message.
2. Preserve the authoritative state, except for explicitly documented system metadata.
3. Do not consume a turn or Item.
4. Do not grant EXP or rewards.
5. Do not emit success events.
6. Allow the caller to refresh state and retry when appropriate.

Example error codes:

- `BATTLE_NOT_FOUND`
- `BATTLE_ALREADY_ENDED`
- `ACTION_NOT_ALLOWED`
- `INVALID_TARGET`
- `INSUFFICIENT_ITEM`
- `MONSTER_NOT_ELIGIBLE`
- `CAPTURE_NOT_ALLOWED`
- `SAVE_NOT_FOUND`
- `SAVE_CORRUPTED`
- `PERSISTENCE_FAILURE`
- `AI_NO_LEGAL_ACTIONS`

### 10.3. Idempotency and State Safety

- Battle completion must be idempotent.
- EXP and reward application must be deduplicated by Battle ID and reward identity.
- Capture must not create duplicate ownership records.
- Retried save operations must not create logically inconsistent state.
- If a multi-step operation fails, the module must either roll back or expose a recoverable, explicitly represented partial state.
- Logs must provide sufficient context for debugging without exposing unnecessary sensitive data.

---

## 11. Open Decisions and Assumptions

The following baseline decisions apply to the initial implementation. Game & Gameplay Lead must approve any change that affects gameplay behavior or a shared interface.

| Topic | Baseline decision | Owner for implementation |
|---|---|---|
| Run policy | Allowed only in Wild Battles, subject to the Run rule | Game & Gameplay + Battle System Leads |
| Capture policy | Allowed only in Wild Battles | Game & Gameplay + Battle System + Monster & Progression Leads |
| Team capacity | Maximum six Monsters | Game & Gameplay + Monster & Progression Leads |
| Full Team | Send captured Monster to Collection | Monster & Progression + UI & Architecture Leads |
| EXP eligibility | Participating Monsters only | Game & Gameplay + Monster & Progression Leads |
| EXP formula | Configurable formula approved by Game & Gameplay Lead | Monster & Progression Lead |
| Multiple Level Ups | Process sequentially | Monster & Progression Lead |
| Evolution confirmation | Explicit Player confirmation required | Monster & Progression + UI & Architecture Leads |
| Declined Evolution | Eligibility remains available | Monster & Progression Lead |
| Evolution HP | Preserve current HP, capped at new maximum | Monster & Progression Lead |
| Evolution Skills | Retain existing Skills unless a rule says otherwise | Monster & Progression Lead |
| Active Battle saving | Not resumable in the initial release | Game & Gameplay + UI & Architecture Leads |
| Invalid actions | Reject without unintended gameplay mutation | Battle System Lead |
| AI authority | AI proposes; Battle Engine validates and executes | Battle System + AI & Data Leads |
| Event infrastructure | Result objects or callbacks initially; Event Bus optional | UI & Architecture Lead |
| Duplicate rewards | Prevented using Battle identity and idempotent processing | Battle System + Monster & Progression Leads |
| Save validation | Validate before replacing the active session | UI & Architecture Lead + domain owners |

### Decisions Requiring Explicit Approval Before Expansion

The following are not required to block a basic prototype but must be resolved before the relevant feature is considered complete:

- Exact Damage, turn-order, accuracy, critical-hit, and status-effect formulas.
- Exact Run probability formula.
- Exact Capture probability formula and Item consumption behavior.
- EXP curve, reward scaling, and rounding rules.
- Skill slot limits and Skill replacement UX.
- EvolutionRule exceptions, including any Evolution-specific resets.
- Battle defeat consequences, such as returning to a checkpoint or losing currency.
- Gym and Boss reward policies.
- Save schema migration and recovery policy.
- Whether any future mode permits active Battle persistence.

Changes to these decisions must be documented and communicated to affected owners before implementation changes are merged.

---

## 12. Acceptance Criteria

### Gameplay Session

- [ ] New Game creates a valid session without silently overwriting an existing save.
- [ ] Continue restores all required persistent state.
- [ ] Invalid loading does not partially replace an active session.
- [ ] The application cannot return to Exploring while a Battle remains active.

### Battle

- [ ] All four Battle types are supported by the contract.
- [ ] Legal Actions reflect the Battle type and current state.
- [ ] Boss and Trainer Battles reject Run.
- [ ] Non-Wild Battles reject Capture.
- [ ] Invalid actions do not consume turns or Items.
- [ ] Battle results expose terminal outcomes and relevant events.
- [ ] A completed Battle cannot grant rewards more than once.
- [ ] The Battle Engine has no JavaFX dependency.

### Monster & Progression

- [ ] EXP is awarded only to eligible Monsters.
- [ ] Repeated reward processing does not duplicate EXP.
- [ ] Multiple Level Ups produce consistent final Stats.
- [ ] Skill learning validates eligibility.
- [ ] Evolution requires confirmation and preserves the approved state.
- [ ] Progression events are emitted only after successful changes.

### Encounter & Capture

- [ ] Encounters use the configured Area encounter pool.
- [ ] Capture is restricted to eligible Wild Battles.
- [ ] A successful Capture updates ownership exactly once.
- [ ] A full Team routes captured Monsters to Collection.
- [ ] Capture outcomes correctly control Battle termination.

### Adaptive AI

- [ ] AI receives an authoritative BattleState snapshot and Legal Actions.
- [ ] AI returns only a proposed action.
- [ ] AI does not mutate HP, Inventory, or BattleState.
- [ ] AI falls back safely when history or evaluation is insufficient.
- [ ] Battle System Lead validates every action returned by AI & Data Lead.

### Save & Load

- [ ] Save captures a consistent GameState.
- [ ] Save failure is reported accurately.
- [ ] Load validates data before session restoration.
- [ ] Invalid references and unsupported save versions are handled explicitly.
- [ ] An interrupted Battle is not restored as a completed Battle.

### Architecture & Integration

- [ ] Each module has a documented owner and public boundary.
- [ ] UI does not duplicate Battle, Capture, or Progression calculations.
- [ ] Cross-module operations use agreed request/result contracts.
- [ ] Errors have structured codes and consistent failure behavior.
- [ ] All unresolved decisions affecting implemented features are recorded.
- [ ] Contract changes are reviewed by the relevant module owners.

---

## 13. Implementation Handoff

### Battle System

Implement Battle initialization, action validation, turn resolution, Legal Actions, terminal outcomes, and duplicate reward protection. Consume the approved gameplay policies without introducing independent rules.

### Monster & Progression

Implement Monster state management, EXP, Level Up, Skill learning, Evolution, and ownership updates. Keep progression formulas within the domain module and follow the approved invariants.

### AI & Data

Implement AI against immutable Battle snapshots and the authoritative Legal Actions list. Return action proposals only, with deterministic fallback behavior where appropriate.

### UI & Architecture

Implement application services, Controllers, JavaFX presentation, Repository, and persistence. Keep UI behavior separate from domain decisions and expose structured errors to the presentation layer.

### Game & Gameplay Lead

Review cross-module changes that alter gameplay behavior, resolve conflicting assumptions, approve changes to the shared contracts, and verify acceptance criteria during integration.