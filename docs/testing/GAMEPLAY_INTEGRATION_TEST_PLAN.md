# MONSTERRA — Gameplay Integration Test Plan

## 1. Mục tiêu

Xác định cách kiểm chứng các module của MONSTERRA hoạt động chính xác khi phối hợp trong những luồng gameplay hoàn chỉnh.

Các mục tiêu chính:

- Chuyển từng integration flow thành test case có thể thực thi và tái hiện.
- Xác định preconditions, test data, test steps và expected results cho từng trường hợp.
- Kiểm tra sự phối hợp giữa các module Dev Team.
- Phát hiện lỗi tại ranh giới module, chẳng hạn Battle thắng nhưng EXP không được cộng hoặc Gym thắng nhưng vùng mới không được mở khóa.
- Xác định tiêu chí nghiệm thu trước khi tích hợp code.
- Đảm bảo các thay đổi về sau không làm hỏng những luồng gameplay đã được xác nhận.

### 1.1. Phạm vi

Tài liệu tập trung vào các luồng:

1. Game initialization và Save/Load.
2. Exploration và Wild Battle.
3. Monster, Capture và Progression.
4. Gym, Boss và Adaptive AI.

Các test tập trung vào kết quả gameplay và sự phối hợp giữa nhiều module, không nhằm thay thế Unit Test của từng class hoặc UI Test cho từng thành phần giao diện.

### 1.2. Nguyên tắc kiểm thử

- **Gameplay-first:** Ưu tiên các luồng ảnh hưởng trực tiếp đến trải nghiệm và tiến trình của người chơi.
- **Deterministic:** Sử dụng dữ liệu cố định và kiểm soát randomness khi có thể.
- **Contract-based:** Kiểm tra dữ liệu đầu ra của module này có đáp ứng contract của module tiếp theo hay không.
- **Repeatable:** Cùng một test data và cấu hình phải cho kết quả có thể tái hiện.
- **Isolated:** Sử dụng GameState, save data và database riêng cho test.
- **No duplicate rewards:** Các sự kiện kết thúc Battle không được trao EXP hoặc reward nhiều lần ngoài quy tắc đã thống nhất.
- **State consistency:** Trạng thái sau mỗi luồng phải nhất quán giữa Battle, Monster, Player, Progression và Save.

---

## 2. Deliverable

### 2.1. Định nghĩa trạng thái test

| Trạng thái | Ý nghĩa |
|---|---|
| Draft | Test case đã được đề xuất nhưng chưa xác nhận đầy đủ |
| Ready | Preconditions, dữ liệu và expected results đã được thống nhất |
| Pass | Kết quả thực tế đáp ứng toàn bộ expected results |
| Fail | Có ít nhất một expected result không đạt |
| Blocked | Không thể chạy do thiếu implementation, dependency hoặc quy tắc chưa được chốt |
| Not Run | Chưa thực thi test |

Các trạng thái trên mô tả tình trạng kiểm thử, không phải trạng thái gameplay trong game.

---

## 3. Danh sách Integration Test

### 3.1. Nhóm A — Game Initialization và Save/Load

| ID | Test case | Kết quả mong đợi | Owner chính |
|---|---|---|---|
| IT-01 | New Game thành công | Profile, Starter Monster, Inventory và vị trí ban đầu được khởi tạo đúng | UI/Architecture Lead + Game & Gameplay Lead |
| IT-02 | New Game với tên Trainer không hợp lệ | Không tạo profile không hợp lệ; trả về hoặc hiển thị lỗi phù hợp | UI/Architecture Lead + Game & Gameplay Lead |
| IT-03 | Save rồi Continue | Khôi phục đúng Team, EXP, Inventory, vị trí và tiến trình Gym | UI/Architecture Lead |
| IT-04 | Load save bị hỏng | Báo lỗi an toàn; không tạo GameState sai hoặc làm crash game | UI/Architecture Lead |
| IT-05 | Auto Save khi gameplay đang diễn ra | Lưu thành công mà không làm gián đoạn luồng gameplay hoặc giao diện | UI/Architecture Lead + Game & Gameplay Lead |

