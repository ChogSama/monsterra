## 1. Purpose

`DOMAIN_MODEL.md` định nghĩa gameplay domain model của MONSTERRA ở mức domain.

Mục tiêu của document:

- Xác định các domain object chính.
- Xác định dữ liệu mà mỗi object sở hữu.
- Xác định responsibility của từng object.
- Xác định relationship giữa các object.
- Phân biệt Entity, Value Object, Service và Engine.
- Xác định boundary giữa các thành viên trong team.
- Làm nền tảng cho UML Class Diagram và implementation.
- Định nghĩa gameplay contracts giữa các module.

Nguyên tắc chính:

> Game & Gameplay Lead owns "what the gameplay means".
> Other members own "how their module implements it".

Domain model không phải implementation design cuối cùng.

Không tạo hàng chục Java class chỉ vì mỗi noun trong gameplay đều được biến thành class.

Mỗi domain object phải trả lời được:

1. Object này đại diện cho cái gì?
2. Nó sở hữu dữ liệu gì?
3. Nó chịu trách nhiệm gì?
4. Nó liên hệ với object nào?

---

## 2. Domain Overview

MONSTERRA được chia thành các domain chính:

```text
                         MONSTERRA DOMAIN
                               |
        +----------------------+----------------------+
        |                      |                      |
        v                      v                      v
     PLAYER                  WORLD                 BATTLE
        |                      |                      |
   +----+----+            +----+----+           +----+----+
   |    |    |            |    |    |           |    |    |
   v    v    v            v    v    v           v    v    v
Trainer Team Inventory   Map  Area NPC        Action Skill AI
   |
   v
 Monster
   |
 +---+-------------+
 |   |             |
 v   v             v
Stats Skills    Evolution
                 |
                 v
             Progression
                 |
           +-----+-----+
           |     |     |
           v     v     v
          EXP   Gym  Region

                  +-------------+
                  |  GameState  |
                  +------+------+
                         |
                  +------+------+
                  |             |
                  v             v
                 Save          Load
                  |             |
                  +------+------+
                         v
                     Database
```

---

## 3. Player Domain

### 3.1 Player

`Player` đại diện cho người đang chơi game.

```text
Player
├── id
├── name
├── trainer
├── currentArea
├── position
└── progress
```

### Responsibility

`Player`:

- sở hữu Trainer;
- xác định vị trí hiện tại;
- truy cập tiến trình;
- tương tác với World;
- thực hiện các gameplay interaction ở cấp Player.

`Player` không nên chịu trách nhiệm trực tiếp cho:

- Damage calculation;
- Battle AI;
- Evolution calculation;
- Database persistence;
- JavaFX rendering.

### Relationships

```text
Player
   |
   +-- owns --> Trainer
   |
   +-- has --> Position
   |
   +-- has --> Progress
   |
   +-- located in --> Area
```

---

## 4. Trainer Domain

### 4.1 Trainer

`Trainer` là domain object đại diện cho nhân vật/trainer mà Player điều khiển.

```text
Trainer
├── id
├── name
├── team
├── collection
├── inventory
├── money
└── profile
```

### Responsibility

Trainer quản lý:

- Monster collection;
- Active Team;
- Inventory;
- money;
- Trainer profile;
- các resource mà Player sở hữu.

### Relationships

```text
Player
   |
   +-- owns
        |
        v
      Trainer
        |
    +---+----------+
    |              |
    v              v
   Team        Collection
    |
    v
 Monster
```

---

## 5. Team Domain

### 5.1 Team

`Team` đại diện cho nhóm Monster hiện đang được sử dụng.

```text
Team
├── members
├── maxSize
└── activeMonster
```

### Rules

- Team là một tập con của Collection.
- Team size tối đa là 6.
- Một Monster có thể thuộc Collection nhưng không thuộc Team.
- Monster trong Team phải thuộc Collection của Trainer.
- Active Monster phải là một member hợp lệ của Team.

### Responsibility

`Team` chịu trách nhiệm:

- addMonster();
- removeMonster();
- switchMonster();
- isFull();
- containsMonster();
- getActiveMonster();
- validateTeamComposition().

### Quan hệ Team và Collection

```text
Collection = toàn bộ Monster mà Trainer sở hữu.

Team = Monster đang được sử dụng.
```

Ví dụ:

```text
Collection:
    12 Monsters

Team:
    Flametail
    Aquaffin
    Leafling
    Stormfang
```

Không nên model Team bằng nhiều `List<Monster>` rải rác trong các class khác nhau.

---

## 6. Collection Domain

### 6.1 Collection

`Collection` đại diện cho toàn bộ Monster mà Trainer sở hữu.

```text
Collection
├── monsters
└── capacity
```

### Responsibility

- addMonster();
- removeMonster();
- containsMonster();
- findMonsterById();
- getAllMonsters().

Collection không chịu trách nhiệm:

- Battle;
- Damage calculation;
- AI;
- Evolution calculation;
- persistence.

---

## 7. Monster Domain

### 7.1 Monster

`Monster` là domain object trung tâm của gameplay.

Monster đại diện cho một cá thể Monster cụ thể.

```text
Monster
├── id
├── nickname
├── species
├── level
├── experience
├── currentHP
├── stats
├── types
├── skills
├── statusEffects
└── evolutionState
```

### Responsibility

Monster chịu trách nhiệm:

- lưu trạng thái cá thể;
- nhận EXP;
- tăng Level;
- quản lý HP;
- quản lý Skill;
- quản lý trạng thái;
- kiểm tra khả năng chiến đấu;
- expose các state cần thiết cho Battle.

