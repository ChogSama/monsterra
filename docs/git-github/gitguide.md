# Git Guide

## 1. Kiểm tra trạng thái

git status

Xem file nào đã thay đổi, file nào chưa được add/commit.

---

## 2. Lấy code mới nhất

git pull

Lấy code mới từ remote về.

---

## 3. Thêm thay đổi

git add .

Thêm tất cả file đã thay đổi vào staging.

Hoặc thêm một file:

git add filename

---

## 4. Commit

git commit -m "Mô tả thay đổi"

Ví dụ:

git commit -m "Fix login bug"

---

## 5. Push

git push

Đẩy commit lên remote.

---

## 6. Checkout / chuyển branch

Xem branch:

git branch

Chuyển branch:

git checkout main

Tạo branch mới:

git checkout -b feature/login

---

## 7. Branch

Tạo branch:

git branch feature/login

Xóa branch:

git branch -d feature/login

---

## 8. Xem thay đổi

git diff

Xem những gì đã thay đổi nhưng chưa commit.

Xem thay đổi đã git add:

git diff --cached

---

## 9. Hủy thay đổi

Hủy thay đổi của một file:

git restore filename

Bỏ file khỏi staging:

git restore --staged filename

---

## 10. Lưu tạm thay đổi

git stash

Lấy lại thay đổi:

git stash pop

---

## 11. Clone project

git clone <url>

Ví dụ:

git clone https://github.com/user/project.git

---

## 12. Git workflow cơ bản

git status
git pull

# Code...

git status
git add .
git status
git commit -m "Mô tả thay đổi"
git push

---

## 13. KHÔNG PUSH CODE NHẠY CẢM

Không commit/push:

- Password
- API key
- Access token
- Private key
- Database password
- File .env
- Secret/config production

Dùng .gitignore:

.env
.env.*
*.key
*.pem
node_modules/

Trước khi push, kiểm tra:

git status
git diff --cached

Nếu lỡ push secret:

1. Đổi/revoke secret ngay.
2. Không chỉ xóa file rồi commit lại.
3. Kiểm tra và xóa secret khỏi Git history nếu cần.

---

## 14. Các lệnh cần nhớ

git status       # Xem trạng thái
git add .        # Add tất cả
git commit -m "" # Commit
git push         # Push lên remote
git pull         # Pull code mới
git checkout     # Chuyển branch
git branch       # Xem branch
git diff         # Xem thay đổi
git stash        # Lưu tạm
git clone        # Clone project

---

## Nhớ:

status → add → commit → push

git status
git add .
git commit -m "message"
git push