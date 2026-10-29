# MONSTERRA — Implementation Task Breakdown

# 1. Mục tiêu và phạm vi

## 1.1. Mục tiêu

Tài liệu này chuyển các đặc tả gameplay và kiến trúc đã thống nhất thành các công việc cụ thể có thể giao cho từng thành viên triển khai song song.

Mỗi task cần xác định:

- ID và tên công việc.
- Thành viên chịu trách nhiệm.
- Các dependency cần hoàn thành trước.
- Nội dung triển khai.
- Deliverables cần bàn giao.
- Tiêu chí nghiệm thu.
- Trạng thái thực hiện.

Mục tiêu cuối cùng là tạo ra một prototype MONSTERRA có thể chạy xuyên suốt một vòng chơi, với gameplay logic độc lập với JavaFX, dữ liệu có thể lưu/khôi phục và các module được kiểm thử.

## 1.2. Nguyên tắc triển khai

1. **Contract-first:** Thống nhất model và interface dùng chung trước khi triển khai các module phụ thuộc.
2. **Separation of concerns:** UI, gameplay logic, AI và persistence có trách nhiệm riêng biệt.
3. **Testable modules:** Mỗi module có unit tests phù hợp.
4. **Incremental integration:** Tích hợp liên tục theo từng phase, không đợi hoàn thành toàn bộ mới ghép.
5. **Single source of truth:** Quy tắc gameplay được quản lý tập trung, không để nhiều module tự định nghĩa các luật khác nhau.
6. **Explicit dependencies:** Không triển khai hoặc merge code dựa trên các API chưa được thống nhất.
7. **Small deliverables:** Mỗi task cần có đầu ra cụ thể để review và nghiệm thu độc lập.

## 1.3. Phạm vi chức năng

### Core gameplay

- New Game và khởi tạo tiến trình.
- Chọn Starter Monster.
- Explore và Wild Encounter.
- Battle theo lượt.
- Damage Calculation và Type Effectiveness.
- Battle Victory/Defeat.
- EXP và Level Up.
- Team và Collection.
- Save/Load và Continue.

### Gameplay expansion

- Capture Monster.
- Inventory và Item Usage.
- Evolution.
- Gym và Badge.
- Boss Battle và Adaptive Battle AI.
- Region Unlock và Game Completion.
- Auto Save và phục hồi tiến trình.

### Kỹ thuật

- Java và JavaFX.
- Maven project structure.
- SQLite persistence.
- Repository/DAO.
- Unit tests, integration tests và UI tests.
- UML và tài liệu hướng dẫn chạy ứng dụng.

Các tính năng expansion chỉ được triển khai sau khi core vertical slice đạt tiêu chí nghiệm thu.

---

# 2. Phân chia công việc theo thành viên

## 2.1. Thành viên Game & Gameplay Lead

**Vai trò:** Chủ sở hữu gameplay specification, progression rules và gameplay acceptance.

| ID | Task | Deliverable |
|---|---|---|
| A01 | Hoàn thiện gameplay specification và quyết định còn bỏ ngỏ | Gameplay decision log |
| A02 | Chuẩn hóa gameplay flow và state transitions | State/flow diagrams |
| A03 | Định nghĩa Encounter, Victory, Defeat, Gym và Boss rules | Gameplay rules |
| A04 | Định nghĩa rewards, unlock conditions và game completion | Progression specification |
| A05 | Nghiệm thu các luồng tích hợp xuyên module | Acceptance checklist và test results |
| A06 | Quản lý backlog, dependency và vấn đề liên module | Backlog và integration issue log |

### Trách nhiệm chi tiết

**A01 — Gameplay Specification**

- Rà soát các quyết định gameplay chưa được chốt.
- Xác định các điều kiện đầu vào, đầu ra và trường hợp ngoại lệ.
- Ghi nhận các quyết định ảnh hưởng đến nhiều module.
- Đảm bảo Dev Team sử dụng cùng một bộ quy tắc.

**A02 — Gameplay Flow**

- Xác định các trạng thái và điều kiện chuyển trạng thái.
- Làm rõ luồng New Game, Explore, Battle, Reward, Save và Continue.
- Xác định hành vi khi người chơi hủy thao tác hoặc xảy ra lỗi.

**A03 — Battle và Encounter Rules**

- Xác định điều kiện bắt đầu và kết thúc Encounter.
- Xác định Victory/Defeat và các trường hợp đặc biệt.
- Xác định điều kiện tham gia Gym và Boss.
- Đảm bảo luật gameplay không phụ thuộc vào cách UI hiển thị.

**A04 — Progression Rules**

- Xác định EXP, reward và unlock conditions.
- Xác định điều kiện mở khóa vùng, Gym tiếp theo và nội dung cuối game.
- Làm rõ tương tác giữa Victory, Level Up, Evolution và Save.

**A05 — Integration Acceptance**

- Kiểm tra các luồng xuyên module.
- Đối chiếu kết quả thực tế với acceptance criteria.
- Ghi nhận lỗi, phân công người sửa và xác nhận kết quả retest.

**A06 — Backlog Management**

- Theo dõi trạng thái các task.
- Giải quyết dependency và điều phối các thay đổi shared contract.
- Đảm bảo các quyết định ảnh hưởng đến gameplay được thống nhất trước khi triển khai.