### Monster không chịu trách nhiệm

Monster không nên tự xử lý:

```text
X toàn bộ Damage calculation
X Battle AI
X Database
X JavaFX
X toàn bộ Battle lifecycle
X toàn bộ Evolution rules
```

Những responsibility này thuộc service/engine/domain khác.

---

## 8. Species Domain

### 8.1 Species

`Species` đại diện cho loài Monster.

```text
Species
├── id
├── name
├── baseStats
├── baseTypes
├── learnableSkills
└── evolutionRules
```

Species là definition/static gameplay data.

### Monster vs Species

```text
Species = loài Monster.

Monster = một cá thể cụ thể thuộc Species.
```

Ví dụ:

```text
Species:
    Flametail

Monster #001:
    nickname = "Blaze"
    level = 22
    hp = 102

Monster #002:
    nickname = "Fire"
    level = 15
    hp = 73
```

Hai Monster có cùng Species nhưng là hai Entity khác nhau.

### Relationship

```text
Species
   ^
   |
   | belongs to
   |
Monster
```

---

## 9. Stats Domain

### 9.1 Stats

`Stats` là Value Object biểu diễn các combat stats của Monster.

```text
Stats
├── maxHP
├── attack
├── defense
├── specialAttack
├── specialDefense
└── speed
```

### Responsibility

Stats chỉ biểu diễn giá trị stat.

Stats không chịu trách nhiệm:

- Battle lifecycle;
- AI;
- database;
- UI;
- progression logic.

### Relationship

```text
Monster
   |
   +-- has-a --> Stats
```

Đây là một ví dụ về Composition.

---

## 10. Type Domain

### 10.1 Type

Type đại diện cho elemental type của Monster hoặc Skill.

Ví dụ:

```java
public enum Type {
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    EARTH,
    AIR,
    LIGHT,
    DARK
}
```

### Rules

Một Monster có thể có:

```text
1-2 Types
```

Ví dụ:

```text
Flametail
    FIRE

Stormfang
    ELECTRIC + AIR
```

---

## 11. TypeEffectivenessMatrix

`TypeEffectivenessMatrix` chứa Type Chart của gameplay.

Ví dụ:

```text
FIRE -> GRASS = 2.0
FIRE -> WATER = 0.5
WATER -> FIRE = 2.0
GRASS -> FIRE = 0.5
```

### API concept

```text
getMultiplier(attackingType, defendingType)
```

Nếu Defender có hai Type:

```text
Fire Skill
      |
      v
Water/Air Monster

Fire -> Water = 0.5
Fire -> Air   = 1.0

Final multiplier = 0.5 * 1.0
```

### Responsibility

TypeEffectivenessMatrix:

- cung cấp type effectiveness;
- không biết Monster cụ thể;
- không biết Battle lifecycle;
- không biết UI;
- không lưu database.

---

## 12. Skill Domain

### 12.1 Skill

`Skill` đại diện cho definition của một skill.

```text
Skill
├── id
├── name
├── type
├── power
├── accuracy
├── resourceCost
├── priority
└── effects
```

Ví dụ:

```text
Flame Burst
Type: FIRE
Power: 80
Accuracy: 95
Priority: 0
```

### Shared Definition

Skill nên được xem là shared definition.

Ví dụ:

```text
Skill Database

Flame Burst
      ^
      |
 +----+----+
 |         |
 v         v
Monster A Monster B
```

Không cần tạo một skill definition độc lập cho từng Monster nếu chúng sử dụng cùng một Skill definition.

### Responsibility

Skill mô tả:

- Skill là gì;
- power;
- type;
- accuracy;
- priority;
- resource cost;
- effect definition.

Skill không chịu trách nhiệm:

- chọn target;
- quyết định AI;
- resolve toàn bộ Battle;
- persistence.

---

## 13. Status Effect Domain

### 13.1 StatusEffect

`StatusEffect` đại diện cho trạng thái tạm thời tác động lên Monster.

```text
StatusEffect
├── type
├── duration
├── intensity
└── effect
```

Ví dụ:

```text
BURN
POISON
STUN
SLOW
```

### Relationship

```text
Monster
   |
   +-- has --> List<StatusEffect>
```

### Responsibility

StatusEffect biểu diễn state/effect đang active.

Logic resolve effect có thể thuộc Battle/StatusEffect service tùy implementation.

---

## 14. Evolution Domain

### 14.1 EvolutionRule

`EvolutionRule` mô tả điều kiện để một Species có thể tiến hóa.

```text
EvolutionRule
├── requiredLevel
├── requiredItem
├── requiredLocation
├── requiredTime
├── requiredCondition
└── targetSpecies
```

Ví dụ:

```text
Flametail
   |
   | Level >= 20
   | + Fire Stone
   v
Infernoon
```

### Relationship

```text
Species
   |
   +-- has --> EvolutionRule
                    |
                    v
              Target Species
```

---

## 15. EvolutionService

Không nên để Monster tự xử lý toàn bộ evolution condition.

Thay vào đó:

```text
EvolutionService
```

### Responsibility

```text
canEvolve(monster, context)
performEvolution(monster)
```

### Flow

```text
Monster gains EXP
       |
       v
Level Up
       |
       v
EvolutionService
       |
       v
Check EvolutionRule
       |
       v
Can evolve?
   +---+---+
   |       |
  No      Yes
   |       |
   v       v
Continue Preview
           |
           v
        Confirm
           |
           v
       Evolution
```

EvolutionService chịu trách nhiệm orchestration của evolution.