### 3.2. Nhóm B — Exploration và Wild Battle

| ID | Test case | Kết quả mong đợi | Owner chính |
|---|---|---|---|
| IT-06 | Đi vào vùng có thể gặp Monster | Encounter chỉ xuất hiện khi thỏa các điều kiện encounter | Game & Gameplay Lead + UI/Architecture Lead |
| IT-07 | Encounter khởi tạo Battle | Đúng Monster của người chơi và Wild Monster được đưa vào Battle | Game & Gameplay Lead + Battle System Lead + Monster & Progression Lead |
| IT-08 | Người chơi thắng Wild Battle | Battle kết thúc; EXP và reward được xử lý đúng; người chơi có thể tiếp tục khám phá | Game & Gameplay Lead + Battle System Lead + Monster & Progression Lead |
| IT-09 | Đội người chơi bị đánh bại hoàn toàn | Áp dụng đúng quy tắc defeat; không cho Monster đã bị hạ tiếp tục hành động | Game & Gameplay Lead + Battle System Lead + Monster & Progression Lead |
| IT-10 | Người chơi sử dụng Skill có Type Advantage | Damage, kết quả Skill và Battle State được cập nhật đúng theo luật | Battle System Lead + Monster & Progression Lead |

### 3.3. Nhóm C — Monster, Capture và Progression

| ID | Test case | Kết quả mong đợi | Owner chính |
|---|---|---|---|
| IT-11 | Monster nhận EXP sau chiến thắng | EXP được cộng đúng cho các Monster đủ điều kiện | Monster & Progression Lead + Battle System Lead |
| IT-12 | EXP vượt ngưỡng Level | Level và Stats được cập nhật đúng theo quy tắc progression | Monster & Progression Lead |
| IT-13 | Level Up mở khóa Evolution | Điều kiện Evolution được kiểm tra; lựa chọn và tiến hóa hoạt động đúng khi được xác nhận | Monster & Progression Lead + UI/Architecture Lead |
| IT-14 | Capture Wild Monster thành công | Monster được thêm vào Team hoặc Collection theo quy tắc đã thống nhất | Game & Gameplay Lead + Battle System Lead + Monster & Progression Lead |
| IT-15 | Capture khi Team đã đủ 6 Monster | Không vượt giới hạn Team; Monster mới được lưu theo quy tắc Team/Storage đã thống nhất | Game & Gameplay Lead + Monster & Progression Lead + UI/Architecture Lead |
| IT-16 | Switch sang Monster đã bị hạ | Hành động bị từ chối; Battle State không bị thay đổi sai | Battle System Lead + Monster & Progression Lead |

### 3.4. Nhóm D — Gym, Boss và Adaptive AI

| ID | Test case | Kết quả mong đợi | Owner chính |
|---|---|---|---|
| IT-17 | Challenge Gym chưa được mở khóa | Không bắt đầu Gym Battle; tiến trình không thay đổi ngoài dự kiến | Game & Gameplay Lead + UI/Architecture Lead |
| IT-18 | Thắng Gym Battle | Nhận Badge, reward và mở khóa vùng tiếp theo đúng điều kiện | Game & Gameplay Lead + Battle System Lead + Monster & Progression Lead |
| IT-19 | Thua Gym Battle | Không nhận Badge; tiến trình trước Battle được giữ theo luật | Game & Gameplay Lead + Battle System Lead + UI/Architecture Lead |
| IT-20 | AI chọn Skill trong Battle | Action được chọn thuộc danh sách hành động hợp lệ | AI & Data Lead + Battle System Lead |
| IT-21 | AI phân tích lịch sử người chơi | Pattern Profile được cập nhật và có thể ảnh hưởng đến lựa chọn tiếp theo theo thiết kế | AI & Data Lead |
| IT-22 | Boss chuyển Phase khi HP xuống ngưỡng | Phase và chiến thuật được cập nhật đúng; không kích hoạt lặp ngoài ý muốn | Game & Gameplay Lead + Battle System Lead + AI & Data Lead |
| IT-23 | AI đề xuất hành động không hợp lệ | Battle từ chối hoặc dùng fallback an toàn; không phá vỡ Battle State | Battle System Lead + AI & Data Lead |

