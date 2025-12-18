# Hướng dẫn Làm cho TẤT CẢ Test Cases PASS

## 🎯 Mục tiêu: Result = Pass cho cả 3 test cases

## 📋 Bảng hướng dẫn cập nhật Excel

| Test Case | UserName | Password | Expected Message | Kết quả mong đợi |
|-----------|----------|----------|------------------|------------------|
| **TC001** | ✅ Username ĐÚNG của bạn | ✅ Password ĐÚNG của bạn | Đăng nhập thành công, chuyển đến trang profile | Login thành công → **Pass** ✅ |
| **TC002** | ❌ Username SAI (bất kỳ) | ❌ Password bất kỳ | Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác | Nhận được error message → **Pass** ✅ |
| **TC003** | ✅ Username ĐÚNG của bạn | ❌ Password SAI | Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác | Nhận được error message → **Pass** ✅ |

## 📝 Cách cập nhật file Excel

### Bước 1: Mở Excel
Mở file: `src/test/resources/testdata/LoginData.xlsx`

### Bước 2: Cập nhật từng dòng

#### Row 2 - TC001 (Valid Login Test):
```
Title: TC001 - Valid Login Test
Step: (giữ nguyên)
UserName: your-real-username@gmail.com    ← THAY BẰNG USERNAME ĐÚNG
Password: YourRealPassword123             ← THAY BẰNG PASSWORD ĐÚNG
Expected Message: Đăng nhập thành công, chuyển đến trang profile
Result: (để trống - tự động fill)
```

#### Row 3 - TC002 (Invalid Username Test):
```
Title: TC002 - Invalid Username Test
Step: (giữ nguyên)
UserName: wronguser999@gmail.com          ← USERNAME SAI BẤT KỲ
Password: AnyPassword123                  ← PASSWORD BẤT KỲ
Expected Message: Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác
Result: (để trống - tự động fill)
```

#### Row 4 - TC003 (Invalid Password Test):
```
Title: TC003 - Invalid Password Test
Step: (giữ nguyên)
UserName: your-real-username@gmail.com    ← THAY BẰNG USERNAME ĐÚNG
Password: WrongPassword999                ← PASSWORD SAI BẤT KỲ
Expected Message: Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác
Result: (để trống - tự động fill)
```

### Bước 3: Lưu file Excel

### Bước 4: Chạy test
```bash
mvn clean compile && mvn test
```

### Bước 5: Kiểm tra kết quả
Mở lại Excel và xem cột **Result(pass/Failed)** - tất cả phải là **Pass**

## ✅ Ví dụ cụ thể (giả sử username thật là test@happyorder.vn, password là Test123)

### Excel sau khi cập nhật:

| Title | Step | UserName | Password | Expected Message | Result |
|-------|------|----------|----------|------------------|--------|
| TC001 - Valid Login Test | (steps) | **test@happyorder.vn** | **Test123** | Đăng nhập thành công, chuyển đến trang profile | Pass ✅ |
| TC002 - Invalid Username Test | (steps) | **fake@example.com** | **AnyPass123** | Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác | Pass ✅ |
| TC003 - Invalid Password Test | (steps) | **test@happyorder.vn** | **WrongPass999** | Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác | Pass ✅ |

## 🎯 Logic Pass/Failed

### TC001 Pass khi:
- Login thành công
- Redirect đến URL chứa "profile"
- ✅ Result = **Pass**

### TC002 Pass khi:
- Login thất bại (vì username sai)
- Nhận message: "Tên tài khoản hoặc mật khẩu không chính xác"
- Expected chứa "không chính xác"
- ✅ Result = **Pass**

### TC003 Pass khi:
- Login thất bại (vì password sai)
- Nhận message: "Tên tài khoản hoặc mật khẩu không chính xác"
- Expected chứa "không chính xác"
- ✅ Result = **Pass**

## 📊 Console Output mẫu (tất cả Pass)

```
===============================================
Test Case: TC001 - Valid Login Test
...
✓ Login successful, redirect to https://happyorder.vn/client-area/profile.html
Result: Pass ✅
===============================================

===============================================
Test Case: TC002 - Invalid Username Test
...
✗ Error: Tên tài khoản hoặc mật khẩu không chính xác
Result: Pass ✅
===============================================

===============================================
Test Case: TC003 - Invalid Password Test
...
✗ Error: Tên tài khoản hoặc mật khẩu không chính xác
Result: Pass ✅
===============================================

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS ✅
```

## ⚠️ Lưu ý QUAN TRỌNG

1. **TC001 cần credentials ĐÚNG**:
   - Nếu username/password sai → Test Failed ❌
   - Chỉ dùng tài khoản thật, đã đăng ký trên HappyOrder

2. **TC002 cần username SAI**:
   - Dùng email bất kỳ chưa đăng ký
   - VD: wronguser999@test.com, fake123@example.com

3. **TC003 cần password SAI**:
   - Dùng username ĐÚNG
   - Dùng password SAI bất kỳ
   - VD: WrongPass999, FakePassword123

4. **Expected Message phải chứa từ khóa đúng**:
   - Valid login: "thành công", "success", "profile"
   - Invalid login: "không chính xác", "sai", "error", "invalid"

## 🚀 Quick Checklist

- [ ] Đã cập nhật TC001 với username/password ĐÚNG
- [ ] Đã cập nhật TC002 với username SAI
- [ ] Đã cập nhật TC003 với password SAI (username ĐÚNG)
- [ ] Đã lưu file Excel
- [ ] Đã chạy: `mvn clean compile && mvn test`
- [ ] Kiểm tra tất cả Result = Pass trong Excel

## 🎉 Kết quả cuối cùng

Sau khi hoàn thành, mở Excel sẽ thấy:

```
TC001 - Valid Login Test              → Pass ✅
TC002 - Invalid Username Test         → Pass ✅
TC003 - Invalid Password Test         → Pass ✅

Total: 3/3 Pass (100%)
```

Chúc bạn thành công! 🎊