---

## 16. World Domain

### 16.1 World

`World` đại diện cho toàn bộ thế giới gameplay.

```text
World
├── maps
├── areas
├── npcs
├── gyms
└── regions
```

### Responsibility

World:

- quản lý cấu trúc thế giới;
- cung cấp access tới Map/Area;
- không xử lý Battle damage;
- không xử lý AI;
- không chịu trách nhiệm persistence.

---

## 17. Map Domain

### 17.1 Map

```text
Map
├── id
├── name
├── width
├── height
└── areas
```

### Relationship

```text
World
   |
   +-- contains --> Map
```

---

## 18. Area Domain

### 18.1 Area

```text
Area
├── id
├── name
├── tiles
├── encounters
├── npcs
├── items
└── exits
```

Ví dụ:

```text
World
|
+-- Region 1
|   |
|   +-- Starter Town
|   +-- Forest
|   +-- Forest Gym
|
+-- Region 2
    |
    +-- Storm City
    +-- Mountain
    +-- Storm Gym
```

### Responsibility

Area đại diện cho gameplay location.

Area không nên tự xử lý toàn bộ random encounter.

---

## 19. Position Domain

### 19.1 Position

`Position` là Value Object.

```text
Position
├── x
└── y
```

### Relationship

```text
Player
   |
   +-- has --> Position
```

Có thể mở rộng sau này nếu gameplay yêu cầu:

```text
Position
├── x
├── y
└── mapId
```

---

## 20. Encounter Domain

### 20.1 Encounter

`Encounter` mô tả khả năng xuất hiện Monster trong Area.

```text
Encounter
├── type
├── area
├── monsterSpecies
├── levelRange
├── encounterRate
└── conditions
```

Ví dụ:

```text
Forest Encounter Table

Leafling     30%
Bugster      25%
Flametail    10%
Stormfang     5%
```

### Responsibility

Encounter là gameplay data/definition.

Encounter không tự random encounter.

---

## 21. EncounterManager

`EncounterManager` chịu trách nhiệm resolve encounter.

### API concept

```text
checkEncounter()
generateEncounter()
createWildMonster()
```

### Flow

```text
Player moves
      |
      v
Area checks encounter zone
      |
      v
EncounterManager
      |
      v
Roll probability
      |
      v
Encounter?
   +--+--+
  No    Yes
        |
        v
 Generate Wild Monster
        |
        v
 Start Battle
```

---

## 22. Battle Domain

Battle là domain chủ yếu do Battle System owner triển khai, nhưng gameplay contract được định nghĩa ở đây.

### 22.1 Battle

```text
Battle
├── battleType
├── playerTeam
├── enemyTeam
├── currentTurn
├── state
├── actions
└── result
```

### Battle Type

```java
enum BattleType {
    WILD,
    TRAINER,
    GYM,
    BOSS
}
```

### Responsibility

Battle đại diện cho một Battle session.

Battle quản lý:

- participants;
- state;
- actions;
- turns;
- result.

Battle không nên chứa toàn bộ implementation của damage calculation hoặc AI.

---

## 23. BattleState

Battle state machine:

```text
START
   |
   v
PLAYER_TURN
   |
   v
ACTION_RESOLUTION
   |
   v
ENEMY_TURN
   |
   v
ACTION_RESOLUTION
   |
   v
CHECK_RESULT
   |
   +---- CONTINUE
   |
   +---- VICTORY
   |
   +---- DEFEAT
```

Có thể mở rộng:

```text
BATTLE_START
PLAYER_TURN
ENEMY_TURN
SWITCHING
VICTORY
DEFEAT
ESCAPED
```

---

## 24. BattleAction

`BattleAction` là object mô tả một action được lựa chọn trong Battle.

```text
BattleAction
├── actor
├── actionType
├── skill
├── target
└── priority
```

### Action Type

```java
enum ActionType {
    SKILL,
    SWITCH,
    ITEM,
    RUN
}
```

Ví dụ:

```text
Flametail
   |
   v
Flame Burst
   |
   v
Stormfang
```

Battle Engine nhận:

```text
BattleAction(
    actor = Flametail,
    actionType = SKILL,
    skill = Flame Burst,
    target = Stormfang
)
```

### Responsibility

BattleAction là command/value-like object.

Nó mô tả:

- ai thực hiện;
- thực hiện action gì;
- skill nào;
- target nào;
- priority.

Nó không tự execute action.

---

## 25. BattleResult

`BattleResult` đại diện cho kết quả cuối cùng của Battle.

```text
BattleResult
├── outcome
├── winner
├── loser
├── experienceGained
├── itemsObtained
└── monstersDefeated
```

### Outcome

```text
VICTORY
DEFEAT
ESCAPE
```

### Importance

Gameplay sau Battle phụ thuộc vào BattleResult.

Ví dụ:

```text
BattleResult
     |
     +-- Victory
     |      |
     |      +-- EXP
     |      +-- Rewards
     |      +-- Progress
     |
     +-- Defeat
     |      |
     |      +-- Game Over / Recovery
     |
     +-- Escape
            |
            +-- Return to exploration
```

---

## 26. BattleEngine

`BattleEngine` chịu trách nhiệm simulation của Battle.

```text
BattleEngine
├── resolveAction()
├── resolveTurn()
├── checkBattleEnd()
└── produceResult()
```

BattleEngine có thể sử dụng:

```text
DamageCalculator
TypeEffectivenessMatrix
BattleAI
StatusEffectResolver
```

### Flow

