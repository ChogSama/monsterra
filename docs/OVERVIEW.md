# MONSTERRA — OOP Monster Battle RPG

> **Đề tài:** Xây dựng game nhập vai chiến thuật thu thập, huấn luyện và tiến hóa quái thú bằng Java và lập trình hướng đối tượng.

---

# CHƯƠNG 1. GIỚI THIỆU ĐỀ TÀI

## 1.1 Đặt vấn đề

Trong quá trình học lập trình hướng đối tượng, sinh viên thường được tiếp cận với các khái niệm như lớp, đối tượng, đóng gói, kế thừa, đa hình, trừu tượng hóa, interface, generic, exception và các nguyên lý thiết kế phần mềm. Tuy nhiên, nếu chỉ thực hành thông qua những bài toán đơn giản như quản lý sinh viên, quản lý thư viện hoặc quản lý nhân viên, việc hình dung mối quan hệ giữa nhiều đối tượng trong một hệ thống lớn có thể gặp nhiều hạn chế.

Game nhập vai chiến thuật là một bài toán phù hợp để áp dụng lập trình hướng đối tượng vì một game có rất nhiều thực thể và hành vi tương tác với nhau. Ví dụ, người chơi có Trainer, Trainer sở hữu nhiều Monster, Monster có Type, Skill, Stats, Item và Evolution. Các Monster có thể kế thừa từ một lớp cơ sở, đồng thời triển khai các interface khác nhau. Battle Engine có thể tương tác với các đối tượng Monster thông qua abstraction và polymorphism.

Từ đó, đề tài **MONSTERRA — OOP Monster Battle RPG** được xây dựng với mục tiêu tạo ra một game RPG trong đó người chơi khám phá thế giới, thu thập quái thú, huấn luyện, tiến hóa và tham gia các trận chiến chiến thuật.

Hệ thống được thiết kế xoay quanh các cơ chế:

- Khám phá bản đồ RPG.
- Thu thập Monster.
- Xây dựng đội hình.
- Chiến đấu theo lượt.
- Hệ thống Type và khắc chế.
- Kỹ năng và hiệu ứng trạng thái.
- Level và kinh nghiệm.
- Tiến hóa Monster.
- Gym Battle.
- Boss Battle.
- Item.
- Save/Load.
- AI điều khiển đối thủ.

Đề tài đồng thời được sử dụng như một môi trường thực tế để vận dụng các kiến thức của môn Lập trình hướng đối tượng từ Chương 1 đến Chương 13.

---

## 1.2 Mục tiêu và phạm vi đề tài

### 1.2.1. Mục tiêu

Đề tài hướng tới các mục tiêu chính:

1. Xây dựng một game RPG hoàn chỉnh ở mức prototype có thể chơi được.
2. Áp dụng các nguyên lý lập trình hướng đối tượng vào một hệ thống có quy mô tương đối lớn.
3. Thiết kế hệ thống class có tính mở rộng.
4. Xây dựng hệ thống chiến đấu theo lượt.
5. Xây dựng hệ thống Type có quan hệ khắc chế.
6. Xây dựng hệ thống Level, EXP và Evolution.
7. Xây dựng hệ thống Gym và Boss.
8. Xây dựng bản đồ RPG có thể khám phá.
9. Xây dựng hệ thống lưu và tải tiến trình.
10. Áp dụng JavaFX để xây dựng giao diện.
11. Áp dụng UML trong quá trình phân tích và thiết kế.
12. Xây dựng AI chiến đấu không dựa trên sinh văn bản.

### 1.2.2. Phạm vi

Phiên bản prototype tập trung vào:

- Một người chơi.
- Một thế giới game có bản đồ mở ở mức giới hạn.
- Một số khu vực khám phá.
- Một số loại Monster.
- Một số Type.
- Hệ thống bắt/thu thập Monster.
- Hệ thống Battle.
- Hệ thống Skill.
- Hệ thống Evolution.
- Một số Gym Leader.
- Một Boss cuối.
- Inventory.
- Save/Load.
- Settings.
- AI Battle.

Các chức năng như multiplayer, PvP trực tuyến, marketplace, cloud save và hệ thống mạng xã hội không nằm trong phạm vi phiên bản đầu tiên.

---

## 1.3 Định hướng giải pháp

Hệ thống được định hướng theo kiến trúc hướng đối tượng, trong đó các thành phần của game được mô hình hóa thành các lớp độc lập.

Một số đối tượng chính:

```text
Player
 └── Trainer
      ├── Monster
      ├── Inventory
      ├── Progress
      └── Achievement

Monster
 ├── Stats
 ├── Type
 ├── Skill
 ├── Evolution
 └── StatusEffect

Battle
 ├── BattleEngine
 ├── BattleAction
 ├── DamageCalculator
 └── BattleAI

World
 ├── Map
 ├── Area
 ├── NPC
 ├── WildMonster
 └── Gym
```

Hệ thống Battle sử dụng một Battle Engine độc lập với giao diện. Điều này cho phép thay đổi giao diện mà không cần viết lại toàn bộ logic chiến đấu.

### Định hướng AI

AI được tích hợp vào đối thủ trong Battle.

AI sẽ:

1. Theo dõi hành động của người chơi.
2. Ghi nhận Type Monster thường được sử dụng.
3. Ghi nhận Skill thường sử dụng.
4. Phân tích xu hướng lựa chọn.
5. Ước lượng hành động tiếp theo.
6. Lựa chọn Monster/Skill phù hợp để phản ứng.

Ví dụ:

```text
Player frequently uses:
Fire Skill     65%
Normal Skill   20%
Water Skill    15%

AI prediction:
Next action = Fire Skill

AI response:
Switch to Water-resistant Monster
```

Đây là **game AI/decision-making**, không phải Generative AI.

---