### Definition of Done

- Các quy tắc gameplay quan trọng được ghi thành tài liệu.
- Những quyết định chưa chốt có owner và trạng thái rõ ràng.
- Mỗi luồng chính có điều kiện bắt đầu, kết thúc và xử lý lỗi.
- Acceptance criteria có thể kiểm tra được.
- Các luồng tích hợp được nghiệm thu bằng test hoặc demo có thể lặp lại.

---

## 2.2. Thành viên Battle System Lead

**Vai trò:** Sở hữu Battle domain và logic chiến đấu.

| ID | Task | Deliverable |
|---|---|---|
| B01 | Xây dựng Battle models và Battle State | Battle domain models |
| B02 | Xây dựng Battle Engine và turn resolution | Battle Engine |
| B03 | Xây dựng Damage Calculator và Type Effectiveness | Damage module |
| B04 | Xử lý Status Effect, Victory, Defeat và Battle termination | Battle resolution logic |
| B05 | Xử lý action validation và Monster switching | Action validation/switching |
| B06 | Viết unit tests cho Battle module | Battle test suite |

### Trách nhiệm chi tiết

**B01 — Battle Models**

- Định nghĩa Battle state.
- Xác định active Monster của mỗi bên.
- Quản lý các dữ liệu cần thiết cho một Battle.
- Xác định các trạng thái Battle theo contract đã thống nhất.

**B02 — Battle Engine**

- Kiểm tra tính hợp lệ của action.
- Xác định thứ tự hành động.
- Thực thi lượt đánh theo luật gameplay.
- Cập nhật trạng thái Battle thông qua các quy tắc đã định nghĩa.
- Xử lý các điều kiện kết thúc lượt.

**B03 — Damage Calculator**

- Tính damage theo các tham số đã thống nhất.
- Tính Type Effectiveness.
- Áp dụng các modifier cần thiết.
- Xử lý các trường hợp damage tối thiểu, miễn nhiễm hoặc không thể gây damage nếu có trong specification.

**B04 — Battle Resolution**

- Xác định Victory/Defeat.
- Xử lý Monster bị đánh bại.
- Kiểm tra điều kiện đổi Monster.
- Kết thúc Battle và cung cấp kết quả cho module progression.

**B05 — Action Validation và Switching**

- Từ chối action không hợp lệ.
- Kiểm tra Monster còn khả năng chiến đấu.
- Xử lý đổi Monster theo luật gameplay.
- Không để UI hoặc AI tự bỏ qua validation.

**B06 — Unit Tests**

- Kiểm thử damage và Type Effectiveness.
- Kiểm thử thứ tự lượt.
- Kiểm thử action không hợp lệ.
- Kiểm thử Victory/Defeat.
- Kiểm thử Monster switching và các trường hợp ngoại lệ.

### Definition of Done

- Battle module biên dịch và chạy độc lập với JavaFX.
- Battle Engine nhận input qua contract đã thống nhất.
- Kết quả action có thể kiểm thử và tái hiện.
- Các trường hợp thắng, thua và action không hợp lệ được xử lý.
- Unit tests cho logic cốt lõi đều pass.
- Battle module không trực tiếp truy cập database.

---

## 2.3. Thành viên Monster & Progression Lead

**Vai trò:** Sở hữu Monster domain, Team, Collection và progression logic.

| ID | Task | Deliverable |
|---|---|---|
| C01 | Xây dựng Monster, Species, Stats, Type và Skill models | Monster domain models |
| C02 | Xây dựng Team và Collection | Team/Collection services |
| C03 | Xây dựng EXP, Level Up và stat progression | Progression service |
| C04 | Xây dựng Evolution rules và Evolution Service | Evolution module |
| C05 | Chuẩn bị dữ liệu Monster/Skill mẫu và validation | Seed data và validators |
| C06 | Viết unit tests cho Monster và progression | Monster/progression test suite |

### Trách nhiệm chi tiết

**C01 — Monster Domain**

- Phân biệt dữ liệu loài Monster với trạng thái của từng cá thể.
- Xây dựng các model cho Species, Stats, Type và Skill.
- Xác định cách lưu level, EXP, HP và các dữ liệu cá thể.
- Thống nhất các ID được sử dụng giữa Battle, Progression và Persistence.

**C02 — Team và Collection**

- Quản lý danh sách Monster thuộc người chơi.
- Quản lý Team đang sử dụng.
- Kiểm tra giới hạn số lượng theo gameplay specification.
- Kiểm tra việc thêm, loại bỏ và đổi Monster.
- Ngăn trạng thái Team không hợp lệ.

**C03 — EXP và Level Up**

- Tính EXP nhận được.
- Kiểm tra điều kiện Level Up.
- Cập nhật level và chỉ số.
- Xử lý các giới hạn level và EXP.
- Cung cấp kết quả progression cho Battle reward flow.

**C04 — Evolution**

- Kiểm tra điều kiện tiến hóa.
- Xác định Species đích.
- Cập nhật dữ liệu Monster sau Evolution.
- Xử lý trường hợp không đủ điều kiện hoặc dữ liệu tiến hóa không hợp lệ.