```text
BattleAction
     |
     v
BattleEngine
     |
 +---+-------------------+
 |       |               |
 v       v               v
Damage  Type            AI
Calc    Matrix
 |
 v
BattleResult
```

---

## 27. DamageCalculator

`DamageCalculator` chịu trách nhiệm tính damage theo gameplay rules.

Ví dụ conceptual inputs:

```text
Attacker
Defender
Skill
TypeEffectiveness
Modifiers
```

Output:

```text
DamageResult
```

`Monster` không tự tính toàn bộ damage.

---

## 28. DamageResult

`DamageResult` là Value Object biểu diễn kết quả tính damage.

```text
DamageResult
├── damage
├── critical
├── effectiveness
└── modifiers
```

Ví dụ:

```text
damage = 42
critical = false
effectiveness = 2.0
```

---

## 29. BattleAI

`BattleAI` chịu trách nhiệm quyết định action cho enemy.

```text
BattleAI
├── observe()
├── evaluate()
├── selectAction()
└── difficulty
```

BattleAI không nên truy cập UI hoặc database.

### Flow

```text
Battle State
     |
     v
BattleAI
     |
     +-- Observe
     |
     +-- Analyze
     |
     +-- Evaluate
     |
     +-- Select Action
     |
     v
BattleAction
```

---

## 30. Gym Domain

### 30.1 Gym

`Gym` đại diện cho một Gym definition trong World.

```text
Gym
├── id
├── name
├── region
├── leader
├── team
├── specialtyType
├── badge
├── reward
└── unlockRequirement
```

Ví dụ:

```text
Forest Gym

Specialty:
GRASS

Leader:
Flora

Team:
Leafling
Vinebeast
Forestaur

Reward:
Forest Badge

Unlock:
Region 2
```

Gym là static gameplay definition.

---

## 31. GymProgress

`GymProgress` đại diện cho trạng thái của một Player đối với Gym.

```text
GymProgress
├── gymId
├── completed
├── attempts
└── completedAt
```

### Quan trọng

Không gắn progression trực tiếp vào Gym.

```text
Gym
    = static definition

GymProgress
    = Player-specific state
```

Ví dụ:

```text
Gym:
    Forest Gym

Player A:
    completed = true

Player B:
    completed = false
```

---

## 32. Region Domain

### 32.1 Region

```text
Region
├── id
├── name
├── areas
├── requiredBadges
└── unlockConditions
```

Region là một đơn vị progression/world grouping.

---

## 33. Progress Domain

### 33.1 Progress

`Progress` đại diện cho progression của Player.

```text
Progress
├── currentRegion
├── unlockedRegions
├── gymProgress
├── defeatedBosses
├── completedTutorial
└── storyFlags
```

Ví dụ:

```text
Progress

Tutorial = completed

Gym 1 = completed
Gym 2 = locked
Gym 3 = locked

Region 1 = unlocked
Region 2 = unlocked
Region 3 = locked
```

### Responsibility

Progress chịu trách nhiệm lưu và expose progression state.

Logic unlock cụ thể có thể nằm trong:

```text
ProgressionService
```

---

## 34. ProgressionService

`ProgressionService` xử lý các gameplay rules liên quan đến progression.

Ví dụ:

```text
canUnlockRegion()
canChallengeGym()
completeGym()
unlockRegion()
registerBossDefeat()
```

Flow:

```text
BattleResult
     |
     v
ProgressionService
     |
 +---+----------------+
 |                    |
 v                    v
GymProgress       RegionProgress
 |
 v
Unlock Condition
```

---

## 35. Boss Domain

Boss có thể được model như một specialized gameplay encounter.

```text
Boss
├── id
├── name
├── area
├── team
├── requirements
└── rewards
```

Boss defeat state nên nằm trong `Progress`, không phải trong static Boss definition.

---

## 36. Item Domain

### 36.1 Item

```text
Item
├── id
├── name
├── type
├── description
└── effect
```

### Item Type

```text
HEAL
CAPTURE
EVOLUTION
BATTLE
KEY
OTHER
```

Item definition là shared/static data.

---

## 37. Inventory Domain

### 37.1 Inventory

Inventory đại diện cho Item resources của Trainer.

Concept:

```text
Inventory
    |
    +-- Map<Item, Quantity>
```

Ví dụ:

```text
Potion      x5
Fire Stone  x1
Capture Orb x8
```

### Responsibility

Inventory:

- addItem();
- removeItem();
- hasItem();
- getQuantity();
- consumeItem().

Inventory không nên chứa logic của từng gameplay system.

---

## 38. Capture Domain

Vì MONSTERRA có Monster collection, Capture là một gameplay domain riêng.

### CaptureService

```text
CaptureService
├── canCapture()
├── calculateProbability()
├── attemptCapture()
└── addToCollection()
```

### Flow

```text
Wild Battle
     |
     v
Player uses Capture Item
     |
     v
Check capture eligibility
     |
     v
Calculate capture probability
     |
     v
Success?
   +---+---+
  No      Yes
  |        |
  v        v
Continue  Monster added
          to Collection
```

### Responsibility boundary

CaptureService quyết định gameplay outcome.

Inventory chỉ quản lý item ownership/quantity.

Collection chỉ quản lý Monster ownership.

---

## 39. GameState Domain

### 39.1 GameState

`GameState` là aggregate state cấp cao dùng để snapshot gameplay state.

```text
GameState
├── Player
├── WorldState
├── Progress
├── Inventory
├── Settings
└── timestamp
```

Ví dụ:

