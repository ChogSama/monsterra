# MONSTERRA Gameplay Specification

## 1. Purpose

Tài liệu này định nghĩa gameplay scope và các quy tắc kết nối giữa các subsystem của MONSTERRA.

Game & Gameplay Lead chịu trách nhiệm định nghĩa:

- Core Gameplay Loop
- Gameplay State Flow
- Gameplay State Machine
- Encounter Flow
- Reward Flow
- Player Progression Flow
- World/Region Progression
- Gym/Boss Progression
- Boundary giữa các gameplay subsystem
- Cross-system contracts
- Gameplay acceptance criteria

Các subsystem owner chịu trách nhiệm triển khai chi tiết bên trong domain của mình.

---

## 2. Core Gameplay Loop

Gameplay loop chính của MVP:

    EXPLORE
       ↓
    ENCOUNTER
       ↓
    BATTLE
       ↓
    REWARD
       ↓
    PROGRESS
       ↓
    TEAM BUILDING
       ↓
    GYM
       ↓
    BOSS
       ↓
    NEW REGION
       ↓
    EXPLORE

Mục tiêu của MVP là chứng minh được loop hoàn chỉnh từ lúc bắt đầu game đến khi người chơi mở khóa region tiếp theo.

---

## 3. Player Progression

Player progression ở cấp gameplay gồm:

    Player
    ├── Trainer
    ├── Team
    ├── Inventory
    ├── Money
    ├── Badges
    ├── Unlocked Areas
    └── Progress

MVP constraint:

    Maximum team size = 6 Monster

Các công thức và data cụ thể của progression thuộc Monster & Progression Lead.

---

# 4. Gameplay State Architecture

Gameplay được tổ chức thành ba lớp khái niệm:

    Game State
        │
        ├── Gameplay Flow
        │
        └── Battle Session
                │
                └── Battle State

### 4.1 Game State

Game State biểu diễn trạng thái cấp cao của toàn bộ game.

MVP Game States:

    MAIN_MENU
    NEW_GAME
    LOADING
    TUTORIAL
    EXPLORATION
    PAUSED
    GAME_OVER

### 4.2 Gameplay Flow

Một số hoạt động gameplay không cần trở thành top-level Game State.

Các flow chính:

    ENCOUNTER
    GYM_CHALLENGE
    BOSS_CHALLENGE
    PROGRESSION
    REWARD
    EVOLUTION
    AREA_UNLOCK

Các flow này được thực hiện bên trong hoặc giữa các Game State thay vì làm phình Game State Machine.

### 4.3 Battle Session

`BATTLE` là một gameplay session được khởi tạo từ:

    Encounter
    Gym Challenge
    Boss Challenge

Battle có state machine riêng.

    GameState
        │
        └── BATTLE Session
                │
                └── BattleState

Battle System Lead sở hữu implementation của Battle State Machine.

Game & Gameplay Lead chỉ định nghĩa:

- Khi nào tạo Battle Request
- Khi nào Battle được xem là kết thúc
- Gameplay phải làm gì với Battle Result
- State nào tiếp theo sau Battle

---

# 5. Gameplay State Machine

## 5.1 High-Level Flow

                         ┌──────────────┐
                         │  MAIN_MENU   │
                         └──────┬───────┘
                                │
                    ┌───────────┴───────────┐
                    │                       │
                New Game                 Continue
                    │                       │
                    ▼                       ▼
                NEW_GAME                 LOADING
                    │                       │
                    ▼                       │
                TUTORIAL                    │
                    │                       │
                    └───────────┬───────────┘
                                ▼
                          EXPLORATION
                                │
               ┌────────────────┼────────────────┐
               │                │                │
               ▼                ▼                ▼
          ENCOUNTER       GYM/BOSS ACCESS      PAUSED
               │                │                │
               └────────┬───────┘                │
                        ▼                        │
                      BATTLE ◄───────────────────┘
                        │
                        ▼
                 BATTLE RESULT
                        │
                        ▼
                PROGRESSION FLOW
                        │
                ┌───────┼────────┐
                │       │        │
                ▼       ▼        ▼
              REWARD LEVEL UP EVOLUTION
                │       │        │
                └───────┼────────┘
                        ▼
                   EXPLORATION
                        │
                        ▼
                  AREA UNLOCK
                        │
                        ▼
                   EXPLORATION