**Lưu ý:** Đây là danh sách test case đề xuất ban đầu. Các quy tắc chưa được chốt phải được đánh dấu Blocked hoặc giữ ở trạng thái Draft cho đến khi có quyết định chính thức.

Ví dụ: khi Team đã có đủ 6 Monster, Monster mới được đưa vào Storage, Collection hay một khu vực dự trữ khác cần được thống nhất trước khi xác nhận IT-15 là tiêu chí nghiệm thu cuối cùng.

---

## 4. Cấu trúc chuẩn của một Integration Test

Mỗi test case phải cung cấp đủ thông tin để một thành viên khác có thể thiết lập môi trường, thực thi và xác nhận kết quả mà không cần suy đoán các quy tắc gameplay.

### 4.1. Các trường bắt buộc

| Trường | Nội dung |
|---|---|
| ID | Mã định danh duy nhất, ví dụ IT-08 |
| Name | Tên ngắn mô tả luồng cần kiểm thử |
| Type | Integration |
| Objective | Mục tiêu kiểm chứng |
| Preconditions | Điều kiện bắt buộc trước khi chạy |
| Test data | Dữ liệu đầu vào cố định |
| Test steps | Các bước thực thi theo thứ tự |
| Expected results | Kết quả phải đạt sau từng bước hoặc khi kết thúc |
| Modules involved | Danh sách module tham gia |
| Owner | Người chịu trách nhiệm chính |
| Dependencies | Các contract, tính năng hoặc quy tắc cần có |
| Status | Draft, Ready, Pass, Fail, Blocked hoặc Not Run |

### 4.2. Ví dụ chi tiết — IT-08

#### IT-08 — Wild Battle Victory → EXP → Level Up → Save/Load

**Type:** Integration

**Objective**

Xác minh luồng liên module từ chiến thắng Wild Battle đến trao EXP, xử lý Level Up, kiểm tra điều kiện Evolution và lưu/khôi phục tiến trình.

**Modules involved:** Game & Gameplay Lead, Battle System Lead, Monster & Progression Lead, UI/Architecture Lead.

**Owner chính:** Game & Gameplay Lead + Battle System Lead + Monster & Progression Lead.

**Dependencies**

- Battle có thể khởi tạo và kết thúc theo Battle State Machine.
- Battle Result cung cấp kết quả thắng/thua rõ ràng.
- Progression xử lý EXP và Level theo contract đã thống nhất.
- SaveService có thể lưu và khôi phục GameState.
- Quy tắc phân phối EXP và kích hoạt Evolution đã được xác nhận.

**Preconditions**

1. GameState hợp lệ và đã có Player.
2. Player có ít nhất một Monster còn khả năng chiến đấu.
3. Có một Wild Monster hợp lệ để chiến đấu.
4. EXP hiện tại, EXP reward và ngưỡng Level của Monster đã biết.
5. Test environment sử dụng dữ liệu cố định.
6. SaveService và database test đã sẵn sàng.

**Test data**

| Thuộc tính | Giá trị mẫu |
|---|---|
| Player Monster | `MON-001` |
| Monster EXP hiện tại | `90` |
| EXP reward từ Battle | `20` |
| EXP cần đạt Level tiếp theo | `100` |
| Battle outcome mong muốn | `VICTORY` |
| Save slot | `integration-test-08` |

