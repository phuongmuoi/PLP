# Hướng dẫn Test Login HappyOrder

## Tổng quan

Dự án đã được cấu hình để tự động test chức năng đăng nhập vào trang HappyOrder sử dụng dữ liệu từ file Excel.

## Các file đã tạo

### 1. Page Object
- **[HappyOrderLoginPage.java](src/main/java/com/happyorder/pages/HappyOrderLoginPage.java)**
  - Class chứa tất cả actions và locators cho trang login
  - Hỗ trợ multiple locator strategies để tìm elements
  - Xử lý JavaScript-heavy pages

### 2. Test Class
- **[HappyOrderLoginTest.java](src/test/java/com/happyorder/tests/HappyOrderLoginTest.java)**
  - Test cases để đăng nhập với dữ liệu từ Excel
  - Tự động chụp screenshot sau mỗi test
  - In ra thông tin chi tiết trong console

### 3. TestNG Configuration
- **[testng-happyorder.xml](testng-happyorder.xml)** - Suite riêng cho HappyOrder tests
- **[testng.xml](testng.xml)** - Main suite đã được update

## Dữ liệu test

File Excel: [src/test/resources/testdata/LoginData.xlsx](src/test/resources/testdata/LoginData.xlsx)

| STT | username | password |
|-----|----------|----------|
| 1 | admin@example.com | Admin@123 |
| 2 | user01@test.com | User@2024 |
| 3 | testuser@demo.com | Test@Pass123 |

**Lưu ý**: Đây là dữ liệu mẫu. Bạn cần cập nhật username và password thực tế trong file Excel.

## Cách chạy tests

### 1. Chạy tất cả HappyOrder tests:
```bash
mvn test -DsuiteXmlFile=testng-happyorder.xml
```

### 2. Chạy chỉ HappyOrder login test:
```bash
mvn test -Dtest=HappyOrderLoginTest
```

### 3. Chạy một test method cụ thể:

**Test với data từ Excel (tất cả 3 dòng)**:
```bash
mvn test -Dtest=HappyOrderLoginTest#testHappyOrderLogin
```

**Test với user đầu tiên**:
```bash
mvn test -Dtest=HappyOrderLoginTest#testSingleLogin
```

**Kiểm tra trang login có load được không**:
```bash
mvn test -Dtest=HappyOrderLoginTest#testLoginPageLoad
```

### 4. Chạy với browser khác:
```bash
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=firefox
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=edge
```

### 5. Chạy headless (không hiển thị browser):
```bash
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=chrome-headless
```

## Cập nhật dữ liệu login

### Cách 1: Sửa trực tiếp file Excel
1. Mở file `src/test/resources/testdata/LoginData.xlsx`
2. Cập nhật username và password
3. Lưu file
4. Chạy lại test

### Cách 2: Tạo file Excel mới bằng code
Sửa file [ExcelDataGenerator.java](src/main/java/com/happyorder/utils/ExcelDataGenerator.java):

```java
Object[][] testData = {
    {1, "your-username1@example.com", "YourPassword1"},
    {2, "your-username2@example.com", "YourPassword2"},
    {3, "your-username3@example.com", "YourPassword3"}
};
```

Sau đó chạy:
```bash
mvn compile exec:java -Dexec.mainClass="com.happyorder.utils.ExcelDataGenerator"
```

## Cấu trúc HappyOrderLoginPage

### Các method chính:

```java
// Navigate tới trang login
loginPage.navigateToLoginPage();

// Nhập username
loginPage.enterUsername("username");

// Nhập password
loginPage.enterPassword("password");

// Click nút đăng nhập
loginPage.clickLoginButton();

// Login trong một bước
loginPage.login("username", "password");

// Kiểm tra login thành công
boolean isSuccess = loginPage.isLoginSuccessful();

// Lấy thông tin lỗi
String error = loginPage.getErrorMessage();
```

## Locators được sử dụng

Page Object sử dụng **multiple strategies** để tìm elements:

### Username field:
```java
// Primary locator
By usernameField = By.xpath("//input[@placeholder='Tên đăng nhập' or @placeholder='Username' or @type='text' or @name='username' or @id='username']");

// Alternative locator
By usernameFieldAlt = By.cssSelector("input[type='text'], input[name='username'], input[id='username']");
```

### Password field:
```java
// Primary locator
By passwordField = By.xpath("//input[@placeholder='Mật khẩu' or @placeholder='Password' or @type='password' or @name='password' or @id='password']");

// Alternative locator
By passwordFieldAlt = By.cssSelector("input[type='password'], input[name='password'], input[id='password']");
```

