# MONSTERRA Gameplay Specification

## 1. Purpose

Tài liệu này định nghĩa gameplay scope và các quy tắc kết nối giữa các subsystem của MONSTERRA.

Game & Gameplay Lead chịu trách nhiệm định nghĩa:

- Core Gameplay Loop
- Gameplay State Flow
- Encounter Flow
- Reward Flow
- Player Progression Flow
- World/Region Progression
- Gym/Boss Progression
- Boundary giữa các gameplay subsystem

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

---

## 4. Gameplay States

MVP gameplay state machine:

MAIN_MENU
NEW_GAME
TUTORIAL
EXPLORATION
ENCOUNTER
BATTLE
REWARD
EVOLUTION
GYM
BOSS
PAUSE
GAME_OVER

Core flow:

MAIN_MENU
    → NEW_GAME
    → TUTORIAL
    → EXPLORATION
    → ENCOUNTER
    → BATTLE
    → REWARD
    → EVOLUTION
    → EXPLORATION

Gym/Boss flow:

EXPLORATION
    → GYM
    → BATTLE
    → REWARD / PROGRESS
    → BOSS
    → BATTLE
    → REWARD / PROGRESS
    → NEW_REGION
    → EXPLORATION

---

## 5. Core Rules

- Team maximum: 6 Monster
- Battle is turn-based
- EXP is awarded after victory
- Monster may level up
- Evolution occurs when conditions are satisfied
- Gym victory unlocks progression
- Boss victory unlocks the next major region

Các công thức chi tiết chưa được chốt:

- EXP formula
- Damage formula
- Encounter rate
- Level cap
- Evolution conditions
- Reward amounts
- Gym/Boss configuration
- AI decision formula

---

## 6. Encounter Flow

Explore
   ↓
Encounter Trigger
   ↓
Create Encounter
   ↓
Battle

Encounter System chịu trách nhiệm:

- Detect encounter
- Xác định encounter type
- Chọn encounter data phù hợp
- Khởi tạo battle request

Encounter System không chịu trách nhiệm:

- Damage
- Skill execution
- Turn order
- EXP calculation
- Monster progression

---

## 7. Battle Flow

Encounter
   ↓
Battle Start
   ↓
Battle Engine
   ↓
Battle Result
   ↓
Reward

Battle System Lead sở hữu:

- Battle Engine
- Turn system
- Action execution
- Skill execution
- Damage calculation
- Battle effects/status
- Battle state
- Battle result

Game & Gameplay Lead chỉ định nghĩa lifecycle và contract với Battle System.

Contract:

Gameplay → Battle Request
Battle   → Battle Result

---

## 8. Reward Flow

Battle Result
    ↓
EXP
Money
Item / Other Reward
    ↓
Progression Check
    ↓
Evolution Check
    ↓
Continue Gameplay

Gameplay System chịu trách nhiệm điều phối reward flow.

Monster/Progression System xử lý Monster progression.

Inventory/System khác xử lý resource tương ứng.

---

## 9. Evolution Flow

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

Gameplay System chịu trách nhiệm trigger evolution check tại đúng lifecycle point và chuyển game sang EVOLUTION state khi cần.

---

## 10. Gym Progression

Explore
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

Gym victory có thể thay đổi:

- Badge state
- Available areas
- Available progression
- Access to subsequent content

---

## 11. Boss Progression

Explore
   ↓
Boss Access
   ↓
Boss Battle
   ↓
Victory
   ↓
Reward
   ↓
Major Progress Update
   ↓
Unlock New Region

Boss Battle vẫn sử dụng Battle System của Battle System Lead.

Boss AI/behavior thuộc AI & Data Lead.

Gameplay System chịu trách nhiệm:

- Boss access condition
- Boss encounter lifecycle
- Victory consequence
- Region unlock

---

## 12. World / Region Progression

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

Một major progression milestone có thể unlock content/region tiếp theo.

World/map implementation thuộc subsystem tương ứng.

---

## 13. Ownership

### Game & Gameplay Lead

Owns:

- Core gameplay loop
- Gameplay states
- State transitions
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
- Damage formula
- Skill execution
- Monster stat implementation
- EXP formula implementation
- Evolution data implementation
- AI decision-making
- JavaFX rendering
- Persistence implementation

### Battle System Lead

Owns:

- Battle Engine
- Turn system
- Action execution
- Skill execution
- Damage calculation
- Battle effects/status
- Battle state
- Battle result

### Monster & Progression Lead

Owns:

- Monster model
- Stats
- Level
- EXP
- Evolution
- Team system
- Monster progression

### AI & Data Lead

Owns:

- Battle AI
- AI decision-making
- AI patterns
- AI data
- Relevant gameplay data/configuration

### UI & Architecture Lead

Owns:

- JavaFX UI
- Controllers
- Application architecture
- Repository/database integration
- Persistence boundary
- UI ↔ gameplay integration

---

## 14. Cross-System Contract

                 GAMEPLAY
                     │
           ┌─────────┼─────────┐
           │         │         │
           ▼         ▼         ▼
        BATTLE    MONSTER      WORLD
           │         │         │
           ▼         ▼         ▼
        Result    Progress   Unlock
           │         │         │
           └─────────┼─────────┘
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
6. UI không trở thành source of truth cho gameplay rules.

---

## 15. MVP Scope

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

Nếu loop này chưa hoạt động end-to-end thì các feature phụ không được xem là ưu tiên cao hơn core gameplay.

---

## 16. Gameplay Definition of Done

Gameplay MVP được xem là đạt khi:

- Player có thể bắt đầu game.
- Player có thể đi vào exploration.
- Exploration có thể tạo encounter.
- Encounter có thể khởi tạo battle.
- Battle có thể trả về kết quả cho Gameplay.
- Victory tạo reward flow.
- Reward có thể tạo progression.
- Progression có thể trigger evolution khi đủ điều kiện.
- Player có thể tiếp tục exploration.
- Player có thể tiếp cận Gym.
- Gym victory tạo progression.
- Player có thể tiếp cận Boss khi đủ điều kiện.
- Boss victory unlock được region/progression tiếp theo.
- Các state transition không bị subsystem khác tự ý bypass.
- Battle, Monster, AI và UI không tự định nghĩa lại global gameplay flow.

---

## 17. Next Step

Sau khi Gameplay Spec được thống nhất, Game & Gameplay Lead cần phối hợp với toàn bộ các Lead để chốt:

1. Gameplay ↔ Battle
2. Gameplay ↔ Monster/Progression
3. Gameplay ↔ World/Map
4. Gameplay ↔ AI
5. Gameplay ↔ UI
6. Gameplay ↔ Persistence

Chỉ sau khi các contract này rõ ràng mới bắt đầu implementation để tránh mỗi subsystem tạo một interpretation khác nhau của gameplay.