*Lưu ý:* Đây là dữ liệu minh họa để mô tả cách thiết kế test. Khi triển khai, thay bằng fixture hợp lệ của game và công thức EXP chính thức. Ví dụ trên giả định ngưỡng Level là 100 EXP.

**Test steps**

1. Khởi tạo GameState từ fixture đã xác định.
2. Xác nhận Monster của người chơi có đúng EXP ban đầu.
3. Khởi tạo Wild Battle với đối thủ trong test data.
4. Thực hiện các hành động hợp lệ cho đến khi Battle kết thúc với kết quả thắng.
5. Kiểm tra Battle Result và trạng thái kết thúc của Battle.
6. Kiểm tra EXP reward được phát sinh đúng theo quy tắc.
7. Kiểm tra Monster nhận EXP đúng một lần.
8. Kiểm tra Level và Stats sau khi cập nhật EXP.
9. Kiểm tra Evolution được mở khóa nếu Monster thỏa điều kiện.
10. Gọi SaveService để lưu GameState.
11. Khởi tạo GameState mới bằng cách load save slot vừa lưu.
12. Đối chiếu Monster, EXP, Level, Evolution state và các dữ liệu liên quan giữa trạng thái trước khi lưu và sau khi load.

**Expected results**

- Battle kết thúc với trạng thái `VICTORY`.
- EXP reward được xử lý đúng theo quy tắc đã thống nhất.
- EXP không bị cộng thiếu hoặc cộng trùng.
- Level và Stats phản ánh đúng lượng EXP mới.
- Evolution được xử lý đúng theo điều kiện; không tự động tiến hóa nếu cần xác nhận từ người chơi.
- SaveService lưu thành công GameState hợp lệ.
- GameState sau khi Load phản ánh đúng trạng thái đã lưu.
- Việc load không phát lại reward hoặc làm tăng EXP lần thứ hai.
- Không có lỗi dữ liệu giữa Battle, Progression và Save.

**Pass criteria**

Tất cả expected results đều đạt. Chỉ cần một điều kiện không đạt thì test case được đánh dấu Fail.

---

## 5. Chi tiết các Integration Test ưu tiên

Các test dưới đây mở rộng tiêu chí nghiệm thu cho những luồng có ảnh hưởng lớn đến tính toàn vẹn của gameplay.

### 5.1. IT-01 — New Game thành công

**Preconditions**

- Chưa có save slot được sử dụng cho test.
- Dữ liệu Starter Monster và Inventory mặc định hợp lệ.
- Các quy tắc đặt tên Trainer đã được xác nhận.

**Test steps**

1. Khởi chạy luồng New Game.
2. Nhập tên Trainer hợp lệ.
3. Xác nhận tạo game.
4. Đọc GameState được khởi tạo.
5. Kiểm tra Profile, Starter Monster, Inventory và vị trí ban đầu.

**Expected results**

- Profile được tạo đúng một lần.
- Trainer có tên hợp lệ.
- Starter Monster có dữ liệu và trạng thái ban đầu chính xác.
- Inventory khớp với cấu hình khởi đầu.
- Vị trí và tiến trình ban đầu đúng thiết kế.
- Không có dữ liệu rác từ save slot của test trước.

**Modules involved:** Game & Gameplay Lead, Monster & Progression Lead, UI/Architecture Lead.

### 5.2. IT-03 — Save rồi Continue

**Preconditions**

- Có GameState hợp lệ với Monster, EXP, Inventory, vị trí và tiến trình Gym đã biết.

**Test steps**

1. Ghi lại trạng thái ban đầu.
2. Lưu GameState.
3. Thay đổi hoặc loại bỏ instance GameState đang chạy.
4. Khởi chạy Continue.
5. Load save đã tạo.
6. So sánh dữ liệu sau khi load với snapshot đã lưu.

**Expected results**