**C05 — Data và Validation**

- Chuẩn bị dữ liệu Monster/Skill phục vụ phát triển.
- Kiểm tra ID trùng, tham chiếu không tồn tại và dữ liệu thiếu.
- Đảm bảo Battle và AI có thể sử dụng cùng một nguồn dữ liệu.

**C06 — Unit Tests**

- Kiểm thử Team và Collection.
- Kiểm thử EXP và Level Up.
- Kiểm thử giới hạn progression.
- Kiểm thử Evolution hợp lệ và không hợp lệ.
- Kiểm thử dữ liệu đầu vào không hợp lệ.

### Definition of Done

- Monster domain không phụ thuộc vào JavaFX.
- Team và Collection duy trì các invariant đã thống nhất.
- Progression logic có unit tests.
- Dữ liệu mẫu có thể được sử dụng bởi Battle và AI.
- Evolution không làm mất dữ liệu cá thể ngoài những thay đổi được quy định.
- Các service không tự ý ghi database khi chưa thông qua persistence contract.

---

## 2.4. Thành viên AI & Data Lead

**Vai trò:** Sở hữu Battle AI, Pattern Analyzer và chiến lược lựa chọn hành động.

| ID | Task | Deliverable |
|---|---|---|
| D01 | Xác định Battle observation và AI input/output contracts | AI contract |
| D02 | Xây dựng Pattern Analyzer | Player behavior analyzer |
| D03 | Xây dựng Action Evaluator | Action scoring module |
| D04 | Xây dựng Strategy Selector và difficulty profiles | Battle AI |
| D05 | Xây dựng fallback và xử lý AI không có action hợp lệ | AI fallback logic |
| D06 | Viết unit tests cho AI | AI test suite |

### Trách nhiệm chi tiết

**D01 — AI Contract**

- Xác định thông tin Battle mà AI được phép quan sát.
- Xác định định dạng action AI trả về.
- Xác định cách lấy danh sách action hợp lệ.
- Không để AI truy cập trực tiếp các thành phần nội bộ của Battle Engine.

**D02 — Pattern Analyzer**

- Ghi nhận các hành động của người chơi.
- Phân tích xu hướng sử dụng Skill hoặc Type nếu được hỗ trợ.
- Xác định cách lưu và giới hạn lịch sử quan sát.
- Không đưa ra kết luận dựa trên dữ liệu không tồn tại.

**D03 — Action Evaluator**

- Chấm điểm các action hợp lệ.
- Cân nhắc damage, Type Effectiveness và khả năng sống sót.
- Sử dụng thông tin chiến đấu được contract cho phép.
- Giữ việc đánh giá tách biệt với việc thực thi action.

**D04 — Strategy Selector**

- Xác định cách lựa chọn action theo EASY, NORMAL, HARD và BOSS.
- Áp dụng thông tin từ Pattern Analyzer khi phù hợp.
- Đảm bảo độ khó khác nhau về chiến lược hoặc cách đánh giá.
- Không sử dụng Generative AI như một dependency bắt buộc.

**D05 — Fallback**

- Xử lý khi không có action hợp lệ được cung cấp.
- Tránh vòng lặp chọn action vô hạn.
- Trả về kết quả lỗi hoặc fallback theo contract.
- Không tự tạo action trái với luật gameplay.

**D06 — Unit Tests**

- Kiểm thử lựa chọn action.
- Kiểm thử action không hợp lệ.
- Kiểm thử fallback.
- Kiểm thử các difficulty profiles.
- Kiểm thử trường hợp thiếu dữ liệu quan sát.

### Definition of Done

- AI nhận Battle observation và trả về action theo contract.
- AI không trực tiếp cập nhật HP hoặc thực thi damage.
- Action do AI đề xuất vẫn được Battle Engine kiểm tra.
- Kết quả lựa chọn action có thể kiểm thử.
- Fallback không gây crash hoặc vòng lặp vô hạn.
- AI không phụ thuộc vào JavaFX hoặc database implementation.

---

## 2.5. Thành viên UI/Architecture Lead

**Vai trò:** Sở hữu project foundation, UI, persistence và tích hợp kiến trúc.

| ID | Task | Deliverable |
|---|---|---|
| E01 | Thiết lập Maven, JavaFX và package structure | Buildable project |
| E02 | Xây dựng GameState và New Game initialization | Game state module |
| E03 | Xây dựng Main Menu, New Game và Continue | Initial UI |
| E04 | Xây dựng Controller và navigation | UI navigation |
| E05 | Xây dựng Repository/DAO và SQLite persistence | Persistence module |
| E06 | Xây dựng Save/Load và Auto Save | Save/load service |
| E07 | Tích hợp UI với gameplay services và xử lý lỗi | Integrated application |

### Trách nhiệm chi tiết

**E01 — Project Foundation**

- Tạo Maven project.
- Cấu hình Java và JavaFX.
- Thiết lập package structure.
- Thiết lập cấu hình test và quy trình build.
- Cung cấp hướng dẫn chạy project cho toàn nhóm.

**E02 — GameState**