## 1.4 Bố cục đồ án

Đồ án gồm 6 chương chính:

- **Chương 1:** Giới thiệu đề tài.
- **Chương 2:** Khảo sát và phân tích yêu cầu.
- **Chương 3:** Nền tảng lý thuyết và công nghệ.
- **Chương 4:** Phân tích thiết kế, triển khai và đánh giá hệ thống.
- **Chương 5:** Các giải pháp và đóng góp nổi bật.
- **Chương 6:** Kết luận và hướng phát triển.

Ngoài ra đồ án gồm tài liệu tham khảo và phụ lục đặc tả Use Case.

---

# CHƯƠNG 2. KHẢO SÁT VÀ PHÂN TÍCH YÊU CẦU

## 2.1 Khảo sát hiện trạng

Các game RPG thu thập quái thú thường có một số cơ chế đặc trưng:

- Người chơi điều khiển một nhân vật.
- Khám phá thế giới.
- Gặp Monster.
- Thu thập Monster.
- Huấn luyện Monster.
- Chiến đấu.
- Tăng Level.
- Tiến hóa.
- Đánh các Boss/Gym.
- Mở khóa khu vực mới.

Những cơ chế này có tính tương tác cao và tạo ra nhiều quan hệ giữa các đối tượng.

Đối với mục tiêu học tập của môn Lập trình hướng đối tượng, đây là một bài toán có ưu điểm:

- Nhiều lớp và đối tượng.
- Quan hệ kế thừa tự nhiên.
- Có thể sử dụng abstract class.
- Có thể sử dụng interface.
- Có nhiều tình huống polymorphism.
- Có nhiều collection.
- Có exception.
- Có thể thiết kế UML class diagram lớn.
- Có thể áp dụng SOLID và Design Pattern.

---

# 2.2 Tổng quan chức năng

Hệ thống gồm các nhóm chức năng:

### Nhóm 1 — Quản lý Game

- New Game.
- Continue.
- Save Game.
- Auto Save.
- Settings.
- Exit.

### Nhóm 2 — Quản lý Player

- Tạo Profile.
- Đặt tên Trainer.
- Xem thông tin Trainer.
- Xem tiến trình.
- Xem đội Monster.

### Nhóm 3 — Khám phá

- Di chuyển trên Map.
- Vào Area.
- Gặp NPC.
- Gặp Wild Monster.
- Thu thập Item.
- Tương tác với môi trường.

### Nhóm 4 — Monster

- Xem Monster.
- Thu thập Monster.
- Tăng Level.
- Học Skill.
- Thay đổi đội hình.
- Tiến hóa.

### Nhóm 5 — Battle

- Battle với Wild Monster.
- Battle với Trainer.
- Battle với Gym Leader.
- Battle với Boss.
- Chọn Skill.
- Đổi Monster.
- Sử dụng Item.
- Tính Damage.
- Áp dụng Type Advantage.
- Áp dụng Status Effect.

### Nhóm 6 — AI

- Phân tích hành động người chơi.
- Dự đoán xu hướng.
- Chọn hành động.
- Chọn Monster.
- Chọn Skill.
- Điều chỉnh độ khó.

---

# 2.2.1 Biểu đồ Use Case tổng quát

Actor chính:

```text
                    ┌────────────────────┐
                    │       PLAYER       │
                    └─────────┬──────────┘
                              │
       ┌──────────────────────┼───────────────────────┐
       │                      │                       │
       ▼                      ▼                       ▼
  Manage Game            Explore World           Manage Monster
       │                      │                       │
       ├── New Game           ├── Move               ├── View
       ├── Continue           ├── Explore            ├── Train
       ├── Save               ├── NPC                ├── Skill
       └── Settings           └── Wild Monster       └── Evolution
                              │
                              ▼
                           Battle
                              │
                  ┌───────────┼───────────┐
                  ▼           ▼           ▼
                Wild       Gym Leader    Boss
```

Actor phụ:

```text
SYSTEM
 ├── Auto Save
 ├── Load Data
 └── Game State Management

BATTLE AI
 ├── Observe Player
 ├── Analyze Pattern
 ├── Predict Action
 └── Select Counter Strategy
```

---

# 2.2.2 Biểu đồ Use Case phân rã

## Use Case: Battle

```text
                    Battle
                      │
       ┌──────────────┼───────────────┐
       │              │               │
       ▼              ▼               ▼
 Select Monster   Select Action   Check Battle State
                       │
             ┌─────────┼─────────┐
             ▼         ▼         ▼
           Skill      Item      Switch
             │
             ▼
       Calculate Damage
             │
             ▼
       Apply Type Effectiveness
             │
             ▼
       Apply Status Effect
             │
             ▼
        Update HP/EXP
             │
             ▼
        Check Winner
```

## Use Case: Monster Evolution

```text
Evolution
    │
    ├── Check Level
    ├── Check Item
    ├── Check Condition
    ├── Show Evolution Preview
    ├── Confirm Evolution
    └── Update Monster Form
```

## Use Case: Save Game

```text
Save Game
   │
   ├── Collect Player State
   ├── Collect Monster State
   ├── Collect Inventory
   ├── Collect World Progress
   ├── Serialize Data
   └── Store Database
```

---

# 2.2.3 Quy trình nghiệp vụ

## User Flow

Luồng chính của ứng dụng:

```text
                  ┌──────────────┐
                  │ Main Screen  │
                  └──────┬───────┘
                         │
              ┌──────────┼──────────┐
              │                     │
              ▼                     ▼
        New Game / Continue      Settings
              │                     │
              ▼                     ▼
        Open RPG Map            Game Options
              │
       ┌──────┴──────┐
       │             │
       ▼             ▼
    New Game      Continue
       │             │
       ▼             │
   Tutorial          │
       │             │
       └──────┬──────┘
              ▼
       Free Exploration
              │
       ┌──────┼──────┐
       ▼      ▼      ▼
      NPC    Wild   Items
             Monster
               │
               ▼
             Battle
               │
       ┌───────┴────────┐
       ▼                ▼
     Victory           Defeat
       │                │
       ▼                ▼
      EXP             Retry
       │
       ▼
   Level / Evolution
       │
       ▼
    Continue
```