- Các trường dữ liệu được lưu theo save contract.
- Team, EXP, Inventory, vị trí và Gym progression được khôi phục đúng.
- Các tham chiếu giữa Player, Monster và Progression hợp lệ.
- Không có dữ liệu bị nhân đôi hoặc mất ngoài quy tắc đã xác nhận.

**Modules involved:** UI/Architecture Lead, Game & Gameplay Lead, Monster & Progression Lead.

### 5.3. IT-09 — Đội người chơi bị đánh bại hoàn toàn

**Preconditions**

- Tất cả Monster trong Team đều có thể bị đưa về trạng thái không còn khả năng chiến đấu.
- Battle đang ở trạng thái cho phép tiếp tục lượt.

**Test steps**

1. Khởi tạo Battle với dữ liệu cố định.
2. Thực hiện các hành động dẫn đến việc toàn bộ Team bị hạ.
3. Kiểm tra trạng thái từng Monster.
4. Kiểm tra Battle State và hành động tiếp theo.
5. Kiểm tra luồng xử lý defeat của game.

**Expected results**

- Battle kết thúc hoặc chuyển sang trạng thái defeat theo State Machine.
- Không Monster nào đã bị hạ tiếp tục hành động.
- Không cho phép Switch sang Monster không thể chiến đấu.
- Không phát sinh thêm lượt hoặc reward trái với quy tắc.
- Luồng sau defeat được xử lý nhất quán.

**Modules involved:** Game & Gameplay Lead, Battle System Lead, Monster & Progression Lead.

### 5.4. IT-14 — Capture Wild Monster thành công

**Preconditions**

- Có Wild Monster có thể bị Capture.
- Điều kiện Capture và dữ liệu xác suất được kiểm soát trong test.
- Team hoặc Collection đang ở trạng thái đã biết.

**Test steps**

1. Khởi tạo Wild Battle.
2. Đưa Battle vào điều kiện cho phép Capture.
3. Thực hiện Capture bằng kết quả thành công được cố định trong test.
4. Kiểm tra Battle Result.
5. Kiểm tra Team hoặc Collection.
6. Kiểm tra dữ liệu Monster vừa được Capture.

**Expected results**

- Capture thành công theo contract.
- Monster được thêm vào đúng nơi lưu trữ.
- Monster mới có dữ liệu hợp lệ và định danh duy nhất.
- Không tạo hai bản ghi ngoài quy tắc.
- Battle kết thúc hoặc tiếp tục theo luật Capture đã thống nhất.

**Modules involved:** Game & Gameplay Lead, Battle System Lead, Monster & Progression Lead.

### 5.5. IT-18 — Thắng Gym Battle và mở khóa vùng tiếp theo

**Preconditions**

- Gym hiện tại đã được mở khóa.
- Player đủ điều kiện Challenge.
- Gym Leader và Battle fixture hợp lệ.
- Điều kiện nhận Badge và mở khóa vùng đã được xác nhận.

**Test steps**

1. Khởi tạo GameState với tiến trình trước Gym.
2. Bắt đầu Gym Challenge.
3. Thực hiện Battle cho đến khi Player thắng.
4. Kiểm tra Battle Result.
5. Kiểm tra Badge và reward.
6. Kiểm tra trạng thái mở khóa vùng tiếp theo.
7. Lưu GameState và load lại.
8. Đối chiếu Gym progression sau khi load.

**Expected results**

- Gym Battle kết thúc với kết quả thắng.
- Badge được cấp đúng một lần.
- Reward được trao đúng theo quy tắc.
- Vùng tiếp theo được mở khóa nếu đáp ứng điều kiện.
- Gym progression được lưu và khôi phục chính xác.
- Không có trạng thái mâu thuẫn giữa Battle Result, Badge, World Unlock và Save.

**Modules involved:** Game & Gameplay Lead, Battle System Lead, Monster & Progression Lead, UI/Architecture Lead.

### 5.6. IT-20 — AI chọn Skill hợp lệ