- Xác định trạng thái tiến trình tổng thể.
- Quản lý các tham chiếu cần thiết đến Team, Collection, progression và world progress.
- Xác định cách tạo GameState mới.
- Thống nhất dữ liệu nào cần tồn tại sau khi load game.

**E03 — Main Menu**

- Xây dựng Main Menu.
- Xây dựng New Game flow.
- Xây dựng Continue flow.
- Hiển thị trạng thái khi chưa có save hoặc save không hợp lệ.

**E04 — Controller và Navigation**

- Điều hướng giữa các màn hình.
- Chuyển yêu cầu của người chơi tới service phù hợp.
- Cập nhật UI từ kết quả service.
- Không đưa các luật damage, EXP hoặc AI vào controller.

**E05 — Persistence**

- Xây dựng Repository/DAO.
- Thiết lập SQLite schema.
- Quản lý lưu và truy xuất dữ liệu.
- Xử lý lỗi truy cập database.
- Phân biệt lỗi dữ liệu, lỗi kết nối và lỗi save/load.

**E06 — Save/Load**

- Lưu GameState theo persistence contract.
- Khôi phục tiến trình khi Continue.
- Xác định thời điểm Auto Save theo specification.
- Xử lý dữ liệu save không hợp lệ hoặc không tương thích.

**E07 — Integration**

- Kết nối UI với Battle, Monster, Progression và AI services.
- Hiển thị kết quả action và trạng thái gameplay.
- Xử lý lỗi service bằng thông báo phù hợp.
- Đảm bảo UI không tự sửa dữ liệu domain để vượt qua validation.

### Definition of Done

- Project build được bằng Maven trên môi trường thống nhất.
- Ứng dụng khởi chạy được bằng JavaFX.
- UI không chứa gameplay rules.
- Repository/DAO tách khỏi domain logic.
- Save/Load có thể kiểm thử.
- Lỗi persistence được xử lý rõ ràng.
- Có hướng dẫn build, test và run ứng dụng.

---

# 3. Shared Contracts và quy tắc phối hợp

## 3.1. Shared contracts

Các contract sau phải được thống nhất trước khi các module liên quan triển khai phần phụ thuộc.

| Contract | Owner chính | Thành viên phối hợp |
|---|---|---|
| Monster và Species models | Monster & Progression Lead | Battle System Lead, AI & Data Lead, UI/Architecture Lead |
| Skill và Type models | Monster & Progression Lead | Battle System Lead, AI & Data Lead |
| Battle state và Battle actions | Battle System Lead | Monster & Progression Lead, AI & Data Lead, UI/Architecture Lead |
| Battle observation và AI action | AI & Data Lead | Battle System Lead, Monster & Progression Lead |
| GameState và progression snapshot | UI/Architecture Lead | Game & Gameplay Lead, Monster & Progression Lead |
| EXP/reward result | Monster & Progression Lead | Game & Gameplay Lead, Battle System Lead, UI/Architecture Lead |
| Save/load representation | UI/Architecture Lead | Battle System Lead, Monster & Progression Lead |
| Gameplay acceptance criteria | Game & Gameplay Lead | Dev Team |

## 3.2. Quy tắc thiết kế contract

1. Model dùng chung phải có tên, kiểu dữ liệu và ý nghĩa thống nhất.
2. ID được sử dụng xuyên module phải có định dạng thống nhất.
3. Không tự ý đổi tên method hoặc cấu trúc dữ liệu dùng chung mà không thông báo các module phụ thuộc.
4. Business logic phải được gọi qua public interface đã thống nhất.
5. Không để AI thực thi Battle action trực tiếp.
6. Không để UI tự tính damage, EXP hoặc điều kiện Evolution.
7. Persistence không được trở thành nơi chứa gameplay rules.
8. Khi một contract thay đổi, phải cập nhật tài liệu, implementation và tests liên quan.

## 3.3. Contract Change Process

Khi cần thay đổi một shared contract:

1. Thành viên tạo đề xuất thay đổi.
2. Mô tả lý do và các module bị ảnh hưởng.
3. Thành viên owner của contract review đề xuất.
4. Game & Gameplay Lead xác nhận nếu thay đổi ảnh hưởng gameplay rules.
5. Các thành viên liên quan cập nhật implementation và tests.
6. Chỉ merge sau khi các module phụ thuộc đã được kiểm tra.

---

# 4. Implementation Backlog

## 4.1. Quy ước trạng thái

| Status | Ý nghĩa |
|---|---|
| Todo | Chưa bắt đầu |
| In Progress | Đang triển khai |
| In Review | Đang chờ review |
| Blocked | Bị chặn bởi dependency hoặc quyết định chưa chốt |
| Done | Đã đáp ứng Definition of Done và được nghiệm thu |

Tất cả task khởi tạo ở trạng thái `Todo`. Trạng thái phải được cập nhật theo tiến độ thực tế.

## 4.2. Priority

- **P0:** Bắt buộc để hoàn thành core vertical slice.
- **P1:** Cần cho gameplay expansion.
- **P2:** Hoàn thiện, tối ưu hoặc bổ sung trải nghiệm.

## 4.3. Backlog tổng hợp