---

# 2.3 Đặc tả chức năng

## 2.3.1 Đặc tả Use Case: New Game

| Thành phần     | Nội dung                   |
| -------------- | -------------------------- |
| Use Case       | New Game                   |
| Actor          | Player                     |
| Mục tiêu       | Tạo một game mới           |
| Tiền điều kiện | Không có profile đang chơi |
| Hậu điều kiện  | Profile mới được tạo       |
| Trigger        | Player chọn New Game       |

### Main Flow

1. Player chọn **New Game**.
2. Hệ thống yêu cầu nhập tên Trainer.
3. Player nhập tên.
4. Hệ thống tạo Profile.
5. Hệ thống khởi tạo đội Monster ban đầu.
6. Hệ thống khởi tạo Inventory.
7. Hệ thống đưa Player vào Tutorial.
8. Sau Tutorial, Map RPG được mở.
9. Hệ thống bắt đầu Auto Save.

### Alternative Flow

Nếu tên Trainer không hợp lệ:

```text
InvalidTrainerNameException
```

Hệ thống yêu cầu nhập lại.

---

# 2.3.2 Đặc tả Use Case: Continue Game

| Thành phần     | Nội dung                  |
| -------------- | ------------------------- |
| Use Case       | Continue Game             |
| Actor          | Player                    |
| Mục tiêu       | Tiếp tục game đã lưu      |
| Tiền điều kiện | Có save data              |
| Hậu điều kiện  | Game State được khôi phục |
| Trigger        | Player chọn Continue      |

### Main Flow

1. Player chọn Continue.
2. Hệ thống kiểm tra save data.
3. Hệ thống đọc dữ liệu Profile.
4. Hệ thống khôi phục Player.
5. Hệ thống khôi phục Monster.
6. Hệ thống khôi phục Inventory.
7. Hệ thống khôi phục World Progress.
8. Hệ thống mở Map tại vị trí đã lưu.

### Alternative Flow

Nếu save data bị lỗi:

```text
SaveDataCorruptedException
```

Hệ thống thông báo lỗi và cho phép người chơi quay lại Main Menu.

---

# 2.4 Yêu cầu phi chức năng

## Hiệu năng

- Giao diện phản hồi nhanh với thao tác người dùng.
- Battle không gây treo giao diện.
- Auto Save không làm gián đoạn gameplay.
- Game có thể chạy ổn định trên máy tính cấu hình phổ thông.

## Khả năng mở rộng

Hệ thống phải cho phép dễ dàng:

- Thêm Monster.
- Thêm Type.
- Thêm Skill.
- Thêm Map.
- Thêm Gym.
- Thêm Item.
- Thêm Evolution.
- Thêm Boss.

mà không phải sửa toàn bộ hệ thống.

## Khả năng bảo trì

- Tách logic Battle khỏi GUI.
- Tách Database khỏi Business Logic.
- Tách AI khỏi Battle UI.
- Sử dụng package rõ ràng.
- Áp dụng nguyên tắc Single Responsibility.

## Tính nhất quán

Các phép tính Damage, EXP, Level và Evolution phải cho kết quả nhất quán.

## Tính tin cậy

- Không mất save data trong quá trình lưu bình thường.
- Có Auto Save.
- Có kiểm tra dữ liệu khi Load.
- Có xử lý Exception.

## Tính dễ sử dụng

- Giao diện trực quan.
- Các nút có chức năng rõ ràng.
- Battle hiển thị đầy đủ thông tin.
- Người chơi có thể xem Type Advantage.

---

# CHƯƠNG 3. NỀN TẢNG LÝ THUYẾT VÀ CÔNG NGHỆ SỬ DỤNG

## 3.1 Lập trình hướng đối tượng

Hệ thống áp dụng bốn nguyên lý chính:

### Encapsulation

Dữ liệu Monster được che giấu bên trong class.

```java
public class Monster {
    private String name;
    private int hp;
    private int level;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
```

### Abstraction

Các hành vi chung được định nghĩa trong abstract class.

```java
public abstract class Monster {
    public abstract void attack();
}
```

### Inheritance

Các Monster cụ thể kế thừa class cơ sở.

```text
Monster
 ├── FlameMonster
 ├── AquaMonster
 ├── LeafMonster
 └── StormMonster
```

### Polymorphism

Battle Engine làm việc với `Monster` thay vì phụ thuộc vào từng Monster cụ thể.

```java
Monster monster = new FlameMonster();
monster.attack();
```

---

# 3.2 Interface

Một số hành vi có thể được biểu diễn bằng interface.

```java
public interface Evolvable {
    Monster evolve();
}
```

```java
public interface BattleCapable {
    void attack();
}
```

---

# 3.3 Generic

Collection được sử dụng xuyên suốt hệ thống.

```java
List<Monster> team;
Map<String, Item> inventory;
Set<String> achievements;
```

Có thể xây dựng Generic Repository:

```java
public interface Repository<T> {
    T findById(int id);
    void save(T entity);
    void delete(T entity);
}
```

---

# 3.4 Exception Handling

Các exception riêng:

```text
GameException
 ├── SaveDataException
 ├── InvalidMonsterException
 ├── InvalidTrainerException
 ├── InvalidBattleActionException
 └── EvolutionException
```

---

# 3.5 UML

Các biểu đồ được sử dụng:

- Use Case Diagram.
- Class Diagram.
- Sequence Diagram.
- Activity Diagram.
- State Diagram.