---

# 6. Game States

| State | Description |
|---|---|
| `MAIN_MENU` | Main screen của game |
| `NEW_GAME` | Tạo Trainer và Game State mới |
| `LOADING` | Đọc và khôi phục save data |
| `TUTORIAL` | Hướng dẫn gameplay ban đầu |
| `EXPLORATION` | Người chơi khám phá thế giới |
| `PAUSED` | Game đang tạm dừng |
| `GAME_OVER` | Game kết thúc hoặc chờ recovery |

---

## 6.1 MAIN_MENU

Player có thể:

- Start New Game
- Continue
- Exit

Transitions:

    New Game
        → NEW_GAME

    Continue
        → LOADING

---

## 6.2 NEW_GAME

Mục đích:

- Khởi tạo Trainer
- Khởi tạo Team
- Khởi tạo Inventory
- Khởi tạo Money
- Khởi tạo World State
- Khởi tạo Progress State

Transition:

    Profile Created
        → TUTORIAL

---

## 6.3 LOADING

Mục đích:

- Load persistent Game State
- Validate save data
- Khôi phục gameplay session

Transitions:

    Load Success
        → EXPLORATION

    Load Failed
        → MAIN_MENU

---

## 6.4 TUTORIAL

Tutorial giới thiệu các gameplay mechanic cần thiết cho MVP.

Transition:

    Tutorial Complete
        → EXPLORATION

---

## 6.5 EXPLORATION

Đây là gameplay state chính của người chơi.

Player có thể:

- Di chuyển
- Khám phá area
- Trigger encounter
- Tiếp cận NPC
- Tiếp cận Gym
- Tiếp cận Boss
- Pause game

Transitions:

    Encounter Triggered
        → BATTLE

    Enter Gym Challenge
        → BATTLE

    Enter Boss Challenge
        → BATTLE

    Pause
        → PAUSED

---

## 6.6 PAUSED

Game tạm dừng gameplay.

Player có thể:

- Resume
- Open relevant pause options
- Return về menu nếu gameplay design cho phép

Transition chính:

    Resume
        → EXPLORATION

---

## 6.7 GAME_OVER

Game kết thúc khi gameplay condition tương ứng xảy ra.

MVP có thể hỗ trợ recovery hoặc quay về menu tùy implementation.

Game Over không tự quyết định bởi UI.

Gameplay System quyết định transition vào `GAME_OVER`.

---

# 7. Battle State Machine

Battle là subsystem state machine độc lập nằm bên trong Game State.

    BATTLE_START
          │
          ▼
    PLAYER_TURN
          │
          ▼
    RESOLVING_ACTION
          │
          ├───────────────┐
          │               │
          ▼               ▼
    ENEMY_TURN         VICTORY
          │
          ▼
    RESOLVING_ACTION
          │
          ├───────────────┐
          │               │
          ▼               ▼
    PLAYER_TURN        DEFEAT

Battle States:

| State | Description |
|---|---|
| `BATTLE_START` | Khởi tạo battle session |
| `PLAYER_TURN` | Chờ Player chọn action |
| `ENEMY_TURN` | Chờ AI chọn action |
| `RESOLVING_ACTION` | Execute và resolve action |
| `VICTORY` | Player thắng |
| `DEFEAT` | Player thua |
| `BATTLE_END` | Cleanup và tạo Battle Result |

Battle State Machine thuộc Battle System Lead.

---

# 8. Transition Rules

| Current State | Event | Next State |
|---|---|---|
| `MAIN_MENU` | New Game | `NEW_GAME` |
| `MAIN_MENU` | Continue | `LOADING` |
| `NEW_GAME` | Profile Created | `TUTORIAL` |
| `LOADING` | Load Success | `EXPLORATION` |
| `LOADING` | Load Failed | `MAIN_MENU` |
| `TUTORIAL` | Tutorial Complete | `EXPLORATION` |
| `EXPLORATION` | Encounter Triggered | `BATTLE` |
| `EXPLORATION` | Enter Gym | `BATTLE` |
| `EXPLORATION` | Enter Boss | `BATTLE` |
| `EXPLORATION` | Pause | `PAUSED` |
| `PAUSED` | Resume | `EXPLORATION` |
| `BATTLE` | Victory | `PROGRESSION FLOW` |
| `BATTLE` | Defeat | `GAME_OVER` / Recovery Flow |
| `PROGRESSION FLOW` | Complete | `EXPLORATION` |
| `PROGRESSION FLOW` | Region Unlocked | `EXPLORATION` |