```text
GameState
|
+-- Player
|   |
|   +-- Trainer
|   +-- Position
|
+-- WorldState
|
+-- Progress
|   |
|   +-- GymProgress
|   +-- RegionProgress
|
+-- Inventory
|
+-- Settings
|
+-- timestamp
```

### Vì sao cần GameState?

Thay vì gameplay perspective phải quản lý:

```text
Save Player
Save Monster
Save Inventory
Save Gym
Save Map
Save Position
Save Settings
...
```

ta có:

```text
Current Game
      |
      v
GameState
      |
      v
SaveService
      |
      v
Repository
      |
      v
SQLite
```

---

## 40. WorldState

`WorldState` đại diện cho các state của World cần persistence.

Có thể bao gồm:

```text
WorldState
├── discoveredAreas
├── defeatedBosses
├── openedPaths
├── worldFlags
└── encounterState
```

Không phải toàn bộ static World definition cần được save.

Ví dụ:

```text
World
    = static definition

WorldState
    = dynamic player-specific state
```

---

## 41. Save Domain

### SaveService

`SaveService` chịu trách nhiệm orchestration save/load.

```text
SaveService
├── save(GameState)
└── load(saveId)
```

Flow:

```text
Current Game
      |
      v
GameState
      |
      v
SaveService
      |
      v
Repository
      |
      v
SQLite
```

SaveService không nên biết SQL details.

---

## 42. Entity, Value Object, Service và Engine

### 42.1 Entity

Entity có identity riêng.

```text
Player
Trainer
Monster
Species
Skill
Item
Gym
Region
Battle
```

### 42.2 Value Object

Value Object chủ yếu biểu diễn giá trị.

```text
Stats
Position
BattleAction
BattleResult
DamageResult
```

Một Value Object không cần identity business riêng nếu giá trị giống nhau thì có thể được xem là tương đương.

### 42.3 Service

Service xử lý nghiệp vụ/orchestration.

```text
BattleService
EvolutionService
EncounterService
CaptureService
ProgressionService
SaveService
```

### 42.4 Engine

Engine xử lý simulation/decision.

```text
BattleEngine
BattleAI
DamageCalculator
```

---

## 43. Core Entity Relationship

```text
Player
   |
   +-- owns --> Trainer
                  |
          +-------+--------+
          |       |        |
          v       v        v
        Team  Collection Inventory
          |
          v
       Monster
          |
     +----+----------------+
     |    |        |       |
     v    v        v       v
 Species Stats   Skills StatusEffects
     |
     v
EvolutionRule
     |
     v
Target Species
```

---

## 44. World Relationship

```text
World
  |
  +-- contains --> Map
                     |
                     +-- contains --> Area
                                        |
                          +-------------+-------------+
                          |             |             |
                          v             v             v
                      Encounter        NPC          Items
                          |
                          v
                    Wild Monster
```

---

## 45. Battle Relationship

```text
Battle
  |
  +-- has --> BattleState
  |
  +-- receives --> BattleAction
  |
  +-- produces --> BattleResult
  |
  +-- uses --> BattleEngine
                 |
        +--------+---------+
        |        |         |
        v        v         v
     Damage    Type      BattleAI
     Calculator Matrix
```

---

## 46. Progression Relationship

```text
BattleResult
     |
     v
ProgressionService
     |
 +---+----------------------+
 |                          |
 v                          v
GymProgress              Progress
                            |
                    +-------+-------+
                    |               |
                    v               v
             Region Unlock     Boss Defeat
```

---

## 47. Save Relationship

```text
Game
 |
 v
GameState
 |
 +-- Player
 +-- WorldState
 +-- Progress
 +-- Inventory
 +-- Settings
 |
 v
SaveService
 |
 v
Repository
 |
 v
SQLite
```

---

## 48. Responsibility Matrix

| Domain Object | Main Responsibility | Should NOT Own |
|---|---|---|
| Player | Player-level state and interaction | Damage, AI, DB |
| Trainer | Monster/team/inventory ownership | Battle calculation |
| Team | Active Monster team management | Damage |
| Collection | Monster ownership | Battle |
| Monster | Individual Monster state | Entire Battle |
| Species | Monster species definition | Individual runtime state |
| Stats | Combat stat values | Battle lifecycle |
| Type | Elemental classification | Battle |
| TypeEffectivenessMatrix | Type multiplier rules | Monster state |
| Skill | Skill definition | AI decision |
| StatusEffect | Status state | Save system |
| EvolutionRule | Evolution conditions | UI |
| EvolutionService | Evolution orchestration | Rendering |
| World | World structure | Damage |
| Map | Map structure | Battle |
| Area | Location gameplay data | Full encounter resolution |
| Encounter | Encounter definition | Randomization execution |
| EncounterManager | Encounter generation | UI |
| Battle | Battle session state | Persistence |
| BattleAction | Player/AI command | Action execution |
| BattleResult | Battle outcome | Database |
| BattleEngine | Battle simulation | UI |
| DamageCalculator | Damage calculation | Save/load |
| BattleAI | Enemy decision-making | Database |
| Gym | Static Gym definition | Player progress |
| GymProgress | Player Gym state | Static Gym data |
| Region | Region definition | Player state |
| Progress | Player progression state | Rendering |
| ProgressionService | Unlock/progression rules | Database |
| Item | Item definition | Inventory quantity |
| Inventory | Item ownership/quantity | Battle AI |
| CaptureService | Capture resolution | Item storage |
| GameState | Snapshot of game state | UI rendering |
| SaveService | Save/load orchestration | SQL details |

---

## 49. Team Ownership