| ID | Task | Owner | Dependency | Priority | Deliverable | Status |
|---|---|---|---|---|---|---|
| E01 | Maven + JavaFX foundation | UI/Architecture Lead | — | P0 | Buildable project | Todo |
| A01 | Gameplay decision log | Game & Gameplay Lead | `docs/*` | P0 | Gameplay specification | Todo |
| A02 | Gameplay flows and state transitions | Game & Gameplay Lead | A01 | P0 | Flow diagrams | Todo |
| C01 | Monster/Species/Stats/Type/Skill models | Monster & Progression Lead | Shared contracts | P0 | Monster domain | Todo |
| C05 | Sample data and validation | Monster & Progression Lead | C01 | P0 | Validated seed data | Todo |
| B01 | Battle model and state | Battle System Lead | C01, shared contracts | P0 | Battle models | Todo |
| B03 | Damage calculator and effectiveness | Battle System Lead | B01, C01 | P0 | Damage module | Todo |
| B02 | Turn resolution and Battle Engine | Battle System Lead | B01, B03 | P0 | Battle Engine | Todo |
| B04 | Victory/Defeat resolution | Battle System Lead | B02, A03 | P0 | Battle result | Todo |
| C02 | Team and Collection | Monster & Progression Lead | C01 | P0 | Team service | Todo |
| C03 | EXP and Level Up | Monster & Progression Lead | C01, A04 | P0 | Progression service | Todo |
| E02 | GameState and initialization | UI/Architecture Lead | E01, shared contracts | P0 | GameState module | Todo |
| E05 | SQLite repository foundation | UI/Architecture Lead | E01, persistence contract | P0 | Repository/DAO | Todo |
| E06 | Save/Load | UI/Architecture Lead | E02, E05 | P0 | Save/load service | Todo |
| D01 | AI input/action contract | AI & Data Lead | B01, shared contracts | P0 | AI interface | Todo |
| D03 | Basic action evaluator | AI & Data Lead | D01, B02 interface | P0 | Action evaluator | Todo |
| D05 | AI fallback | AI & Data Lead | D01 | P0 | Fallback logic | Todo |
| D06 | AI unit tests | AI & Data Lead | D03, D05 | P0 | AI test suite | Todo |
| B06 | Battle unit tests | Battle System Lead | B02, B03, B04 | P0 | Battle test suite | Todo |
| C06 | Monster/progression unit tests | Monster & Progression Lead | C02, C03 | P0 | Progression test suite | Todo |
| E03 | Main Menu/New Game/Continue | UI/Architecture Lead | E01, E02, E06 | P0 | Initial UI flow | Todo |
| E04 | Controller/navigation | UI/Architecture Lead | E03, service contracts | P0 | Navigation module | Todo |
| E07 | UI and service integration | UI/Architecture Lead | B02, C02, C03, E04 | P0 | Integrated UI | Todo |
| A05 | Vertical-slice acceptance | Game & Gameplay Lead | Core tasks complete | P0 | Acceptance results | Todo |
| C04 | Evolution service | Monster & Progression Lead | C01, C03, A04 | P1 | Evolution module | Todo |
| D02 | Pattern Analyzer | AI & Data Lead | D01, battle observation history | P1 | Behavior analyzer | Todo |
| D04 | Difficulty profiles and Boss AI | AI & Data Lead | D02, D03, A03 | P1 | Adaptive AI | Todo |
| B05 | Switching and extended action validation | Battle System Lead | B02, C02 | P1 | Switching logic | Todo |
| E06A | Auto Save and recovery | UI/Architecture Lead | E05, E06 | P1 | Auto Save | Todo |
| X01 | Capture system | Battle System Lead + Monster & Progression Lead | Battle contract, Monster models | P1 | Capture service | Todo |
| X02 | Inventory and item usage | Monster & Progression Lead + UI/Architecture Lead | GameState, item contract | P1 | Inventory module | Todo |
| X03 | Gym and Badge progression | Game & Gameplay Lead + Battle System Lead + Monster & Progression Lead | Battle result, progression | P1 | Gym progression | Todo |
| X04 | Region unlock and completion | Game & Gameplay Lead + Monster & Progression Lead + UI/Architecture Lead | Reward/progression contract | P1 | World progression | Todo |
| X05 | UI expansion | UI/Architecture Lead | Expansion services | P1 | Extended UI | Todo |
| X06 | Integration and regression testing | Game & Gameplay Lead + team | Expansion tasks | P1 | Test results | Todo |
| R01 | Full build/test stabilization | UI/Architecture Lead + team | Core and expansion tasks | P0 | Release candidate | Todo |
| R02 | UML and technical documentation | Game & Gameplay Lead + UI/Architecture Lead | Stable architecture | P1 | Final documentation | Todo |
| R03 | Demo preparation | Game & Gameplay Lead + team | Acceptance passed | P1 | Demo checklist | Todo |

**Lưu ý:** `E06A` là ID riêng cho Auto Save để tránh trùng với E06 — Save/Load. Các ID `X01–X06` và `R01–R03` là task nhóm bổ sung cho expansion và release.

---

# 5. Dependency và thứ tự triển khai

## 5.1. Phase 1 — Project Foundation & Shared Contracts

**Mục tiêu:** Tạo nền tảng kỹ thuật và thống nhất giao tiếp giữa các module.

### Công việc