**Preconditions**

- AI có một Battle State hợp lệ để phân tích.
- Danh sách hành động hợp lệ đã được tạo.
- AI được cấu hình bằng seed hoặc mock phù hợp để test tái hiện được.

**Test steps**

1. Khởi tạo Battle State.
2. Yêu cầu AI chọn hành động.
3. Thu thập hành động AI trả về.
4. Đối chiếu với danh sách hành động hợp lệ.
5. Gửi hành động sang Battle Engine.
6. Kiểm tra Battle Engine xử lý hành động.

**Expected results**

- AI trả về một hành động có cấu trúc hợp lệ.
- Action nằm trong danh sách hành động được phép.
- Action không vi phạm trạng thái Monster, Skill hoặc Battle.
- Battle Engine có thể xử lý hành động hoặc từ chối bằng cơ chế an toàn đã thống nhất.
- Không phát sinh lỗi do ranh giới giữa AI và Battle System.

**Modules involved:** Battle System Lead, AI & Data Lead.

### 5.7. IT-22 — Boss chuyển Phase

**Preconditions**

- Boss có ít nhất hai Phase.
- Ngưỡng HP chuyển Phase đã được xác định.
- Điều kiện chuyển Phase và chiến thuật tương ứng đã được cấu hình.

**Test steps**

1. Khởi tạo Boss Battle ở Phase đầu.
2. Gây Damage để Boss đạt ngưỡng chuyển Phase.
3. Kiểm tra Boss Phase và chiến thuật hiện tại.
4. Tiếp tục xử lý các hành động sau khi chuyển Phase.
5. Kiểm tra việc xử lý lại tại ngưỡng HP.
6. Lưu và khôi phục trạng thái nếu Boss Phase thuộc dữ liệu phải persist.

**Expected results**

- Boss chuyển Phase đúng điều kiện.
- Phase mới và chiến thuật mới được áp dụng nhất quán.
- Không kích hoạt chuyển Phase lặp do cùng một sự kiện.
- Battle Engine không sử dụng hành động hoặc trạng thái không hợp lệ.
- Việc chuyển Phase không làm hỏng thứ tự xử lý lượt.

**Modules involved:** Game & Gameplay Lead, Battle System Lead, AI & Data Lead.

---

## 6. Trách nhiệm giữa các thành viên

| Thành viên | Trách nhiệm |
|---|---|
| Game & Gameplay Lead | Định nghĩa gameplay flow, quy tắc thắng/thua, kết quả mong đợi và tiêu chí nghiệm thu tổng thể |
| Battle System Lead | Xác nhận xử lý lượt, Damage, Battle State, Switch, Battle Result và kết thúc trận |
| Monster & Progression Lead | Xác nhận Monster State, EXP, Level, Stats, Skill, Capture và Evolution |
| AI & Data Lead | Xác nhận hành vi AI, Pattern Analysis, chiến thuật Boss, Phase Transition và fallback |
| UI/Architecture Lead | Xác nhận Controller/Service flow, Save/Load, dữ liệu tích hợp, persistence và UI feedback |

### 6.1. Quy tắc phối hợp

- Game & Gameplay Lead chịu trách nhiệm về tính đúng đắn của kết quả gameplay theo thiết kế đã thống nhất.
- Owner của từng module xác nhận preconditions, test data và expected results liên quan đến module mình.
- Các test liên quan đến nhiều module cần được những owner tương ứng cùng review.
- Người viết implementation không nhất thiết phải là người duy nhất xác nhận test.
- Những bất đồng về luật gameplay phải được giải quyết trước khi test trở thành tiêu chí nghiệm thu chính thức.

---

## 7. Phân biệt các loại Test