Game & Gameplay Lead không nên tự thiết kế implementation details của tất cả module.

Game & Gameplay Lead chịu trách nhiệm định nghĩa:

```text
WHAT the gameplay means.
```

Module owner chịu trách nhiệm:

```text
HOW the module implements it.
```

---

## 50. Responsibility Boundary giữa Team Members

| Domain | Owner | Game & Gameplay Lead's Responsibility |
|---|---|---|
| Player/Trainer | Game & Gameplay Lead | Define gameplay responsibility |
| Team | Game & Gameplay Lead + Monster & Progression Lead | Define rules |
| Monster | Monster & Progression Lead | Define gameplay requirements |
| Species | Monster & Progression Lead | Define progression data |
| Stats | Monster & Progression Lead | Define progression rules |
| Skill | Battle System Lead + Monster & Progression Lead | Define gameplay behavior |
| Type | Battle System Lead + Monster & Progression Lead | Define gameplay rule |
| Battle | Battle System Lead | Define gameplay contract |
| BattleAction | Battle System Lead | Define allowed actions |
| BattleResult | Game & Gameplay Lead + Battle System Lead | Define consequence |
| BattleAI | AI & Data Lead | Define expected AI behavior |
| PatternAnalyzer | AI & Data Lead | Define gameplay expectations |
| World/Map | Game & Gameplay Lead + UI/Architecture Lead | Define gameplay structure |
| Encounter | Game & Gameplay Lead + Monster & Progression Lead | Define encounter rules |
| Gym | Game & Gameplay Lead + Monster & Progression Lead/Battle System Lead | Define progression |
| Region | Game & Gameplay Lead | Own unlock rules |
| Inventory | Monster & Progression Lead + UI/Architecture Lead | Define gameplay rules |
| Save/GameState | Game & Gameplay Lead + UI/Architecture Lead | Define persisted state |
| UI | UI/Architecture Lead | Define required screens/flow |

---

## 51. Cross-Team Contract: Game & Gameplay Lead -> Battle System Lead

Battle must support:

```text
Wild Battle
Trainer Battle
Gym Battle
Boss Battle
```

Player actions:

```text
Skill
Switch
Item
Run
```

`Run` chỉ hợp lệ khi BattleType/rules cho phép.

Battle must return:

```text
Victory
Defeat
Escape
```

Battle must expose:

```text
current state
current turn
active monsters
available actions
result
```

Battle must not expose internal implementation details unnecessarily.

---

## 52. Cross-Team Contract: Game & Gameplay Lead -> Monster & Progression Lead

Monster must support:

```text
Level
EXP
Stats
Skills
Type
Evolution
Status
```

Progression flow:

```text
EXP
 |
 v
Level Up
 |
 v
Evolution Check
 |
 v
Evolution
```

Monster & Progression Lead cần đảm bảo Monster state đủ để Battle, Progression và Capture sử dụng.

---

## 53. Cross-Team Contract: Game & Gameplay Lead -> AI & Data Lead

AI must:

```text
observe battle state
analyze previous player actions
evaluate available actions
select counter strategy
support difficulty levels
```

AI output nên phù hợp với:

```text
BattleAction
```

AI không nên trực tiếp mutate UI hoặc database.

---

## 54. Cross-Team Contract: Game & Gameplay Lead -> UI/Architecture Lead

UI phải expose gameplay states:

```text
Main Menu
New Game
Map
Battle
Team
Inventory
Evolution
Gym
Settings
```

UI không được chứa:

```text
Damage calculation
EXP calculation
AI logic
Evolution calculation
Database logic
```

UI chỉ nên:

```text
Display State
Receive Input
Send Command
Observe Result
```

---

## 55. Domain-to-UML Preparation

Sau Step 4, team có đủ nguyên liệu cho Class Diagram cấp domain.

### Player/Trainer

```text
+----------------+
|    Player      |
+----------------+
| id             |
| name           |
| position       |
| progress       |
+----------------+
        |
        | owns
        v
+----------------+
|    Trainer     |
+----------------+
| id             |
| name           |
| money          |
+----------------+
   |       |       |
   v       v       v
 Team  Collection Inventory
```

### Monster

```text
+----------------+
|    Monster     |
+----------------+
| id             |
| nickname       |
| level          |
| experience     |
| currentHP      |
+----------------+
   |       |       |
   v       v       v
Species Stats  Skills
   |
   v
EvolutionRule
```

### Battle

```text
+----------------+
|    Battle      |
+----------------+
| battleType     |
| currentTurn    |
| state          |
+----------------+
    |       |       |
    v       v       v
 State   Action   Result
    |
    v
BattleEngine
    |
 +--+---------+---------+
 |            |         |
 v            v         v
Damage      Type      BattleAI
Calculator  Matrix
```

---

## 56. Aggregate Boundaries

Các aggregate chính ở mức gameplay:

### Player Aggregate

```text
Player
 |
 +-- Trainer
      |
      +-- Team
      +-- Collection
      +-- Inventory
```

### Monster Aggregate

```text
Monster
 |
 +-- Stats
 +-- StatusEffects
 +-- Runtime state
```

Species, Skill và Item có thể được xem là shared definitions/reference data.

### World Aggregate

```text
World
 |
 +-- Map
      |
      +-- Area
```

### Battle Aggregate

```text
Battle
 |
 +-- BattleState
 +-- BattleActions
 +-- BattleResult
```

### Progress Aggregate

```text
Progress
 |
 +-- GymProgress
 +-- Region progress
 +-- Boss progress
 +-- Story flags
```

### GameState

