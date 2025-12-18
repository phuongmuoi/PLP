# Hướng dẫn chạy Test HappyOrder Login

## ✅ Tests đã hoạt động thành công!

Locators đã được cập nhật để click vào button có class `btn btn-danger btn-auth`.

## Bước 1: Cập nhật dữ liệu đăng nhập

Mở file Excel: `src/test/resources/testdata/LoginData.xlsx`

Thay đổi username và password thành thông tin thật của bạn:

| STT | username | password |
|-----|----------|----------|
| 1 | **your-username** | **your-password** |
| 2 | **user2** | **password2** |
| 3 | **user3** | **password3** |

Lưu file.

## Bước 2: Chạy test

### Chạy HappyOrder login tests:
```bash
mvn clean install -DskipTests
mvn test -DsuiteXmlFile=testng-happyorder.xml
```

### Hoặc chạy test chính:
```bash
mvn clean install -DskipTests
mvn test
```

## Kết quả

Test sẽ:
1. Mở browser Chrome
2. Truy cập https://happyorder.vn/client-area/auth/login
3. Nhập username từ Excel
4. Nhập password từ Excel
5. Click button "ĐĂNG NHẬP" (class="btn btn-danger btn-auth")
6. Chụp screenshot
7. Kiểm tra đã login thành công (redirect đến profile.html)

## Xem kết quả

### Console Output:
```
===============================================
Test Case #1
Testing login with username: testgmailcom
===============================================
Navigated to HappyOrder Login page
Entered username: testgmailcom
Entered password: ****
Clicked login button
Current URL: https://happyorder.vn/client-area/profile.html
✓ Login successful for Test #1
===============================================
```

### Screenshots:
Xem trong thư mục: `test-output/screenshots/`

### TestNG Report:
Mở file: `test-output/index.html` trong browser

## Các Test Cases

Dự án có 3 test cases chính:

1. **testHappyOrderLogin** - Test với tất cả users trong Excel (data-driven)
2. **testSingleLogin** - Test với user đầu tiên trong Excel
3. **testLoginPageLoad** - Kiểm tra trang login có load được không

## Locators đã sử dụng

```java
// Username field
By usernameField = By.cssSelector("input[type='text'], input[type='email']");

// Password field
By passwordField = By.cssSelector("input[type='password']");

// Login button - CẬP NHẬT ĐỂ MATCH VỚI CLASS CỦA BẠN
By loginButton = By.cssSelector("button.btn-danger.btn-auth, button.btn.btn-danger, .btn-auth");
```

## Cấu trúc dự án

```
happyorder/
├── src/
│   ├── main/java/com/happyorder/
│   │   └── pages/
│   │       └── HappyOrderLoginPage.java    ← Page Object cho login
│   └── test/
│       ├── java/com/happyorder/tests/
│       │   └── HappyOrderLoginTest.java    ← Test cases login
│       └── resources/testdata/
│           └── LoginData.xlsx               ← DỮ LIỆU TEST (CẬP NHẬT Ở ĐÂY)
├── testng.xml                               ← Main test suite
├── testng-happyorder.xml                    ← HappyOrder test suite
└── pom.xml                                  ← Maven config
```

## Troubleshooting

### Test fail?
1. Kiểm tra username/password trong Excel có đúng không
2. Xem screenshot trong `test-output/screenshots/`
3. Xem console output

### Build fail?
```bash
mvn clean install -DskipTests -U
```

### Muốn test với browser khác?
```bash
# Firefox
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=firefox

# Edge
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=edge

# Headless (không hiển thị UI)
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=chrome-headless
```

## Thành công! 🎉

Bây giờ bạn có thể:
- Chạy automated tests cho HappyOrder login
- Sử dụng Excel để quản lý test data
- Xem screenshots và reports chi tiết
- Chạy tests với nhiều users khác nhau

Chúc bạn testing vui vẻ!