| Loại test | Ví dụ | Phạm vi |
|---|---|---|
| Unit Test | Fire đánh Grass có multiplier `2.0` | Một class hoặc một logic nhỏ |
| Integration Test | Thắng Battle → nhận EXP → Level Up → Save → Load | Nhiều module phối hợp |
| UI Test | Bấm nút Attack và thấy kết quả đúng trên màn hình | Tương tác giao diện |
| Regression Test | Chạy lại các test cũ sau khi sửa Battle System | Phát hiện chức năng cũ bị hỏng |

Một Integration Test có thể sử dụng mock hoặc fake cho các dependency không thuộc phạm vi đang kiểm tra. Tuy nhiên, những module và ranh giới được nêu trong mục tiêu test phải được tích hợp thực sự ở mức cần thiết để xác minh contract.

Ví dụ: IT-08 cần xác minh việc Battle Result thực sự dẫn đến cập nhật Progression và dữ liệu được SaveService lưu lại. Chỉ kiểm tra riêng hàm cộng EXP là chưa đủ.

---

## 8. Test Environment và Test Data

### 8.1. Môi trường kiểm thử

- Sử dụng cấu hình test riêng biệt với môi trường gameplay thông thường.
- Khởi tạo GameState độc lập cho từng test case.
- Sử dụng database SQLite riêng nếu game dùng SQLite để persistence.
- Không sử dụng save data thật của người chơi.
- Dọn dẹp database và save slot sau mỗi lần chạy.
- Ghi nhận test logs và lỗi module để hỗ trợ debug.

### 8.2. Kiểm soát randomness

Encounter, Capture và AI có thể chứa yếu tố ngẫu nhiên. Để test tái hiện được:

- Inject random generator hoặc seed nếu kiến trúc cho phép.
- Dùng fixture hoặc mock cho kết quả xác suất khi cần kiểm tra một nhánh cụ thể.
- Không phụ thuộc vào việc Encounter ngẫu nhiên xảy ra đúng lúc.
- Không dựa vào việc AI tình cờ chọn đúng hành động.
- Kiểm tra trực tiếp kết quả đầu ra và trạng thái sau xử lý.

Các test xác suất hoặc hành vi AI qua nhiều lần chạy có thể được bổ sung riêng, nhưng không nên thay thế các integration test xác định có thể tái hiện.

### 8.3. Dữ liệu cần đối chiếu

Tùy test case, cần kiểm tra một hoặc nhiều nhóm dữ liệu:

- Player Profile.
- Team và Collection.
- Monster State, HP, EXP, Level và Stats.
- Inventory và Reward.
- Battle State và Battle Result.
- Gym Badge và World Unlock.
- Boss Phase và AI Pattern Profile.
- Save metadata và dữ liệu GameState đã persist.

---

## 9. Tiêu chí nghiệm thu chung

Một Integration Test chỉ được đánh dấu Pass khi thỏa tất cả điều kiện áp dụng:

1. Preconditions được đáp ứng.
2. Test chạy đến kết thúc mà không có lỗi ngoài dự kiến.
3. Các module trao đổi dữ liệu đúng theo contracts.
4. Expected results được đáp ứng đầy đủ.
5. GameState sau luồng gameplay hợp lệ và nhất quán.
6. Không phát sinh reward, EXP, Badge hoặc sự kiện progression trùng ngoài quy tắc.
7. Dữ liệu persistence chính xác nếu test bao gồm Save/Load.
8. Không có lỗi nghiêm trọng hoặc lỗi làm mất tiến trình.

### 9.1. Quy tắc xử lý kết quả

- **Pass:** Tất cả tiêu chí của test đều đạt.
- **Fail:** Kết quả thực tế khác expected results.
- **Blocked:** Không thể xác định kết quả vì dependency hoặc quy tắc chưa được chốt.
- **Not Run:** Chưa thực thi.

Không được đánh dấu Pass chỉ vì game không crash. Kết quả gameplay và dữ liệu sau xử lý phải được xác minh trực tiếp.

### 9.2. Ưu tiên sửa lỗi