`GameState` là snapshot/aggregate cấp application state để persistence, không nhất thiết là một gameplay Entity.

---

## 57. Static Definition vs Runtime State

Đây là distinction quan trọng trong MONSTERRA.

### Static Definition

```text
Species
Skill
Item
Gym
Region
Encounter definition
EvolutionRule
TypeEffectivenessMatrix
```

Static definition mô tả nội dung/gameplay configuration.

### Runtime State

```text
Monster
Trainer
Team
Inventory
Progress
GymProgress
WorldState
Battle
GameState
```

Runtime state thay đổi trong quá trình chơi.

### Ví dụ

```text
Species
    Flametail
    baseAttack = 60

Monster
    #001
    species = Flametail
    level = 22
    currentHP = 102
```

Không nên lưu runtime state vào static definition.

---

## 58. Shared Definition vs Instance

Một số object nên được hiểu là definition được dùng lại.

Ví dụ:

```text
Skill
    Flame Burst
```

nhiều Monster có thể reference cùng Skill definition.

Tương tự:

```text
Species
    Flametail
```

nhiều Monster có thể thuộc cùng Species.

Item definition cũng có thể shared:

```text
Item
    Potion
```

Inventory mới lưu:

```text
Potion x 5
```

---

## 59. Domain Rule Placement

Một gameplay rule nên được đặt ở nơi gần nhất với domain concept nhưng không làm Entity trở thành God Object.

Ví dụ:

```text
Rule:
"Team size <= 6"
```

nên thuộc Team.

```text
Rule:
"Fire deals 2x damage to Grass"
```

nên thuộc TypeEffectivenessMatrix.

```text
Rule:
"Monster evolves at level 20 with Fire Stone"
```

nên thuộc EvolutionRule + EvolutionService.

```text
Rule:
"Player can enter Region 2 after Gym 1"
```

nên thuộc ProgressionService/Region unlock rules.

```text
Rule:
"Enemy chooses an action based on player behavior"
```

nên thuộc BattleAI.

---

## 60. Avoid God Objects

Không tạo một class như:

```text
GameManager
```

rồi đặt tất cả:

```text
battle()
move()
save()
load()
evolve()
capture()
calculateDamage()
levelUp()
unlockGym()
```

vào đó.

Thay vào đó:

```text
BattleService
EvolutionService
CaptureService
ProgressionService
SaveService
EncounterManager
```

mỗi service có boundary rõ ràng.

---

## 61. Avoid Anemic Domain Model

Không nên biến tất cả Entity thành DTO thuần túy:

```text
Monster
    getters
    setters
```

và đặt toàn bộ behavior ở một `GameManager`.

Một số behavior nên nằm gần Entity:

```text
Monster
    gainExperience()
    increaseLevel()
    takeDamage()
    heal()
    applyStatus()
```

Trong khi behavior phức tạp/orchestration nằm ở Service:

```text
EvolutionService
BattleService
CaptureService
ProgressionService
```

Mục tiêu là cân bằng:

```text
Entity owns its local invariant.

Service owns cross-entity workflow.
```

---

## 62. OOP Principles trong Domain Model

### Encapsulation

Entity giữ invariant của chính nó.

Ví dụ:

```text
Team
    addMonster()
```

thay vì:

```text
team.getMembers().add(monster)
```

### Composition

Ví dụ:

```text
Monster
   |
   +-- Stats
   +-- StatusEffects
```

### Association

Ví dụ:

```text
Monster --> Species
Monster --> Skill
```

### Polymorphism

Có thể áp dụng sau khi requirements rõ hơn cho:

```text
BattleAI
BattleAction
ItemEffect
StatusEffect
```

Không nên tạo inheritance hierarchy chỉ để "có polymorphism".

---

## 63. Battle Architecture

Battle domain nên có flow:

```text
BattleAction
      |
      v
BattleEngine
      |
      +--> Validate Action
      |
      +--> Determine Priority
      |
      +--> Resolve Action
      |
      +--> DamageCalculator
      |
      +--> TypeEffectivenessMatrix
      |
      +--> StatusEffect Resolution
      |
      +--> Check Faint
      |
      +--> Check Battle End
      |
      v
BattleResult
```

AI chỉ tạo action:

```text
BattleAI
    |
    v
BattleAction
```

AI không tự resolve damage.

---

## 64. Evolution Architecture

```text
Monster
   |
   | gains EXP
   v
Progression
   |
   v
Level Up
   |
   v
EvolutionService
   |
   v
EvolutionRule
   |
   v
Check Context
   |
 +---+---+
 |       |
 No      Yes
 |       |
 v       v
Continue Preview
           |
           v
        Confirm
           |
           v
    Change Species
```

EvolutionService không nên phụ thuộc trực tiếp vào UI implementation.

UI chỉ gọi command và hiển thị result.

---

## 65. Encounter Architecture

```text
Player Movement
      |
      v
Area
      |
      v
EncounterManager
      |
      v
Encounter Table
      |
      v
Probability Roll
      |
 +----+----+
 |         |
No        Yes
 |         |
 v         v
Continue  Generate Monster
              |
              v
          Start Battle
```

EncounterManager tạo runtime Monster từ static Species definition.

---

## 66. Capture Architecture

```text
Battle
 |
 +-- active Wild Monster
 |
 v
CaptureService
 |
 +-- validate item
 |
 +-- validate target
 |
 +-- calculate probability
 |
 +-- roll
 |
 +-- create/update ownership
 |
 v
Collection
```

CaptureService không trực tiếp quản lý inventory data structure.

---