### Login button:
```java
// Primary locator
By loginButton = By.xpath("//button[contains(text(), 'ĐĂNG NHẬP') or contains(text(), 'Đăng nhập') or @type='submit']");

// Alternative locator
By loginButtonAlt = By.cssSelector("button[type='submit'], button.login-btn, .btn-login");
```

## Điều chỉnh locators

Nếu test không tìm thấy elements, bạn cần cập nhật locators:

### Bước 1: Kiểm tra HTML
1. Mở trang https://happyorder.vn/client-area/auth/login trong browser
2. Nhấn F12 để mở Developer Tools
3. Click vào icon "Select element" (Ctrl+Shift+C)
4. Click vào username field, password field, và login button
5. Xem HTML của các elements này

### Bước 2: Cập nhật locators
Mở [HappyOrderLoginPage.java](src/main/java/com/happyorder/pages/HappyOrderLoginPage.java) và cập nhật:

```java
// Ví dụ: nếu username field có id="email"
private By usernameField = By.id("email");

// Ví dụ: nếu password field có id="pass"
private By passwordField = By.id("pass");

// Ví dụ: nếu button có class="btn-primary"
private By loginButton = By.className("btn-primary");
```

## Screenshots

Tất cả screenshots được lưu tại: `test-output/screenshots/`

Screenshots được tự động chụp:
- Sau mỗi lần login attempt
- Khi có lỗi xảy ra
- Khi test page load

Tên file format: `HappyOrder_Login_Test_{STT}_{timestamp}.png`

## Test Reports

Sau khi chạy test, xem reports tại:
- **TestNG HTML Report**: `test-output/index.html`
- **Console Output**: Hiển thị real-time trong terminal
- **Screenshots**: `test-output/screenshots/`

## Ví dụ Output

Khi chạy test, bạn sẽ thấy output như sau:

```
===============================================
Test Case #1
Testing login with username: admin@example.com
===============================================
Navigated to HappyOrder Login page: https://happyorder.vn/client-area/auth/login
Entered username: admin@example.com
Entered password: ****
Clicked login button
Screenshot saved: C:\Project\happyorder/test-output/screenshots/HappyOrder_Login_Test_1_20251117_230123.png
Current URL: https://happyorder.vn/client-area/dashboard
Page Title: Dashboard - HappyOrder
✓ Login successful for Test #1
===============================================
```

## Troubleshooting

### 1. Test không tìm thấy elements
**Giải pháp**:
- Kiểm tra trang có load xong chưa (tăng wait time)
- Cập nhật locators theo HTML thực tế
- Chạy test `testLoginPageLoad` để xem page có load được không

### 2. Login không thành công
**Giải pháp**:
- Kiểm tra username/password trong Excel có đúng không
- Xem screenshot để kiểm tra giao diện
- Kiểm tra có captcha hay 2FA không

### 3. Browser không mở
**Giải pháp**:
```bash
# Update dependencies
mvn clean install -U

# Thử browser khác
mvn test -Dtest=HappyOrderLoginTest -Dbrowser=firefox
```

### 4. Element not clickable
**Giải pháp**: Page Object đã xử lý bằng:
- Wait for element clickable
- Try alternative locators
- Use JavaScript click as fallback

### 5. JavaScript không load
**Giải pháp**:
- Page Object đã thêm wait 3 giây sau khi load page
- Tăng thời gian wait trong `navigateToLoginPage()` method

## Mở rộng

### Thêm test cases mới:

```java
@Test(description = "Test login with invalid credentials")
public void testInvalidLogin() {
    HappyOrderLoginPage loginPage = new HappyOrderLoginPage(driver);
    loginPage.login("invalid@email.com", "wrongpassword");

    Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
        "Error message should be displayed");
}
```

### Thêm assertions:

```java
@Test(dataProvider = "loginData")
public void testLoginWithValidation(String stt, String username, String password) {
    HappyOrderLoginPage loginPage = new HappyOrderLoginPage(driver);
    loginPage.login(username, password);

    // Assert login successful
    Assert.assertTrue(loginPage.isLoginSuccessful(),
        "Login should be successful for user: " + username);

    // Assert redirected to correct page
    Assert.assertTrue(loginPage.getCurrentUrl().contains("dashboard"),
        "Should redirect to dashboard");
}
```

## Best Practices

1. **Luôn update dữ liệu test** trong Excel trước khi chạy
2. **Kiểm tra screenshots** sau khi test để verify UI
3. **Đọc console output** để hiểu test flow
4. **Sử dụng testLoginPageLoad** để debug khi có vấn đề
5. **Chạy testSingleLogin** trước khi chạy full data-driven test

## Liên hệ

Nếu gặp vấn đề, kiểm tra:
1. Console output
2. Screenshots trong `test-output/screenshots/`
3. TestNG report trong `test-output/index.html`
4. Page source được in ra khi có lỗi