`PROGRESSION FLOW` không nhất thiết là một enum Game State.

---

# 9. Encounter Flow

    EXPLORATION
         ↓
    Encounter Trigger
         ↓
    Create Encounter
         ↓
    Battle Request
         ↓
    BATTLE

Encounter System chịu trách nhiệm:

- Detect encounter
- Xác định encounter type
- Chọn encounter data phù hợp
- Khởi tạo Battle Request

Encounter System không chịu trách nhiệm:

- Damage
- Skill execution
- Turn order
- EXP calculation
- Monster progression
- Global Game State transition

Gameplay System quyết định việc chuyển từ Encounter sang Battle.

---

# 10. Battle Flow

    Encounter / Gym / Boss
            ↓
    Battle Request
            ↓
    Battle Start
            ↓
    Battle Engine
            ↓
    Battle Result
            ↓
    Gameplay

Battle System Lead sở hữu:

- Battle Engine
- Turn system
- Action execution
- Skill execution
- Damage calculation
- Battle effects/status
- Battle state
- Battle result

Gameplay System chỉ định nghĩa lifecycle và contract.

### Contract

    Gameplay → Battle Request

    Battle → Battle Result

---

# 11. Reward & Progression Flow

Battle Victory không chuyển trực tiếp thành một loạt top-level Game State.

Flow chuẩn:

    Battle Victory
         ↓
    Battle Result
         ↓
    Reward Processing
         ↓
    EXP / Money / Items
         ↓
    Progression Check
         ↓
    Level Up Check
         ↓
    Evolution Check
         ↓
    Gym / Boss / Region Progression Check
         ↓
    Continue Gameplay

Các bước có thể không xảy ra nếu điều kiện không phù hợp.

Ví dụ:

    Battle Result
         ↓
        EXP
         ↓
      Level Up?
       ┌─┴─┐
      Yes  No
       │    │
       ▼    │
    Evolution?
       │    │
       └────┴──→ Continue

---

# 12. Evolution Flow

    Battle Victory
        ↓
    EXP Reward
        ↓
    Level / Progression Update
        ↓
    Evolution Eligibility Check
        ↓
    Evolution
        ↓
    Continue Gameplay

Monster & Progression Lead sở hữu:

- Monster model
- Stats
- Level
- EXP
- Evolution
- Team system
- Monster progression

Gameplay System chịu trách nhiệm trigger evolution check tại đúng lifecycle point.

Evolution không bắt buộc phải là top-level Game State.

Nếu UI cần một dedicated evolution screen, UI có thể render một Evolution Flow/View trong khi Game State vẫn giữ lifecycle phù hợp.

---

# 13. Gym Progression

    EXPLORATION
         ↓
    Enter Gym
         ↓
    Gym Challenge
         ↓
    Battle
         ↓
    Victory
         ↓
    Reward
         ↓
    Badge / Progress Update
         ↓
    Unlock Progression
         ↓
    EXPLORATION

Gym victory có thể thay đổi:

- Badge state
- Available areas
- Available progression
- Access to subsequent content

Gym data/configuration thuộc subsystem tương ứng.

Gameplay System sở hữu progression rule và lifecycle.

---

# 14. Boss Progression

    EXPLORATION
         ↓
    Boss Access Check
         ↓
    Boss Battle
         ↓
    Victory
         ↓
    Reward
         ↓
    Major Progress Update
         ↓
    AREA UNLOCK
         ↓
    EXPLORATION

Boss Battle vẫn sử dụng Battle System.

Boss AI/behavior thuộc AI & Data Lead.

Gameplay System chịu trách nhiệm:

- Boss access condition
- Boss encounter lifecycle
- Victory consequence
- Region unlock
- Major progression transition

---

# 15. World / Region Progression

    Region N
       ↓
    Explore
       ↓
    Gym / Challenge
       ↓
    Progress
       ↓
    Boss
       ↓
    Unlock Region N+1
       ↓
    Explore

Gameplay System sở hữu rule:

> Một major progression milestone có thể unlock content hoặc region tiếp theo.

World/map implementation thuộc subsystem tương ứng.

---

# 16. Game State Object

`GameState` là snapshot trạng thái gameplay.

Đề xuất cấu trúc:

    GameState
    │
    ├── player
    │   ├── trainer
    │   ├── team
    │   ├── inventory
    │   └── money
    │
    ├── world
    │   ├── currentArea
    │   ├── playerPosition
    │   ├── unlockedAreas
    │   └── npcProgress
    │
    ├── progression
    │   ├── badges
    │   ├── defeatedGyms
    │   ├── defeatedBosses
    │   └── storyProgress
    │
    ├── settings
    │
    └── session
        ├── currentGameState
        └── battleSession?

`GameState` không chứa UI hoặc infrastructure object.

Không được đưa vào `GameState`:

    JavaFX Button
    JavaFX Scene
    JavaFX Stage
    Controller
    Database Connection
    Repository instance
    UI component

UI và persistence chỉ tương tác với Game State thông qua architecture boundary được định nghĩa bởi UI & Architecture Lead.

---

# 17. Gameplay Rule vs Implementation

Game & Gameplay Lead định nghĩa:

    Battle thắng
        ↓
    Player nhận EXP
        ↓
    Kiểm tra Level Up
        ↓
    Kiểm tra Evolution
        ↓
    Cập nhật Progress
        ↓
    Kiểm tra Unlock
        ↓
    Tiếp tục Exploration

Subsystem owner triển khai logic bên trong từng domain.

### Battle System

    Battle
    Turn
    Action
    Damage
    Status
    Victory / Defeat

### Monster & Progression

    EXP calculation
    Level calculation
    Evolution condition
    Stats update
    Team progression

### AI & Data

    AI decision
    Pattern analysis
    Prediction
    Strategy
    Battle configuration

### UI & Architecture

    UI
    State presentation
    Input routing
    Application navigation
    Persistence integration

Gameplay không duplicate implementation của các subsystem trên.

---

# 18. Ownership

## 18.1 Game & Gameplay Lead

Owns:

- Core gameplay loop
- Game States
- Gameplay State Machine
- State transitions
- Gameplay flows
- Encounter lifecycle
- Reward lifecycle
- Player progression flow
- Gym progression
- Boss progression
- Region unlock rules
- Cross-subsystem contracts
- Gameplay acceptance criteria

Không owns:

- Battle Engine implementation
- Damage formula implementation
- Skill execution
- Monster stat implementation
- EXP formula implementation
- Evolution data implementation
- AI decision-making
- JavaFX rendering
- Persistence implementation

---

## 18.2 Battle System Lead

Owns:

- Battle Engine
- Turn system
- Action execution
- Skill execution
- Damage calculation
- Battle effects/status
- Battle state
- Battle result

Contract với Gameplay:

    BattleRequest
        ↓
    Battle System
        ↓
    BattleResult

---

## 18.3 Monster & Progression Lead

Owns:

- Monster model
- Stats
- Level
- EXP
- Evolution
- Team system
- Monster progression

Contract với Gameplay:

    Progression Request
            ↓
    Monster/Progression System
            ↓
    Progression Result

---

## 18.4 AI & Data Lead

Owns:

- Battle AI
- AI decision-making
- AI patterns
- AI data
- Relevant gameplay data/configuration

AI không tự quyết định global Game State transition.

---

## 18.5 UI & Architecture Lead

Owns:

- JavaFX UI
- Controllers
- Application architecture
- Repository/database integration
- Persistence boundary
- UI ↔ gameplay integration

UI không trở thành source of truth cho gameplay rules.

---

