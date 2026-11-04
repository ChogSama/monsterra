# Monster & Progression Lead

## Vai trò

Sở hữu Monster domain, Team, Collection và progression logic.

---

## Nhiệm vụ chính

| ID | Task | Deliverable |
|---|---|---|
| C01 | Xây dựng Monster, Species, Stats, Type và Skill models | Monster domain models |
| C02 | Xây dựng Team và Collection | Team/Collection services |
| C03 | Xây dựng EXP, Level Up và stat progression | Progression service |
| C04 | Xây dựng Evolution rules và Evolution Service | Evolution module |
| C05 | Chuẩn bị dữ liệu Monster/Skill mẫu và validation | Seed data và validators |
| C06 | Viết unit tests cho Monster và progression | Monster/progression test suite |

---

## Trách nhiệm chi tiết

### C01 — Monster Domain

- Phân biệt dữ liệu loài Monster với trạng thái của từng cá thể.
- Xây dựng các model cho Species, Stats, Type và Skill.
- Xác định cách lưu level, EXP, HP và các dữ liệu cá thể.
- Thống nhất các ID được sử dụng giữa Battle, Progression và Persistence.

### C02 — Team và Collection

- Quản lý danh sách Monster thuộc người chơi.
- Quản lý Team đang sử dụng.
- Kiểm tra giới hạn số lượng theo gameplay specification.
- Kiểm tra việc thêm, loại bỏ và đổi Monster.
- Ngăn trạng thái Team không hợp lệ.

### C03 — EXP và Level Up

- Tính EXP nhận được.
- Kiểm tra điều kiện Level Up.
- Cập nhật level và chỉ số.
- Xử lý các giới hạn level và EXP.
- Cung cấp kết quả progression cho Battle reward flow.

### C04 — Evolution

- Kiểm tra điều kiện tiến hóa.
- Xác định Species đích.
- Cập nhật dữ liệu Monster sau Evolution.
- Xử lý trường hợp không đủ điều kiện hoặc dữ liệu tiến hóa không hợp lệ.

### C05 — Data và Validation

- Chuẩn bị dữ liệu Monster/Skill phục vụ phát triển.
- Kiểm tra ID trùng, tham chiếu không tồn tại và dữ liệu thiếu.
- Đảm bảo Battle và AI có thể sử dụng cùng một nguồn dữ liệu.

### C06 — Unit Tests

- Kiểm thử Team và Collection.
- Kiểm thử EXP và Level Up.
- Kiểm thử giới hạn progression.
- Kiểm thử Evolution hợp lệ và không hợp lệ.
- Kiểm thử dữ liệu đầu vào không hợp lệ.

---

## Definition of Done

- Monster domain không phụ thuộc vào JavaFX.
- Team và Collection duy trì các invariant đã thống nhất.
- Progression logic có unit tests.
- Dữ liệu mẫu có thể được sử dụng bởi Battle và AI.
- Evolution không làm mất dữ liệu cá thể ngoài những thay đổi được quy định.
- Các service không tự ý ghi database khi chưa thông qua persistence contract.

---

## Phối hợp với các vai trò khác

Monster & Progression Lead làm việc chặt chẽ với:

- **Battle System Lead**: Thống nhất contract cho Monster, Skill, Type dùng trong Battle.
- **Game & Gameplay Lead**: Nhận yêu cầu về Monster state, cung cấp dữ liệu progression.
- **AI & Data Lead**: Cung cấp dữ liệu Monster/Skill cho AI sử dụng.
- **UI/Architecture Lead**: Phối hợp về GameState, Save/Load representation.