## 67. Progression Architecture

```text
BattleResult
     |
     v
ProgressionService
     |
 +---+-----------------------+
 |                           |
 v                           v
EXP / Level               Gym / Region
 |                           |
 v                           v
Monster state             Progress
 |
 v
Evolution check
```

---

## 68. Save/Load Architecture

```text
Runtime Domain
      |
      v
GameState
      |
      v
SaveService
      |
      v
Repository Interface
      |
      v
SQLite Repository
```

Domain không nên phụ thuộc trực tiếp vào SQLite.

Ví dụ không nên:

```text
Monster.saveToDatabase()
```

Thay vào đó:

```text
SaveService
    |
    v
Repository
```

---

## 69. Persistence Boundary

Domain layer không cần biết:

```text
SQL
SQLite connection
PreparedStatement
ResultSet
JavaFX
File chooser
```

Persistence layer chịu trách nhiệm mapping:

```text
Domain Object
      |
      v
Persistence Model / Mapper
      |
      v
Database
```

---

## 70. Domain Events có thể mở rộng sau

Hiện tại không bắt buộc implementation Event Bus.

Nhưng domain có thể conceptualize các event:

```text
MonsterLevelUp
MonsterEvolved
BattleStarted
BattleFinished
GymCompleted
RegionUnlocked
MonsterCaptured
```

Các event này hữu ích khi project mở rộng.

Ví dụ:

```text
BattleFinished
      |
 +----+----------------+
 |                     |
 v                     v
ProgressionService   UI/Event Listener
```

Chỉ thêm Event Bus khi complexity thực sự cần.

---

## 71. Non-Goals của Domain Model

Domain Model không quyết định:

- Java package structure cuối cùng;
- database schema chi tiết;
- JavaFX component hierarchy;
- SQL queries;
- exact AI algorithm;
- exact damage formula nếu chưa chốt;
- animation system;
- rendering architecture;
- thread model;
- network architecture.

Domain Model chỉ định nghĩa gameplay domain và contracts.

---

## 72. Implementation Guidance

Một implementation package structure có thể được tạo sau khi domain model được approve.

Ví dụ:

```text
com.monsterra
|
+-- domain
|   |
|   +-- player
|   +-- monster
|   +-- world
|   +-- battle
|   +-- progression
|   +-- item
|   +-- capture
|   +-- save
|
+-- application
|   |
|   +-- services
|
+-- infrastructure
|   |
|   +-- persistence
|   +-- ai
|
+-- presentation
|   |
|   +-- javafx
```

Đây chỉ là implementation direction, không phải domain model mandatory structure.

---

## 73. Final Domain Model

```text
                         +-------------+
                         |  GameState  |
                         +------+------+
                                |
       +------------------------+------------------------+
       |                        |                        |
       v                        v                        v
    PLAYER                    WORLD                  PROGRESS
       |                        |                        |
       v                        v                        v
    Trainer                  Map/Area             Gym/Region
       |                        |                        |
   +---+-----+                  v                        v
   |         |              Encounter                Unlock
   v         v
  Team   Collection
   |         |
   +----+----+
        |
        v
     Monster
        |
   +----+-------------------+
   |    |         |         |
   v    v         v         v
Species Stats   Skills   StatusEffects
   |
   v
EvolutionRule
   |
   v
Target Species


                    +-------------+
                    |   BATTLE    |
                    +------+------+
                           |
                +----------+----------+
                |          |           |
                v          v           v
             Action      State       Result
                |
                v
          BattleEngine
                |
        +-------+---------+
        |       |         |
        v       v         v
     Damage    Type      BattleAI
     Calc      Matrix
```

---

## 74. Core Relationships

| Relationship | Meaning |
|---|---|
| Player -> Trainer | Player owns Trainer |
| Trainer -> Team | Trainer owns one active Team |
| Trainer -> Collection | Trainer owns Monster collection |
| Trainer -> Inventory | Trainer owns Inventory |
| Team -> Monster | Team contains Monster |
| Collection -> Monster | Collection contains owned Monster |
| Monster -> Species | Monster belongs to Species |
| Monster -> Stats | Monster owns Stats |
| Monster -> Skill | Monster learns/uses Skill |
| Monster -> StatusEffect | Monster has active statuses |
| Species -> EvolutionRule | Species defines evolution rules |
| EvolutionRule -> Species | Rule targets another Species |
| World -> Map | World contains Maps |
| Map -> Area | Map contains Areas |
| Area -> Encounter | Area has Encounter definitions |
| Gym -> GymProgress | Player-specific progress references Gym |
| Player -> Progress | Player owns progression state |
| Battle -> Monster | Battle uses Monster participants |
| Battle -> BattleAction | Battle processes actions |
| Battle -> BattleResult | Battle produces result |
| Battle -> BattleAI | Battle can use AI for enemy actions |
| GameState -> Player | Snapshot contains Player state |
| GameState -> Progress | Snapshot contains Progress |
| GameState -> WorldState | Snapshot contains dynamic World state |

---

## 75. Final Responsibility Rule

MONSTERRA domain architecture follows this principle:

```text
                    GAMEPLAY RULE
                         |
                         v
                   DOMAIN OBJECT
                         |
                         v
                    RESPONSIBILITY
                         |
                         v
                    RELATIONSHIP
                         |
                         v
                 SERVICE / ENGINE
                         |
                         v
                  IMPLEMENTATION
```

Không thiết kế class chỉ dựa trên tên danh từ.

Mỗi class phải có:

```text
Meaning
Data
Responsibility
Relationship
Invariant
```

---