# 19. Cross-System Contract

                 GAMEPLAY
                     │
          ┌──────────┼──────────┐
          │          │          │
          ▼          ▼          ▼
       BATTLE     MONSTER      WORLD
          │          │          │
          ▼          ▼          ▼
       Result     Progress    Unlock
          │          │          │
          └──────────┼──────────┘
                     ▼
                 GAMEPLAY
                     │
                     ▼
                    UI

Nguyên tắc:

1. Gameplay quyết định khi nào subsystem được gọi.
2. Subsystem owner quyết định cách subsystem hoạt động.
3. Gameplay nhận kết quả từ subsystem.
4. Gameplay dùng kết quả để quyết định state/progression tiếp theo.
5. UI phản ánh gameplay state.
6. UI gửi player intent về gameplay/application layer.
7. UI không trở thành source of truth cho gameplay rules.
8. Subsystem không được tự ý bypass Gameplay State Machine.
9. Cross-system contract phải được định nghĩa trước implementation integration.

---

# 20. State Transition Rules

Mọi transition cấp Game State phải đi qua Gameplay State Machine.

Ví dụ hợp lệ:

    Encounter System
        ↓
    Encounter Created
        ↓
    Gameplay
        ↓
    BATTLE

Ví dụ không hợp lệ:

    Encounter System
        ↓
    UI Controller
        ↓
    Battle Scene

Tương tự:

    Battle
        ↓
    BattleResult
        ↓
    Gameplay
        ↓
    Progression
        ↓
    EXPLORATION

Không được:

    Battle
        ↓
    UI
        ↓
    EXPLORATION

UI chỉ phản ánh transition đã được Gameplay quyết định.

---

# 21. MVP Scope

MVP phải chứng minh được loop hoàn chỉnh:

    Start Game
        ↓
    Explore
        ↓
    Encounter
        ↓
    Battle
        ↓
    Win
        ↓
    Receive Reward
        ↓
    Gain Progress
        ↓
    Continue Exploring
        ↓
    Reach Gym
        ↓
    Gym Battle
        ↓
    Progress
        ↓
    Reach Boss
        ↓
    Boss Battle
        ↓
    Unlock New Region
        ↓
    Explore

Nếu loop này chưa hoạt động end-to-end thì các feature phụ không được xem là ưu tiên cao hơn core gameplay.

---

# 22. Gameplay Definition of Done

Gameplay MVP được xem là đạt khi:

- Player có thể bắt đầu game.
- Player có thể đi vào exploration.
- Exploration có thể tạo encounter.
- Encounter có thể tạo Battle Request.
- Battle có thể khởi tạo từ Battle Request.
- Battle có thể trả về Battle Result.
- Victory tạo reward flow.
- Reward có thể tạo progression.
- Progression có thể trigger evolution khi đủ điều kiện.
- Player có thể tiếp tục exploration.
- Player có thể tiếp cận Gym.
- Gym battle có thể trả về kết quả.
- Gym victory tạo progression.
- Player có thể tiếp cận Boss khi đủ điều kiện.
- Boss battle có thể trả về kết quả.
- Boss victory unlock được region/progression tiếp theo.
- Các state transition không bị subsystem khác tự ý bypass.
- Battle, Monster, AI và UI không tự định nghĩa lại global gameplay flow.
- `GameState` không phụ thuộc trực tiếp vào JavaFX hoặc persistence implementation.

---

# 23. Next Step — Cross-System Contracts

Sau khi Gameplay State Machine được thống nhất, Game & Gameplay Lead cần phối hợp với toàn bộ các Lead để chốt:

1. Gameplay ↔ Battle
2. Gameplay ↔ Monster/Progression
3. Gameplay ↔ World/Map
4. Gameplay ↔ AI
5. Gameplay ↔ UI
6. Gameplay ↔ Persistence

Thứ tự đề xuất:

    1. Gameplay ↔ Battle
              ↓
    2. Gameplay ↔ Monster/Progression
              ↓
    3. Gameplay ↔ World/Map
              ↓
    4. Gameplay ↔ AI
              ↓
    5. Gameplay ↔ UI
              ↓
    6. Gameplay ↔ Persistence

Chỉ sau khi các contract này rõ ràng mới bắt đầu implementation integration để tránh mỗi subsystem tạo một interpretation khác nhau của gameplay.