- UI/Architecture Lead triển khai E01 — Maven, JavaFX và package structure.
- Game & Gameplay Lead triển khai A01 — Gameplay decision log.
- Game & Gameplay Lead triển khai A02 — Gameplay flow và state transitions.
- Dev Team thống nhất shared contracts.
- Monster & Progression Lead triển khai C01 — Monster domain models.
- Monster & Progression Lead triển khai C05 — Dữ liệu mẫu và validation.
- Battle System Lead xác định Battle model dựa trên Monster contract.
- AI & Data Lead xác định AI input/action contract.
- UI/Architecture Lead xác định GameState và persistence contract.

### Gate 1 — Foundation Ready

Phase được xem là hoàn thành khi:

- Project build thành công.
- Cấu trúc package được thống nhất.
- Các contract chính có tài liệu.
- Monster data mẫu có thể khởi tạo.
- Các dependency chưa giải quyết được đã được ghi nhận.
- Không còn thay đổi contract quan trọng chưa được thông báo.

## 5.2. Phase 2 — Core Gameplay Systems

**Mục tiêu:** Hoàn thành các module gameplay cốt lõi và kiểm thử độc lập.

### Công việc song song

- Battle System Lead: B01, B02, B03, B04, B06.
- Monster & Progression Lead: C02, C03, C06.
- AI & Data Lead: D03, D05, D06.
- UI/Architecture Lead: E02, E05, E06.
- Game & Gameplay Lead: Hoàn thiện acceptance criteria và rà soát luật chơi.

Các task chỉ được bắt đầu khi dependency tương ứng đã sẵn sàng. Có thể dùng interface hoặc test double đã thống nhất để tiếp tục phát triển nếu implementation của module phụ thuộc chưa hoàn tất.

### Gate 2 — Core Modules Ready

- Battle Engine hoạt động mà không phụ thuộc JavaFX.
- Monster, Team và Progression services có unit tests.
- AI trả về action theo contract và có fallback.
- GameState có thể khởi tạo.
- Persistence có thể lưu và khôi phục dữ liệu mẫu.
- Các unit tests liên quan đều pass.

## 5.3. Phase 3 — Vertical Slice

**Mục tiêu:** Hoàn thành một vòng chơi đầu-cuối có thể demo.

### Luồng bắt buộc

1. Người chơi mở ứng dụng.
2. Chọn New Game.
3. Chọn Starter Monster.
4. Vào Explore.
5. Kích hoạt Wild Encounter.
6. Battle diễn ra qua Battle Engine.
7. Battle kết thúc với Victory hoặc Defeat.
8. Nếu Victory, người chơi nhận reward và EXP theo luật.
9. Level Up được xử lý nếu đủ điều kiện.
10. GameState được lưu.
11. Người chơi đóng ứng dụng.
12. Chọn Continue và khôi phục tiến trình đã lưu.

### Công việc

- UI/Architecture Lead hoàn thành E03, E04 và E07.
- Battle System Lead/Monster & Progression Lead/AI & Data Lead phối hợp xử lý luồng Battle và reward.
- UI/Architecture Lead kết nối Save/Load với GameState.
- Game & Gameplay Lead thực hiện nghiệm thu theo integration test plan.

### Gate 3 — Vertical Slice Accepted

- Luồng New Game đến Battle hoạt động.
- Battle result được phản ánh đúng vào progression.
- Save/Load khôi phục đúng trạng thái được yêu cầu.
- Các lỗi đầu vào và trường hợp không hợp lệ được xử lý.
- Có thể demo lại luồng mà không phải chỉnh sửa dữ liệu thủ công.
- Các acceptance tests bắt buộc đều pass.

## 5.4. Phase 4 — Gameplay Expansion

**Mục tiêu:** Bổ sung các hệ thống mở rộng dựa trên core đã ổn định.

### Công việc

- Monster & Progression Lead: C04 — Evolution.
- AI & Data Lead: D02 và D04 — Pattern Analyzer, difficulty profiles và Boss AI.
- Battle System Lead/Monster & Progression Lead: X01 — Capture.
- Monster & Progression Lead/UI/Architecture Lead: X02 — Inventory.
- Game & Gameplay Lead/Battle System Lead/Monster & Progression Lead: X03 — Gym và Badge.
- Game & Gameplay Lead/Monster & Progression Lead/UI/Architecture Lead: X04 — Region unlock và game completion.
- UI/Architecture Lead: X05 — UI expansion và E06A — Auto Save.

### Gate 4 — Expansion Accepted

- Mỗi tính năng có contract và acceptance criteria.
- Integration tests bao phủ luồng mới.
- Gameplay logic vẫn nằm trong đúng module.
- Các luồng hiện có không bị phá vỡ.
- Save/Load xử lý được các dữ liệu progression mới.
- Regression tests liên quan đều pass.

## 5.5. Phase 5 — Stabilization & Release

**Mục tiêu:** Tạo bản prototype ổn định để demo và bàn giao.

### Công việc

- Chạy toàn bộ unit tests và integration tests.
- Kiểm tra Save/Load và dữ liệu không hợp lệ.
- Kiểm tra UI navigation và các luồng gameplay.
- Sửa lỗi tích hợp.
- Xác nhận Maven build trên môi trường thống nhất.
- Hoàn thiện UML, README và tài liệu kỹ thuật.
- Chuẩn bị kịch bản demo.

