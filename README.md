# HappyOrder - Selenium TestNG Automation Framework

Framework tự động hóa kiểm thử sử dụng Selenium WebDriver và TestNG.

## ✅ Status: All Tests Pass (3/3)

**Latest Test Results:**
- TC001 - Valid Login Test: ✅ Pass
- TC002 - Invalid Username Test: ✅ Pass
- TC003 - Invalid Password Test: ✅ Pass

**Build Status:** ✅ BUILD SUCCESS
**Tests:** 3 run, 0 failures, 0 errors, 0 skipped

📖 **Xem chi tiết:** [ALL_TESTS_PASS_SUCCESS.md](ALL_TESTS_PASS_SUCCESS.md) | [FINAL_SOLUTION.md](FINAL_SOLUTION.md)

## Công nghệ sử dụng

- Java 11
- Selenium WebDriver 4.16.1
- TestNG 7.8.0
- WebDriverManager 5.6.3
- Maven
- ExtentReports 5.1.1
- Log4j2 2.22.0

## Cấu trúc dự án

```
happyorder/
├── src/
│   ├── main/
│   │   ├── java/com/happyorder/
│   │   │   ├── base/
│   │   │   │   └── BaseTest.java              # Class cơ sở cho tất cả test
│   │   │   ├── pages/
│   │   │   │   ├── GoogleHomePage.java        # Page Object mẫu
│   │   │   │   └── HappyOrderLoginPage.java   # Page Object cho HappyOrder login
│   │   │   └── utils/
│   │   │       ├── WebElementUtils.java       # Utilities cho WebElement
│   │   │       ├── ScreenshotUtils.java       # Chụp screenshot
│   │   │       ├── ExcelUtils.java            # Đọc/ghi Excel
│   │   │       └── ExcelDataGenerator.java    # Tạo file Excel mẫu
│   │   └── resources/
│   │       └── log4j2.xml                      # Cấu hình logging
│   └── test/
│       ├── java/com/happyorder/tests/
│       │   ├── GoogleSearchTest.java           # Test case mẫu
│       │   ├── SampleTest.java                 # Test case đơn giản
│       │   ├── DataDrivenLoginTest.java        # Data-driven test example
│       │   └── HappyOrderLoginTest.java        # Test login HappyOrder
│       └── resources/testdata/
│           └── LoginData.xlsx                  # Dữ liệu test (Excel)
├── testng.xml                                  # TestNG suite chính (HappyOrder Login)
├── testng-happyorder.xml                       # HappyOrder login tests (alternative)
├── README.md                                   # Tài liệu chính
├── HOW_TO_RUN.md                               # Hướng dẫn nhanh
├── EXCEL_DATA_GUIDE.md                         # Hướng dẫn Excel data-driven
├── HAPPYORDER_LOGIN_GUIDE.md                   # Hướng dẫn test HappyOrder
└── pom.xml                                     # Maven configuration
```

## Yêu cầu

- JDK 11 trở lên
- Maven 3.6+
- Chrome/Firefox/Edge browser

## Cài đặt

1. Clone hoặc tải dự án về máy

2. Mở terminal tại thư mục dự án

3. Cài đặt dependencies:
```bash
mvn clean install
```

## Chạy test

### Cách 1: Chạy test chính (Recommended)
```bash
mvn clean install -DskipTests
mvn test
```

### Cách 2: Chạy HappyOrder Login Tests với suite riêng
```bash
mvn test -DsuiteXmlFile=testng-happyorder.xml
```

### Chạy với trình duyệt cụ thể
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
mvn test -Dbrowser=chrome-headless
```

## Các tính năng chính

### 1. BaseTest Class
- Quản lý khởi tạo và đóng WebDriver
- Hỗ trợ nhiều trình duyệt (Chrome, Firefox, Edge)
- Headless mode
- Cấu hình timeout

### 2. WebElementUtils
- Wait cho elements (visible, clickable)
- Click, sendKeys với wait tự động
- Xử lý dropdown
- JavaScript executor
- Scroll to element

### 3. Page Object Model
- Tách biệt locators và actions
- Dễ bảo trì và mở rộng
- Tái sử dụng code

### 4. TestNG Features
- Test prioritization
- Test grouping (smoke, regression)
- Parallel execution
- Data-driven testing support
- Detailed reporting

### 5. Excel Data-Driven Testing
- Đọc dữ liệu test từ Excel
- DataProvider tích hợp với TestNG
- Hỗ trợ nhiều sheet và file Excel
- Dễ dàng cập nhật test data

### 6. HappyOrder Login Automation
- Page Object cho trang login HappyOrder
- Tự động đăng nhập với dữ liệu từ Excel
- Screenshot tự động sau mỗi test
- Hỗ trợ multiple locator strategies

## Thêm test case mới

### Bước 1: Tạo Page Object
```java
package com.happyorder.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}
```

### Bước 2: Tạo Test Class
```java
package com.happyorder.tests;

import com.happyorder.base.BaseTest;
import com.happyorder.pages.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1, groups = {"smoke"})
    public void testValidLogin() {
        driver.get("https://example.com/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("testuser", "password123");
        // Add assertions
    }
}
```

## Test Reports

Sau khi chạy test, báo cáo sẽ được tạo tại:
- TestNG Report: `test-output/index.html`
- Screenshots: `test-output/screenshots/`
- Logs: `logs/automation.log`

## Cấu hình Browser

Thay đổi browser trong file `testng.xml`:
```xml
<parameter name="browser" value="chrome"/>
```

Các giá trị hỗ trợ:
- `chrome`
- `firefox`
- `edge`
- `chrome-headless`

## Tips & Best Practices

1. Luôn sử dụng WebDriverWait thay vì Thread.sleep()
2. Sử dụng Page Object Model cho maintainability
3. Đặt tên test methods rõ ràng và mô tả
4. Sử dụng groups để tổ chức tests
5. Chụp screenshot khi test fail
6. Sử dụng DataProvider cho data-driven tests

## Troubleshooting

### WebDriver không khởi động
- Kiểm tra kết nối internet (WebDriverManager cần tải driver)
- Đảm bảo browser đã được cài đặt
- Thử chạy với browser khác

### Tests bị fail do timeout
- Tăng timeout trong BaseTest
- Kiểm tra kết nối mạng
- Kiểm tra hiệu năng của website đang test

### Maven build fails
```bash
mvn clean install -U
```

## Tài liệu bổ sung

- **[HOW_TO_RUN.md](HOW_TO_RUN.md)** - Hướng dẫn nhanh và đơn giản nhất
- **[HAPPYORDER_LOGIN_GUIDE.md](HAPPYORDER_LOGIN_GUIDE.md)** - Hướng dẫn chi tiết test login HappyOrder
- **[EXCEL_DATA_GUIDE.md](EXCEL_DATA_GUIDE.md)** - Hướng dẫn sử dụng Excel data-driven testing

## Quick Start - Test HappyOrder Login

1. **Cập nhật dữ liệu test**:
   - Mở file `src/test/resources/testdata/LoginData.xlsx`
   - Nhập username và password thực tế của bạn
   - Lưu file

2. **Chạy test**:
   ```bash
   mvn clean install -DskipTests
   mvn test
   ```

3. **Xem kết quả**:
   - Console output: Hiển thị real-time
   - Screenshots: `test-output/screenshots/`
   - TestNG Report: `test-output/index.html`

## Liên hệ

Nếu có vấn đề hoặc câu hỏi, vui lòng tạo issue trong repository.
