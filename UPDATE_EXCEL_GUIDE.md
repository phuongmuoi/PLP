# Hướng dẫn Cập nhật Excel với Message Tiếng Việt

## ✅ Logic Pass/Failed đã được cập nhật

### Khi nào test Result = Pass?

#### 1. **Login Thành Công** (Valid credentials):
- Actual: Login successful, redirect to profile page
- Expected chứa: "success", "login successful", "profile", "thành công"
- → **Result = Pass**

#### 2. **Login Thất Bại Đúng như Mong Đợi** (Invalid credentials):
- Actual: "Tên tài khoản hoặc mật khẩu không chính xác"
- Expected chứa: "error", "invalid", "không chính xác", "sai", "lỗi"
- → **Result = Pass**

### Hành Vi Thực Tế của HappyOrder

**Quan trọng**: HappyOrder **KHÔNG hiển thị** message lỗi trên màn hình khi nhập sai.

Thay vào đó:
- ✅ **Login thành công**: Redirect sang `/profile.html`
- ❌ **Login thất bại**: Ở lại trang `/auth/login` (không đổi URL)

Framework của chúng ta kiểm tra **URL thay đổi hay không** để xác định kết quả.

## 📊 Cấu trúc Excel Mới (Tiếng Việt)

### TC001 - Valid Login Test
| Column | Value |
|--------|-------|
| Title | TC001 - Valid Login Test |
| Step | 1. Truy cập trang đăng nhập<br>2. Nhập username hợp lệ<br>3. Nhập password hợp lệ<br>4. Click button ĐĂNG NHẬP |
| UserName | testuser@gmail.com |
| Password | Test@123 |
| Expected Message | Đăng nhập thành công, chuyển đến trang profile |
| Result | (tự động fill: Pass/Failed) |

### TC002 - Invalid Username Test
| Column | Value |
|--------|-------|
| Title | TC002 - Invalid Username Test |
| Step | 1. Truy cập trang đăng nhập<br>2. Nhập username không đúng<br>3. Nhập password hợp lệ<br>4. Click button ĐĂNG NHẬP |
| UserName | wronguser@gmail.com |
| Password | Test@123 |
| Expected Message | Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác |
| Result | (tự động fill: Pass/Failed) |

### TC003 - Invalid Password Test
| Column | Value |
|--------|-------|
| Title | TC003 - Invalid Password Test |
| Step | 1. Truy cập trang đăng nhập<br>2. Nhập username hợp lệ<br>3. Nhập password không đúng<br>4. Click button ĐĂNG NHẬP |
| UserName | testuser@gmail.com |
| Password | WrongPass123 |
| Expected Message | Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác |
| Result | (tự động fill: Pass/Failed) |

## 🔄 Cách tạo Excel mới

### Bước 1: Đóng file Excel nếu đang mở

### Bước 2: Chạy lệnh tạo Excel
```bash
mvn clean compile exec:java -Dexec.mainClass="com.happyorder.utils.ExcelDataGenerator"
```

### Bước 3: Mở Excel và cập nhật
Mở `src/test/resources/testdata/LoginData.xlsx` và:
- Thay **testuser@gmail.com** bằng username thật
- Thay **Test@123** bằng password thật
- Giữ nguyên Expected Message hoặc điều chỉnh theo ý muốn

### Bước 4: Chạy test
```bash
mvn clean compile && mvn test
```

## 📝 Ví dụ Expected Message

### Cho test Valid Login:
- ✅ "Đăng nhập thành công, chuyển đến trang profile"
- ✅ "Login successful"
- ✅ "Login thành công"

### Cho test Invalid Login:
- ✅ "Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác"
- ✅ "Error: Tên tài khoản hoặc mật khẩu không chính xác"
- ✅ "Hiển thị lỗi: username hoặc password không đúng"
- ✅ "Error message: Invalid username or password"

## 🎯 Logic Chi Tiết

```java
// Kiểm tra xem có redirect hay không
if (loginPage.isLoginSuccessful()) {
    // URL đã thay đổi (redirect) = Login thành công
    if (expectedMessage.contains("success") ||
        expectedMessage.contains("profile") ||
        expectedMessage.contains("thành công")) {
        testResult = "Pass"; // ✅ Login thành công như mong đợi
    }
} else {
    // URL không đổi (ở lại login page) = Login thất bại
    if (expectedMessage.contains("error") ||
        expectedMessage.contains("invalid") ||
        expectedMessage.contains("không chính xác") ||
        expectedMessage.contains("lỗi")) {
        testResult = "Pass"; // ✅ Login thất bại như mong đợi
    }
}
```

## 📖 Console Output Mẫu

### Test Invalid Login (Pass):
```
===============================================
Test Case: TC002 - Invalid Username Test
Steps:
1. Navigate to login page
2. Enter invalid username
3. Enter valid password
4. Click login button
Username: wronguser@gmail.com
Expected: Error message: Invalid username or password
===============================================
Navigated to HappyOrder Login page
Entered username: wronguser@gmail.com
Entered password: ****
Clicked login button
Current URL: https://happyorder.vn/client-area/auth/login
✗ Login failed: Stayed on login page (URL: .../auth/login)
✓ Test passed: Login correctly rejected invalid credentials
Expected: Error message: Invalid username or password
Actual: Login failed as expected: Invalid credentials, stayed on login page
Result: Pass ✅
===============================================
```

## 🚀 Quick Commands

```bash
# 1. Đóng Excel nếu đang mở

# 2. Tạo Excel mới
mvn compile exec:java -Dexec.mainClass="com.happyorder.utils.ExcelDataGenerator"

# 3. Sửa username/password trong Excel

# 4. Chạy test
mvn clean compile && mvn test

# 5. Mở Excel xem kết quả trong cột "Result(pass/Failed)"
```

## ⚠️ Lưu ý

1. **Đóng Excel trước khi generate**: File Excel phải được đóng mới có thể tạo lại
2. **Expected Message phải chứa từ khóa**: Để test Pass, expected message phải chứa các từ khóa như "không chính xác", "sai", "error", "invalid"
3. **Cả tiếng Việt và tiếng Anh đều work**: Logic hỗ trợ cả 2 ngôn ngữ

Hoàn thành! 🎉