---

# 3.6 JavaFX

JavaFX được sử dụng để xây dựng GUI.

Các thành phần:

- Scene.
- Stage.
- Button.
- Label.
- ImageView.
- ProgressBar.
- GridPane.
- VBox/HBox.
- Canvas.
- Animation.

---

# 3.7 Cơ sở dữ liệu

Database được sử dụng để lưu:

- Player Profile.
- Monster.
- Monster Stats.
- Skill.
- Inventory.
- World Progress.
- Gym Progress.
- Save Slot.
- Battle Statistics.

Có thể sử dụng **SQLite** cho prototype vì dễ triển khai và không yêu cầu server.

---

# 3.8 Game AI

AI được thiết kế dưới dạng **Adaptive Battle AI**.

Không sử dụng LLM để sinh hội thoại.

AI nhận input:

```text
Player Monster
Player Type
Player HP
Previous Actions
Used Skills
Battle Turn
Opponent Monster
Type Effectiveness
```

Sau đó tính điểm cho các hành động:

```text
Action Score =
    Type Advantage
  + Damage Potential
  + Survival Probability
  + Player Pattern Prediction
```

Ví dụ:

```text
Player Pattern:

Turn 1 → Fire Skill
Turn 2 → Fire Skill
Turn 3 → Switch
Turn 4 → Fire Skill

AI:
Fire usage = 75%

Prediction:
Player likely uses Fire

Decision:
Switch to Water-resistant Monster
```

AI vẫn nằm trong hệ thống OOP:

```text
BattleAI
 ├── PatternAnalyzer
 ├── ActionEvaluator
 ├── TypeAnalyzer
 └── StrategySelector
```

---

# CHƯƠNG 4. PHÂN TÍCH THIẾT KẾ, TRIỂN KHAI VÀ ĐÁNH GIÁ HỆ THỐNG

# 4.1 Thiết kế kiến trúc

## 4.1.1 Lựa chọn kiến trúc phần mềm

Đề tài sử dụng kiến trúc phân lớp kết hợp tư tưởng MVC.

```text
┌───────────────────────────────┐
│          JavaFX UI            │
│           View                │
└───────────────┬───────────────┘
                │
┌───────────────▼───────────────┐
│         Controller            │
│      Input / Event            │
└───────────────┬───────────────┘
                │
┌───────────────▼───────────────┐
│       Game / Service          │
│      Business Logic           │
└───────────────┬───────────────┘
                │
       ┌────────┴────────┐
       ▼                 ▼
┌──────────────┐  ┌──────────────┐
│   AI Engine  │  │ Battle Engine│
└──────────────┘  └──────────────┘
                │
┌───────────────▼───────────────┐
│       Repository / DAO        │
└───────────────┬───────────────┘
                │
┌───────────────▼───────────────┐
│          Database             │
└───────────────────────────────┘
```

---

# 4.1.2 Thiết kế tổng quan

Hệ thống được chia thành các module:

```text
MONSTERRA
│
├── UI
├── Game
├── Player
├── Monster
├── Battle
├── AI
├── World
├── Item
├── Evolution
├── Save
├── Database
└── Utility
```

---

# 4.1.3 Thiết kế chi tiết gói

Đề xuất package:

```text
com.monsterra
│
├── app
│   └── Main.java
│
├── controller
│   ├── MainController.java
│   ├── MapController.java
│   ├── BattleController.java
│   └── SettingsController.java
│
├── model
│   ├── player
│   ├── monster
│   ├── battle
│   ├── item
│   ├── world
│   └── gym
│
├── service
│   ├── GameService.java
│   ├── BattleService.java
│   ├── EvolutionService.java
│   └── SaveService.java
│
├── ai
│   ├── BattleAI.java
│   ├── PatternAnalyzer.java
│   └── StrategySelector.java
│
├── repository
│   ├── PlayerRepository.java
│   ├── MonsterRepository.java
│   └── SaveRepository.java
│
├── exception
│
├── util
│
└── database
```

---

# 4.2 Thiết kế chi tiết

# 4.2.1 Thiết kế giao diện

## Màn hình 1 — Main Screen

```text
╔══════════════════════════════════╗
║            MONSTERRA             ║
║                                  ║
║         [ NEW GAME ]             ║
║                                  ║
║         [ CONTINUE ]             ║
║                                  ║
║         [ SETTINGS ]             ║
║                                  ║
║           [ EXIT ]               ║
╚══════════════════════════════════╝
```

---

## Màn hình 2 — New Game

```text
╔══════════════════════════════════╗
║         CREATE TRAINER           ║
║                                  ║
║ Name: [________________]         ║
║                                  ║
║ Choose Starter:                  ║
║                                  ║
║  🔥 Emberling   💧 Aquaffin      ║
║  🌿 Leafling    ⚡ Voltkit       ║
║                                  ║
║            [ START ]              ║
╚══════════════════════════════════╝
```

---

# Màn hình 3 — Map

```text
┌───────────────────────────────────────────┐
│ HP ████████      TEAM 3/6       Lv. 12    │
├───────────────────────────────────────────┤
│                                           │
│        🌲 Forest                          │
│             ↓                             │
│     🏠──────🌳──────🌳                    │
│      │                                  │ │
│      │             🏔️                   │ │
│      └───────────────🏟️ GYM              │
│                                           │
│                 Player                    │
│                   🧍                     │
│                                           │
├───────────────────────────────────────────┤
│ [TEAM] [BAG] [MAP] [SAVE] [MENU]         │
└───────────────────────────────────────────┘
```

---

# Màn hình Battle