### Gate 5 — Release Ready

- Project build thành công từ trạng thái source code được bàn giao.
- Các test bắt buộc đều pass.
- Không còn lỗi nghiêm trọng chặn luồng demo.
- Có hướng dẫn cài đặt và chạy ứng dụng.
- Có kịch bản demo và danh sách tính năng đã nghiệm thu.
- Các lỗi còn tồn tại được ghi nhận rõ ràng.

---

# 6. Definition of Done — Tiêu chí hoàn thành

## 6.1. DoD cho từng task

Một task chỉ được đánh dấu `Done` khi đáp ứng tất cả điều kiện phù hợp sau:

- [ ] Implementation đáp ứng yêu cầu đã mô tả.
- [ ] Không vi phạm module contract.
- [ ] Xử lý các trường hợp lỗi và dữ liệu không hợp lệ có liên quan.
- [ ] Có unit tests hoặc loại kiểm thử phù hợp.
- [ ] Tất cả test liên quan đều pass.
- [ ] Code đã được review.
- [ ] Không có debug code hoặc dữ liệu thử nghiệm không được kiểm soát.
- [ ] Tài liệu/API liên quan được cập nhật.
- [ ] Dependency và tác động liên module được xác nhận.
- [ ] Code đã được merge vào branch tích hợp theo quy trình nhóm.

Task có thể được nghiệm thu với một số điều kiện không áp dụng, nhưng phải ghi rõ lý do trong pull request hoặc kết quả review.

## 6.2. DoD theo module

### Battle module

- [ ] Battle Engine không phụ thuộc JavaFX.
- [ ] Damage Calculation tuân thủ gameplay specification.
- [ ] Action không hợp lệ bị từ chối.
- [ ] Victory/Defeat được xác định chính xác.
- [ ] AI không trực tiếp thực thi damage.
- [ ] Unit tests bao phủ các trường hợp chính.

### Monster & Progression module

- [ ] Monster và Species có model rõ ràng.
- [ ] Team và Collection duy trì invariant.
- [ ] EXP và Level Up hoạt động đúng.
- [ ] Evolution kiểm tra điều kiện hợp lệ.
- [ ] Dữ liệu mẫu vượt qua validation.
- [ ] Unit tests bao phủ progression rules.

### AI module

- [ ] AI nhận observation theo contract.
- [ ] AI lựa chọn action từ danh sách action hợp lệ.
- [ ] Battle Engine vẫn thực hiện validation.
- [ ] Có fallback khi không có action hợp lệ.
- [ ] Các difficulty profiles được kiểm thử.
- [ ] Không phụ thuộc UI hoặc persistence.

### UI & Architecture module

- [ ] Maven build thành công.
- [ ] JavaFX khởi chạy được.
- [ ] Controller gọi service thay vì tự triển khai gameplay rules.
- [ ] Persistence tách khỏi domain logic.
- [ ] Save/Load khôi phục dữ liệu theo contract.
- [ ] Lỗi được hiển thị hoặc xử lý phù hợp.

## 6.3. DoD cho integration flow

Một integration flow được xem là hoàn thành khi:

- [ ] Các module tham gia sử dụng đúng shared contracts.
- [ ] Luồng thành công hoạt động đầu-cuối.
- [ ] Các trường hợp lỗi quan trọng được kiểm thử.
- [ ] Trạng thái domain sau luồng đúng với specification.
- [ ] Dữ liệu được lưu/khôi phục nếu luồng yêu cầu persistence.
- [ ] Có test hoặc demo procedure để tái hiện kết quả.
- [ ] Game & Gameplay Lead hoặc người được giao nghiệm thu xác nhận đạt.

---

# 7. Quy trình tích hợp và bàn giao code

## 7.1. Branching strategy

Đề xuất sử dụng Git với các branch:

- `main`: phiên bản ổn định.
- `develop`: branch tích hợp.
- `feature/<task-id>-<short-name>`: branch triển khai task.
- `fix/<short-name>`: branch sửa lỗi.

Ví dụ:

- `feature/B02-battle-engine`
- `feature/C03-level-progression`
- `feature/D03-action-evaluator`
- `feature/E05-sqlite-persistence`
- `feature/A02-gameplay-flow`

Mỗi task nên có branch riêng để dễ review và theo dõi.

## 7.2. Quy trình bàn giao

1. Thành viên lấy task từ backlog.
2. Kiểm tra dependency và acceptance criteria.
3. Tạo branch cho task.
4. Triển khai implementation và tests.
5. Chạy build và các test liên quan.
6. Tạo Pull Request vào `develop`.
7. Mô tả thay đổi, kết quả test và module bị ảnh hưởng.
8. Thành viên liên quan review.
9. Sửa các vấn đề được yêu cầu.
10. Merge khi đạt tiêu chí nghiệm thu.
11. Cập nhật trạng thái task.
12. Kiểm tra lại integration build sau khi merge.

## 7.3. Pull Request checklist

Mỗi Pull Request cần cung cấp:

- [ ] Task ID và mục tiêu thay đổi.
- [ ] Danh sách file/module bị ảnh hưởng.
- [ ] Shared contract có thay đổi hay không.
- [ ] Unit tests/integration tests đã chạy.
- [ ] Kết quả build.
- [ ] Ảnh chụp màn hình hoặc demo nếu thay đổi UI.
- [ ] Migration hoặc cập nhật schema nếu thay đổi database.
- [ ] Các vấn đề còn tồn tại.
- [ ] Tài liệu cần cập nhật.

## 7.4. Quy tắc tích hợp

- Không merge code chưa build/test.
- Không thay đổi shared contract một cách âm thầm.
- Không đưa gameplay logic vào JavaFX controller.
- Không để repository/DAO trực tiếp quyết định luật chơi.
- Không để AI cập nhật Battle state ngoài contract.
- Không merge một tính năng expansion nếu nó phá vỡ core vertical slice.
- Sau khi merge, cần chạy lại các test bị ảnh hưởng.
- Nếu dependency chưa sẵn sàng, dùng interface hoặc test double đã thống nhất thay vì tự tạo API không tương thích.

## 7.5. Xử lý task bị Blocked

Khi một task bị chặn:

1. Chuyển trạng thái sang `Blocked`.
2. Ghi rõ nguyên nhân.
3. Xác định task hoặc quyết định đang chặn.
4. Gắn owner có trách nhiệm giải quyết.
5. Đề xuất cách tiếp tục không phá vỡ contract nếu có.
6. Cập nhật trạng thái sau khi dependency được giải quyết.

Ví dụ:

| Task | Blocker | Owner giải quyết | Hướng xử lý |
|---|---|---|---|
| B02 | Chưa chốt Battle action contract | Battle System Lead + AI & Data Lead | Thống nhất action model |
| D03 | Chưa có danh sách action hợp lệ | Battle System Lead + AI & Data Lead | Chốt API cung cấp legal actions |
| C03 | Chưa chốt EXP/reward rules | Game & Gameplay Lead + Monster & Progression Lead | Chốt progression specification |
| E06 | Chưa thống nhất GameState representation | Monster & Progression Lead + UI/Architecture Lead | Chốt persistence contract |

---

# 8. Theo dõi tiến độ và nghiệm thu

## 8.1. Quy tắc quản lý backlog

- Mỗi task có đúng một owner chịu trách nhiệm chính.
- Có thể có nhiều contributor, nhưng owner chịu trách nhiệm bàn giao.
- Task mới phải có ID, dependency, deliverable và acceptance criteria.
- Task lớn nên được tách thành các task nhỏ có thể review độc lập.
- Không đánh dấu `Done` chỉ vì code đã được viết.
- Các thay đổi scope phải được ghi nhận trước khi điều chỉnh backlog.
- Game & Gameplay Lead theo dõi tiến độ và điều phối các vấn đề ảnh hưởng nhiều module.

## 8.2. Báo cáo tiến độ

Mỗi thành viên nên cập nhật:

- Task đang thực hiện.
- Task đã hoàn thành.
- Dependency đang chờ.
- Lỗi hoặc vấn đề phát sinh.
- Các thay đổi contract cần review.
- Kế hoạch tiếp theo.

## 8.3. Acceptance checklist tổng thể

### Foundation

- [ ] Maven project build được.
- [ ] JavaFX khởi chạy được.
- [ ] Shared contracts được thống nhất.
- [ ] Monster data mẫu hợp lệ.

### Core systems

- [ ] Battle Engine hoạt động.
- [ ] Damage Calculation và Type Effectiveness đúng.
- [ ] Team và Collection hoạt động.
- [ ] EXP và Level Up hoạt động.
- [ ] AI trả về action theo contract.
- [ ] Save/Load hoạt động.

### Vertical slice

- [ ] New Game.
- [ ] Chọn Starter Monster.
- [ ] Explore và Wild Encounter.
- [ ] Battle Victory/Defeat.
- [ ] Nhận reward và EXP.
- [ ] Save và Continue.
- [ ] Khôi phục đúng tiến trình.

### Expansion

- [ ] Capture.
- [ ] Inventory.
- [ ] Evolution.
- [ ] Gym và Badge.
- [ ] Boss AI.
- [ ] Region Unlock.
- [ ] Auto Save.

### Release

- [ ] Unit tests pass.
- [ ] Integration tests pass.
- [ ] Regression tests pass.
- [ ] Build thành công.
- [ ] README và UML được cập nhật.
- [ ] Demo checklist hoàn thành.

---

# 9. Kết quả bàn giao

Sau khi hoàn thành việc lập kế hoạch và phân công, nhóm cần có:

1. **Implementation backlog:** Danh sách task có ID, owner, dependency, priority và trạng thái.
2. **Module ownership:** Phạm vi trách nhiệm của Dev Team.
3. **Shared contracts:** Các interface và model cần thống nhất trước khi tích hợp.
4. **Dependency map:** Thứ tự triển khai và các gate giữa các phase.
5. **Definition of Done:** Tiêu chí hoàn thành task, module và integration flow.
6. **Integration workflow:** Quy trình branch, review, test và merge.
7. **Acceptance checklist:** Các tiêu chí nghiệm thu core gameplay và prototype.