| Mức độ | Ví dụ |
|---|---|
| Critical | Mất hoặc hỏng save; GameState không thể khôi phục; tiến trình không thể tiếp tục |
| High | EXP hoặc reward bị nhân đôi; Gym thắng nhưng không nhận Badge; progression bị sai |
| Medium | AI chọn action không hợp lệ nhưng được fallback an toàn; một trạng thái UI không phản ánh kịp kết quả |
| Low | Thông báo phụ không chính xác nhưng không ảnh hưởng gameplay state |

Mức độ cuối cùng cần được xác nhận theo tác động thực tế và tiêu chuẩn quản lý lỗi của team.

---

## 10. Quy trình thực hiện Integration Test

1. **Review:** Game & Gameplay Lead xác nhận gameplay flow và expected results; các owner Dev Team xác nhận contract liên quan.
2. **Resolve ambiguities:** Chốt các quy tắc chưa rõ trước khi đưa test sang Ready.
3. **Prepare fixtures:** Tạo dữ liệu đầu vào cố định và môi trường test độc lập.
4. **Execute:** Chạy test bằng test runner hoặc theo test steps đã mô tả.
5. **Verify:** Đối chiếu kết quả thực tế với expected results.
6. **Record:** Ghi Pass, Fail hoặc Blocked kèm logs và thông tin tái hiện.
7. **Fix and retest:** Sửa lỗi tại module chịu trách nhiệm và chạy lại test liên quan.
8. **Regression:** Chạy lại những integration test có thể bị ảnh hưởng bởi thay đổi.
9. **Accept:** Game & Gameplay Lead xác nhận kết quả gameplay; các owner xác nhận tính đúng đắn của module liên quan.

### 10.1. Definition of Ready

Một test case được xem là Ready khi:

- Preconditions và test data đủ rõ để tái hiện.
- Expected results không mơ hồ.
- Các quy tắc gameplay liên quan đã được thống nhất.
- Các dependency cần thiết đã được xác định.
- Owner chính và owner phối hợp đã được chỉ định.

### 10.2. Definition of Done

Một test case được xem là hoàn thành khi:

- Test đã được thực thi trong môi trường phù hợp.
- Kết quả thực tế đã được ghi nhận.
- Tất cả expected results đều đạt.
- Nếu có lỗi, lỗi đã được xử lý và test được chạy lại thành công.
- Những regression test liên quan đã được thực thi theo phạm vi ảnh hưởng.

---

## 11. Các quy tắc cần chốt trước khi nghiệm thu

Các mục sau cần được xác nhận trước khi những test liên quan được xem là tiêu chí nghiệm thu cuối cùng:

- [ ] Công thức EXP, điều kiện nhận EXP và quy tắc phân phối EXP.
- [ ] Điều kiện Level Up và cách cập nhật Stats.
- [ ] Điều kiện Evolution và yêu cầu xác nhận từ người chơi.
- [ ] Giới hạn Team, nơi lưu Monster khi Team đã đủ 6 thành viên và quy tắc Collection.
- [ ] Điều kiện Capture, kết quả Battle sau Capture và quy tắc tạo Monster.
- [ ] Quy tắc defeat khi toàn bộ Team bị hạ.
- [ ] Điều kiện nhận Badge và mở khóa vùng tiếp theo.
- [ ] Quy tắc persistence của Gym Progression, Boss Phase và AI Pattern Profile.
- [ ] Hành vi khi Load save bị hỏng hoặc dữ liệu không tương thích.
- [ ] Quy tắc Auto Save và xử lý lỗi khi lưu.
- [ ] Chính sách fallback khi AI trả về action không hợp lệ.
- [ ] Quy tắc xử lý event lặp để tránh cộng EXP hoặc trao reward nhiều lần.

Các mục chưa được thống nhất phải được ghi nhận là dependency hoặc Blocked, không tự suy diễn thành luật gameplay.