```text
╔══════════════════════════════════════════╗
║              GYM BATTLE                  ║
║                                          ║
║ Enemy: Stormfang Lv. 24                  ║
║ HP ███████████░░                         ║
║                                          ║
║              🐺                          ║
║                                          ║
║                          🦎              ║
║ Player: Flametail Lv. 22                 ║
║ HP ████████░░                            ║
║                                          ║
║ [ATTACK] [SKILL] [SWITCH] [ITEM]         ║
╚══════════════════════════════════════════╝
```

---

# Màn hình Evolution

```text
        BEFORE                 AFTER

      🔥 Flametail   ─────→   🔥🔥 Infernoon

           [ Evolution! ]

      [ CANCEL ]       [ EVOLVE ]
```

---

# 4.2.2 Thiết kế lớp

## Monster

```java
public abstract class Monster {

    private int id;
    private String name;
    private int level;
    private int experience;

    private Stats stats;
    private List<Type> types;
    private List<Skill> skills;

    public abstract void performPassiveEffect();

    public void gainExperience(int exp) {
        experience += exp;
    }
}
```

---

## Type

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

Type Chart:

```text
             FIRE WATER GRASS ELECTRIC
FIRE           1    0.5   2      1
WATER          2     1   0.5     1
GRASS         0.5    2    1      1
ELECTRIC       1     2    0.5    1
```

Có thể mở rộng bảng khắc chế thành một `TypeEffectivenessMatrix`.

---

## Skill

```java
public class Skill {

    private String name;
    private Type type;
    private int power;
    private int accuracy;
    private int manaCost;

    public void execute(Monster attacker,
                        Monster defender) {
        // ...
    }
}
```

---

## Battle

```java
public class Battle {

    private Monster playerMonster;
    private Monster enemyMonster;

    private BattleState state;

    public void executeTurn(BattleAction action) {
        // ...
    }
}
```

---

## Evolution

```java
public class EvolutionRule {

    private int requiredLevel;
    private Item requiredItem;
    private Monster evolutionTarget;

    public boolean canEvolve(Monster monster) {
        return monster.getLevel() >= requiredLevel;
    }
}
```

---

## Gym

```java
public class Gym {

    private String name;
    private GymLeader leader;
    private List<Monster> team;
    private Badge reward;

    public Battle startBattle(Player player) {
        // ...
    }
}
```

---

# 4.2.3 Thiết kế cơ sở dữ liệu

Các bảng chính:

### PLAYER

```text
PLAYER
-------------------------
id
name
money
current_area
pos_x
pos_y
created_at
updated_at
```

### MONSTER

```text
MONSTER
-------------------------
id
player_id
species_id
nickname
level
experience
hp
```

### SPECIES

```text
SPECIES
-------------------------
id
name
base_hp
base_attack
base_defense
base_speed
```

### SKILL

```text
SKILL
-------------------------
id
name
type
power
accuracy
mana_cost
```

### MONSTER_SKILL

```text
MONSTER_SKILL
-------------------------
monster_id
skill_id
slot
```

### INVENTORY

```text
INVENTORY
-------------------------
player_id
item_id
quantity
```

### GYM_PROGRESS

```text
GYM_PROGRESS
-------------------------
player_id
gym_id
completed
completed_at
```

### SAVE_GAME

```text
SAVE_GAME
-------------------------
id
player_id
save_slot
save_time
game_state
```

---

# 4.3 Xây dựng ứng dụng

# 4.3.1 Thư viện và công cụ sử dụng

| Công nghệ               | Mục đích                 |
| ----------------------- | ------------------------ |
| Java                    | Ngôn ngữ lập trình       |
| JavaFX                  | GUI                      |
| SQLite                  | Database                 |
| JDBC                    | Kết nối Database         |
| Maven                   | Quản lý dependency/build |
| Git                     | Version Control          |
| JUnit                   | Unit Test                |
| UML Tool                | Phân tích thiết kế       |
| IntelliJ IDEA / VS Code | IDE                      |

---

# 4.3.2 Kết quả đạt được

Phiên bản prototype hướng tới hoàn thành:

- Main Menu.
- New Game.
- Continue.
- Settings.
- Tutorial.
- RPG Map.
- Player Profile.
- Monster Collection.
- Monster Team.
- Level System.
- EXP System.
- Skill System.
- Type System.
- Type Advantage.
- Turn-based Battle.
- Wild Battle.
- Trainer Battle.
- Gym Battle.
- Boss Battle.
- Evolution.
- Inventory.
- Save.
- Auto Save.
- Load.
- Adaptive Battle AI.

---

# 4.3.3 Minh họa các chức năng chính

## Chức năng khám phá

Player điều khiển Trainer trên bản đồ.

Khi đi vào vùng cỏ:

```text
Random Encounter
       ↓
Generate Wild Monster
       ↓
Start Battle
```

---

## Chức năng Battle

Battle được xử lý theo lượt:

```text
Player chooses action
        ↓
Validate action
        ↓
AI chooses action
        ↓
Determine priority
        ↓
Execute faster action
        ↓
Calculate damage
        ↓
Apply status
        ↓
Update HP
        ↓
Check winner
        ↓
Reward EXP
```

---

## Type Advantage

Ví dụ:

```text
Fire → Grass
Damage × 2.0

Grass → Fire
Damage × 0.5
```

Khi người chơi sử dụng Skill:

```text
🔥 Fire Skill
       ↓
Target = 🌿 Grass Monster
       ↓
Type Advantage = 2.0x
       ↓
Critical/Normal calculation
       ↓
Final Damage
```

---

## Evolution

Monster đạt đủ điều kiện:

```text
Level >= Required Level
        +
Evolution Condition
        ↓
Evolution Available
        ↓
Player Confirmation
        ↓
Transform Monster
        ↓
Update Stats / Skills
```

---

## Gym

Gym đóng vai trò như các cột mốc của game.

Ví dụ:

```text
GYM 1 — Forest Gym
Specialty: GRASS

GYM 2 — Storm Gym
Specialty: ELECTRIC

GYM 3 — Tide Gym
Specialty: WATER

GYM 4 — Inferno Gym
Specialty: FIRE
```

Mỗi Gym có:

- Gym Leader.
- Team riêng.
- Chiến thuật riêng.
- Badge.
- Reward.
- Điều kiện mở khóa khu vực mới.

---

# 4.4 Kiểm thử

## Unit Test

Kiểm thử:

- Damage calculation.
- Type effectiveness.
- EXP calculation.
- Level up.
- Evolution condition.
- Inventory.
- Save/Load.
- AI decision.

Ví dụ:

```text
Test Fire vs Grass
Expected: 2x damage

Test Fire vs Water
Expected: 0.5x damage

Test Evolution Level < requirement
Expected: false

Test Evolution Level >= requirement
Expected: true
```

---

## Integration Test

Kiểm thử:

```text
Map
 ↓
Encounter
 ↓
Battle
 ↓
Victory
 ↓
EXP
 ↓
Level Up
 ↓
Evolution
 ↓
Save
```

---

## UI Test

Kiểm tra:

- New Game.
- Continue.
- Settings.
- Map.
- Battle.
- Inventory.
- Evolution.
- Save.

---

# 4.5 Triển khai

Game có thể được đóng gói thành Java application.

Cấu trúc:

```text
MONSTERRA/
│
├── app/
├── resources/
│   ├── images/
│   ├── sounds/
│   ├── maps/
│   └── data/
│
├── database/
│
├── src/
│
├── pom.xml
└── README.md
```

Có thể đóng gói thành:

```text
MONSTERRA.jar
```

hoặc sử dụng `jpackage` để tạo ứng dụng native cho Windows.

---

# CHƯƠNG 5. CÁC GIẢI PHÁP VÀ ĐÓNG GÓP NỔI BẬT

## 5.1 Hệ thống Type Advantage

Thay vì hard-code nhiều câu lệnh `if/else`, hệ thống sử dụng bảng quan hệ Type.

```text
Type × Type → Multiplier
```

Điều này cho phép dễ dàng thêm Type mới.

---

# 5.2 Hệ thống Evolution

Evolution không đơn giản là tăng Level.

Một Monster có thể có nhiều điều kiện:

```text
Level
+
Item
+
Time
+
Location
+
Battle Condition
```

Ví dụ:

```text
Monster A
Level >= 20
+
Fire Stone
        ↓
Evolution B
```

---

# 5.3 Adaptive Battle AI

Đây là một trong những điểm nổi bật nhất của đề tài.

AI không chỉ chọn hành động random.

Nó xây dựng một hồ sơ chiến đấu tạm thời của người chơi:

```text
Player Battle Profile

Preferred Type:
FIRE

Preferred Skill:
Flame Burst

Switch Frequency:
LOW

Aggression:
HIGH

Healing Frequency:
MEDIUM
```

Sau đó AI điều chỉnh chiến thuật.

### AI Difficulty

Có thể chia:

```text
EASY
 ↓
Random + Basic Type Advantage

NORMAL
 ↓
Type Advantage + HP Awareness

HARD
 ↓
Pattern Analysis + Prediction

BOSS
 ↓
Pattern Analysis
+
Prediction
+
Counter Strategy
+
Team Switching
```

Điều này tạo cảm giác:

> **Boss thực sự “học” cách người chơi đánh.**

---

# 5.4 Auto Save

Game tự động lưu theo chu kỳ.

Ví dụ:

```text
Every 60 seconds
        ↓
Check Game State
        ↓
Serialize
        ↓
Database
```

Ngoài ra người chơi có thể:

```text
Menu → Save Game
```

để lưu thủ công.

---

# 5.5 OOP được thể hiện trong gameplay

Một điểm đóng góp quan trọng của đề tài là **OOP không chỉ nằm trong code mà trực tiếp tạo ra gameplay**.

| Kiến thức OOP  | Ứng dụng                 |
| -------------- | ------------------------ |
| Class          | Monster, Player, Skill   |
| Object         | Monster cụ thể           |
| Encapsulation  | Stats                    |
| Abstraction    | Monster/Battle           |
| Inheritance    | Monster species          |
| Overriding     | Monster behavior         |
| Interface      | Evolvable, BattleCapable |
| Polymorphism   | Battle Engine            |
| Generic        | Repository/Collection    |
| Exception      | Game Exception           |
| Composition    | Monster → Stats/Skills   |
| Aggregation    | Player → Team            |
| UML            | Thiết kế hệ thống        |
| SOLID          | Architecture             |
| Design Pattern | Strategy/Factory/State   |

---

# 5.6 Design Pattern

Có thể áp dụng một số Design Pattern phù hợp.

## Factory Pattern

Tạo Monster:

```java
Monster monster =
    MonsterFactory.create("FLAMETAIL");
```

## Strategy Pattern

AI strategy:

```text
AggressiveStrategy
DefensiveStrategy
BalancedStrategy
CounterStrategy
```

## State Pattern

Battle State:

```text
PlayerTurn
EnemyTurn
Victory
Defeat
```

## Observer Pattern

Theo dõi Game State:

```text
GameState
   │
   ├── UI
   ├── AutoSave
   └── Achievement
```

---

# CHƯƠNG 6. KẾT LUẬN VÀ HƯỚNG PHÁT TRIỂN

# 6.1 Kết luận

Đề tài **MONSTERRA — OOP Monster Battle RPG** xây dựng một game nhập vai chiến thuật dựa trên mô hình thu thập, huấn luyện và chiến đấu với Monster.

Hệ thống cung cấp các chức năng:

- Tạo và quản lý Trainer.
- Khám phá bản đồ.
- Thu thập Monster.
- Xây dựng đội hình.
- Chiến đấu theo lượt.
- Type Advantage.
- Skill.
- Level.
- Evolution.
- Gym.
- Boss.
- Item.
- Save/Load.
- Auto Save.
- Adaptive Battle AI.

Về mặt học thuật, đề tài tạo điều kiện áp dụng hầu hết kiến thức của môn Lập trình hướng đối tượng, bao gồm:

- Abstraction.
- Encapsulation.
- Inheritance.
- Polymorphism.
- Interface.
- Generic.
- Exception.
- Collection.
- UML.
- GUI.
- Phân tích thiết kế hướng đối tượng.
- SOLID.
- Design Pattern.

Đặc biệt, việc xây dựng Battle Engine và Adaptive Battle AI giúp hệ thống có mức độ tương tác cao hơn so với các ứng dụng quản lý thông thường.

---

# 6.2 Hướng phát triển

## Giai đoạn 1

Hoàn thiện core gameplay:

- Map.
- Monster.
- Battle.
- Skill.
- Type.
- Level.
- Evolution.

## Giai đoạn 2

Mở rộng thế giới:

- Nhiều Map.
- Nhiều NPC.
- Quest.
- Gym.
- Boss.
- Item.

## Giai đoạn 3

Nâng cấp AI:

- Pattern learning.
- Dynamic difficulty.
- Boss-specific strategy.
- Team composition analysis.

## Giai đoạn 4

Mở rộng multiplayer:

```text
Player A
    ↕
Battle Server
    ↕
Player B
```

Cho phép PvP.

## Giai đoạn 5

Phát triển hệ thống Competitive:

- Ranking.
- Tournament.
- Team building.
- Battle replay.
- Battle statistics.

## Giai đoạn 6

Phát triển AI nâng cao

Có thể sử dụng Machine Learning để học từ dữ liệu trận đấu:

```text
Battle History
      ↓
Feature Extraction
      ↓
Player Behavior Model
      ↓
Prediction
      ↓
AI Strategy
```

Phiên bản nâng cao có thể sử dụng Reinforcement Learning để tìm chiến thuật tối ưu.

---

# TÀI LIỆU THAM KHẢO

1. Oracle, _Java Documentation_.
2. Oracle, _JavaFX Documentation_.
3. Oracle, _Java Collections Framework Documentation_.
4. Oracle, _JDBC Documentation_.
5. Martin Fowler, _UML Distilled_.
6. Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides, _Design Patterns: Elements of Reusable Object-Oriented Software_.
7. Robert C. Martin, _Clean Code_.
8. Robert C. Martin, _Agile Principles, Patterns, and Practices in C#_.
9. Tài liệu môn học Lập trình hướng đối tượng.
10. Tài liệu SQLite Documentation.

---

# PHỤ LỤC

# PHỤ LỤC A — ĐẶC TẢ USE CASE

## UC-01 — New Game

| Thuộc tính     | Nội dung             |
| -------------- | -------------------- |
| ID             | UC-01                |
| Tên            | New Game             |
| Actor          | Player               |
| Priority       | High                 |
| Trigger        | Chọn New Game        |
| Preconditions  | Không yêu cầu        |
| Postconditions | Profile mới được tạo |

### Main Scenario

1. Player mở Main Screen.
2. Player chọn New Game.
3. Hệ thống hiển thị Create Trainer.
4. Player nhập tên.
5. Player chọn Starter Monster.
6. Hệ thống tạo Profile.
7. Hệ thống tạo Starter Monster.
8. Hệ thống khởi tạo Inventory.
9. Hệ thống lưu Game State.
10. Hệ thống chuyển sang Tutorial.

---

# UC-02 — Continue

| Thuộc tính     | Nội dung                  |
| -------------- | ------------------------- |
| ID             | UC-02                     |
| Tên            | Continue                  |
| Actor          | Player                    |
| Priority       | High                      |
| Preconditions  | Có save data              |
| Postconditions | Game State được khôi phục |

### Main Scenario

1. Player chọn Continue.
2. Hệ thống kiểm tra save data.
3. Hệ thống Load Profile.
4. Hệ thống Load Monster.
5. Hệ thống Load Inventory.
6. Hệ thống Load World State.
7. Hệ thống khôi phục vị trí.
8. Player tiếp tục chơi.

---

# UC-03 — Explore Map

| Thuộc tính | Nội dung    |
| ---------- | ----------- |
| ID         | UC-03       |
| Tên        | Explore Map |
| Actor      | Player      |
| Priority   | High        |

### Main Scenario

1. Player di chuyển.
2. Hệ thống kiểm tra Tile.
3. Nếu Tile hợp lệ, cập nhật vị trí.
4. Nếu Tile chứa Item, hiển thị Item.
5. Nếu Tile có NPC, cho phép tương tác.
6. Nếu Tile có Wild Monster Zone, có xác suất tạo Encounter.
7. Nếu Encounter xảy ra, chuyển sang Battle.

---

# UC-04 — Wild Battle

### Main Scenario

```text
Explore
   ↓
Random Encounter
   ↓
Generate Wild Monster
   ↓
Battle Start
   ↓
Player Action
   ↓
AI Action
   ↓
Resolve Turn
   ↓
Check HP
   ↓
Victory / Defeat
```

---

# UC-05 — Use Skill

### Main Scenario

1. Player chọn Skill.
2. Hệ thống kiểm tra Skill có khả dụng.
3. Kiểm tra MP/resource.
4. Kiểm tra Accuracy.
5. Xác định Type.
6. Tính Type Multiplier.
7. Tính Damage.
8. Áp dụng Damage.
9. Áp dụng Status Effect nếu có.
10. Cập nhật Battle State.

---

# UC-06 — Switch Monster

