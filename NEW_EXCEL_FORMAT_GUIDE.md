# Hướng dẫn Format Excel Mới

## ✅ Cấu trúc Excel đã cập nhật

File Excel giờ có cấu trúc chi tiết hơn để quản lý test cases:

### Columns (Cột):

| Column | Tên Tiếng Việt | Mô tả |
|--------|----------------|-------|
| **Title** | Tiêu đề Test Case | Tên và ID của test case (VD: TC001 - Valid Login Test) |
| **Step** | Các bước thực hiện | Danh sách các bước để thực hiện test, mỗi bước 1 dòng |
| **UserName** | Tên đăng nhập | Username để đăng nhập |
| **Password** | Mật khẩu | Password để đăng nhập |
| **Expected Message** | Kết quả mong đợi | Message hoặc kết quả mong muốn sau khi thực hiện test |
| **Result(pass/Failed)** | Kết quả | Pass/Failed - Tự động được fill sau khi chạy test |

## 📊 Dữ liệu mẫu

File Excel mặc định có 3 test cases mẫu:

### TC001 - Valid Login Test
- **Steps**:
  1. Navigate to login page
  2. Enter valid username
  3. Enter valid password
  4. Click login button
- **UserName**: testuser@gmail.com
- **Password**: Test@123
- **Expected**: Login successful, redirect to profile page

### TC002 - Invalid Username Test
- **Steps**: Test với username sai
- **UserName**: wronguser@gmail.com
- **Password**: Test@123
- **Expected**: Error message: Invalid username or password

### TC003 - Invalid Password Test
- **Steps**: Test với password sai
- **UserName**: testuser@gmail.com
- **Password**: WrongPass123
- **Expected**: Error message: Invalid username or password

## 🔧 Cách sử dụng

### 1. Tạo file Excel mới
```bash
mvn compile exec:java -Dexec.mainClass="com.happyorder.utils.ExcelDataGenerator"
```

### 2. Chỉnh sửa file Excel
Mở `src/test/resources/testdata/LoginData.xlsx` và:
- Cập nhật **UserName** và **Password** thật
- Chỉnh sửa **Steps** nếu cần
- Cập nhật **Expected Message** cho phù hợp
- **Không** cần điền cột **Result** - sẽ tự động được fill

### 3. Chạy test
```bash
mvn clean compile
mvn test
```

### 4. Xem kết quả
- Console output hiển thị chi tiết từng test
- Cột **Result** trong Excel được tự động update: Pass/Failed
- Screenshots: `test-output/screenshots/`

## 📝 Output Console Mẫu

```
===============================================
Test Case: TC001 - Valid Login Test
Steps:
1. Navigate to login page
2. Enter valid username
3. Enter valid password
4. Click login button
Username: testuser@gmail.com
Expected: Login successful, redirect to profile page
===============================================
Navigated to HappyOrder Login page
Entered username: testuser@gmail.com
Entered password: ****
Clicked login button
Current URL: https://happyorder.vn/client-area/profile.html
Page Title: Happy Order Client
✓ Login successful, redirect to https://happyorder.vn/client-area/profile.html
Expected: Login successful, redirect to profile page
Actual: Login successful, redirect to https://happyorder.vn/client-area/profile.html
Result: Pass
===============================================
```

## 🎯 Logic xác định Pass/Failed

Test tự động xác định Pass/Failed dựa trên:

### Pass nếu:
1. **Valid Login Test**:
   - Login thành công
   - Redirect đến trang profile
   - Expected message chứa "profile"

2. **Invalid Login Test**:
   - Hiển thị error message
   - Expected message chứa "error" hoặc "invalid"

### Failed nếu:
- Kết quả không khớp với expected
- Có exception xảy ra
- Login không thành công khi mong đợi thành công
- Không có error khi mong đợi error

## ✏️ Thêm Test Case mới

Trong Excel, thêm dòng mới với format:

| Title | Step | UserName | Password | Expected Message | Result |
|-------|------|----------|----------|------------------|--------|
| TC004 - Empty Username | 1. Navigate to login<br>2. Leave username empty<br>3. Enter password<br>4. Click login | | Test@123 | Error: Username is required | |

**Lưu ý**:
- Để xuống dòng trong Step column, dùng Alt+Enter trong Excel
- Cột Result để trống, sẽ tự động fill

## 🔄 Update Result vào Excel

Test tự động ghi kết quả vào Excel sau khi chạy:
- ✅ **Pass**: Nếu test thành công như mong đợi
- ❌ **Failed**: Nếu test không đúng hoặc có lỗi

File Excel sẽ được update ngay sau khi test chạy xong.

## 📂 File liên quan

- **Excel Template**: `src/test/resources/testdata/LoginData.xlsx`
- **Generator**: `ExcelDataGenerator.java`
- **Reader**: `ExcelUtils.java`
- **Test Class**: `HappyOrderLoginTest.java`

## 💡 Tips

1. **Backup Excel**: Backup file Excel trước khi chạy test
2. **Multiple Scenarios**: Tạo nhiều dòng cho các scenarios khác nhau
3. **Clear Results**: Xóa cột Result trước khi chạy lại test
4. **Screenshots**: Mỗi test tự động chụp screenshot với tên = Title

## 🚀 Quick Start

```bash
# 1. Tạo Excel mới
mvn compile exec:java -Dexec.mainClass="com.happyorder.utils.ExcelDataGenerator"

# 2. Sửa Excel - thay username/password thật

# 3. Chạy test
mvn clean compile && mvn test

# 4. Mở Excel xem kết quả trong cột "Result(pass/Failed)"
```

Hoàn thành! Framework giờ đã hỗ trợ format Excel chi tiết hơn! 🎉