1. Player chọn Switch.
2. Hệ thống hiển thị Team.
3. Player chọn Monster.
4. Hệ thống kiểm tra Monster còn khả năng chiến đấu.
5. Monster hiện tại rời Battle.
6. Monster mới tham gia Battle.
7. AI tiếp tục lượt nếu cần.

---

# UC-07 — Evolution

### Preconditions

Monster phải đáp ứng điều kiện Evolution.

### Main Scenario

```text
Gain EXP
   ↓
Level Up
   ↓
Check Evolution Rule
   ↓
Evolution Available?
   │
   ├── No → Continue
   │
   └── Yes
          ↓
     Show Animation
          ↓
     Player Confirm
          ↓
     Transform
          ↓
     Update Stats
          ↓
     Save
```

---

# UC-08 — Gym Battle

1. Player đến Gym.
2. Hệ thống kiểm tra điều kiện mở Gym.
3. Player chọn Challenge.
4. Gym Leader xuất hiện.
5. Hệ thống khởi tạo Boss Team.
6. Battle bắt đầu.
7. AI điều khiển Gym Leader.
8. Nếu Player thắng:
   - nhận Badge;
   - nhận Reward;
   - mở khóa khu vực mới.

9. Nếu Player thua:
   - trở về trạng thái trước Battle.

---

# UC-09 — Boss Battle

Boss là phiên bản nâng cao của Gym Battle.

Boss có:

- Team mạnh.
- Skill đặc biệt.
- AI nâng cao.
- Adaptive Strategy.
- Phase Battle.

Ví dụ:

```text
Phase 1
Normal Strategy

HP < 50%
     ↓
Phase 2
Aggressive Strategy

HP < 20%
     ↓
Final Phase
Counter Strategy
```

---

# UC-10 — Save Game

### Trigger

- Player bấm Save.
- Auto Save Timer đạt thời gian quy định.

### Main Scenario

```text
Game State
    ↓
Collect Player
    ↓
Collect Monster
    ↓
Collect Inventory
    ↓
Collect World Progress
    ↓
Serialize
    ↓
Database
    ↓
Save Complete
```

---

# UC-11 — Settings

Player có thể thay đổi:

- Volume.
- Music.
- Sound Effect.
- Fullscreen.
- Resolution.
- Animation Speed.
- Battle Speed.
- Text Speed.
- Auto Save Interval.
- Difficulty.

---

# UC-12 — Adaptive Battle AI

| Thuộc tính | Nội dung     |
| ---------- | ------------ |
| ID         | UC-12        |
| Actor      | Battle AI    |
| Input      | Battle State |
| Output     | Action       |
| Priority   | High         |

### Main Scenario

1. Battle bắt đầu.
2. AI lấy thông tin đối thủ.
3. AI lấy lịch sử hành động.
4. Pattern Analyzer phân tích hành vi.
5. Type Analyzer xác định Type Advantage.
6. Action Evaluator chấm điểm các hành động.
7. Strategy Selector chọn chiến thuật.
8. AI thực hiện hành động.
9. Kết quả Battle được ghi nhận.
10. Pattern Profile được cập nhật.

---

# Tổng quan hệ thống

```text
                         MONSTERRA
                             │
             ┌───────────────┼────────────────┐
             │               │                │
             ▼               ▼                ▼
          PLAYER            WORLD           BATTLE
             │               │                │
       ┌─────┼─────┐      Map/Area      ┌─────┼─────┐
       │     │     │         │          │     │     │
     Team  Item  Profile    NPC       Skill  Type   AI
       │                     │          │     │     │
       ▼                     ▼          ▼     ▼     ▼
    Monster              Encounter   Damage Effect Strategy
       │
   ┌───┼────┐
   ▼   ▼    ▼
 Stats Skill Evolution
             │
             ▼
          EVOLUTION
             │
             ▼
          STRONGER
          MONSTER
             │
             ▼
           GYM
             │
             ▼
           BOSS
             │
             ▼
        NEW REGION
```

## Core Gameplay Loop

```text
Explore
   ↓
Encounter
   ↓
Battle
   ↓
Win
   ↓
EXP / Item
   ↓
Level Up
   ↓
Evolution
   ↓
Build Stronger Team
   ↓
Challenge Gym
   ↓
Get Badge
   ↓
Unlock New Region
   ↓
Explore Again
```

## Data Flow

```text
             INPUT
               │
               ▼
       ┌────────────────┐
       │ Player Profile │
       └───────┬────────┘
               │
       ┌───────▼────────┐
       │   Game State    │
       │                 │
       │ • Player        │
       │ • Monster Team  │
       │ • Inventory     │
       │ • Map Position  │
       │ • Gym Progress  │
       │ • Settings      │
       └───────┬────────┘
               │
        ┌──────┴───────┐
        │              │
        ▼              ▼
   Manual Save     Auto Save
        │              │
        └──────┬───────┘
               ▼
          DATABASE
               │
               ▼
          LOAD / READ
               │
               ▼
              OUT
               │
               ▼
       Restored Game State
```

# Điểm nhấn của sản phẩm

**MONSTERRA không chỉ là một game Pokémon-like.**

Ba yếu tố chính tạo nên sản phẩm:

> **RPG Exploration + OOP Architecture + Adaptive Battle AI**

Trong đó:

- **RPG** tạo trải nghiệm chơi.
- **OOP** tạo kiến trúc kỹ thuật.
- **AI** tạo sự khác biệt trong Battle.

Đặc biệt, AI không được đưa vào chỉ để “có AI”, mà giải quyết một vấn đề thực tế của game:

> **Làm thế nào để Boss không đánh theo một kịch bản cố định mà có thể phản ứng với cách chơi của từng người?**

Đây là điểm có thể sử dụng làm **USP chính khi thuyết trình trước ban giám khảo và 10 team đánh giá chéo**.