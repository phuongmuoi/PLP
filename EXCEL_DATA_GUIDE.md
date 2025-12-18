# Hướng dẫn sử dụng Excel Data-Driven Testing

## File Excel đã tạo

File Excel đã được tạo tại: `src/test/resources/testdata/LoginData.xlsx`

### Cấu trúc dữ liệu:

| STT | username | password |
|-----|----------|----------|
| 1 | admin@example.com | Admin@123 |
| 2 | user01@test.com | User@2024 |
| 3 | testuser@demo.com | Test@Pass123 |

## Các file liên quan

1. **ExcelUtils.java** - Utility class để đọc/ghi Excel
   - Đường dẫn: [src/main/java/com/happyorder/utils/ExcelUtils.java](src/main/java/com/happyorder/utils/ExcelUtils.java)

2. **ExcelDataGenerator.java** - Class tạo file Excel mẫu
   - Đường dẫn: [src/main/java/com/happyorder/utils/ExcelDataGenerator.java](src/main/java/com/happyorder/utils/ExcelDataGenerator.java)

3. **DataDrivenLoginTest.java** - Test class sử dụng dữ liệu từ Excel
   - Đường dẫn: [src/test/java/com/happyorder/tests/DataDrivenLoginTest.java](src/test/java/com/happyorder/tests/DataDrivenLoginTest.java)

## Cách tạo lại file Excel

Chạy lệnh sau để tạo lại file Excel với dữ liệu mẫu:

```bash
mvn compile exec:java -Dexec.mainClass="com.happyorder.utils.ExcelDataGenerator"
```

## Cách chạy Data-Driven Tests

### 1. Chạy tất cả data-driven tests:
```bash
mvn test -DsuiteXmlFile=testng-datadriven.xml
```

### 2. Chạy test class cụ thể:
```bash
mvn test -Dtest=DataDrivenLoginTest
```

### 3. Chạy một test method cụ thể:
```bash
mvn test -Dtest=DataDrivenLoginTest#testLoginWithExcelData
```

## Cách sử dụng ExcelUtils trong code

### 1. Đọc dữ liệu với DataProvider (Recommended)

```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() throws IOException {
    String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
    ExcelUtils excel = new ExcelUtils(filePath, "TestData");

    int rowCount = excel.getRowCount() - 1; // Exclude header
    Object[][] data = new Object[rowCount][3];

    for (int i = 1; i <= rowCount; i++) {
        data[i - 1][0] = excel.getCellData(i, 0); // STT
        data[i - 1][1] = excel.getCellData(i, 1); // username
        data[i - 1][2] = excel.getCellData(i, 2); // password
    }

    excel.close();
    return data;
}

@Test(dataProvider = "loginData")
public void testLogin(String stt, String username, String password) {
    // Your test code here
}
```

### 2. Đọc dữ liệu theo tên cột

```java
String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
ExcelUtils excel = new ExcelUtils(filePath, "TestData");

// Đọc theo tên cột
String username = excel.getCellData(1, "username");
String password = excel.getCellData(1, "password");

excel.close();
```

### 3. Đọc dữ liệu theo số thứ tự

```java
String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
ExcelUtils excel = new ExcelUtils(filePath, "TestData");

// Đọc theo index (row, column)
String username = excel.getCellData(1, 1); // Row 1, Column 1
String password = excel.getCellData(1, 2); // Row 1, Column 2

excel.close();
```

### 4. Ghi dữ liệu vào Excel

```java
String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
ExcelUtils excel = new ExcelUtils(filePath, "TestData");

// Ghi kết quả test vào cột mới
excel.setCellData(1, 3, "PASS"); // Row 1, Column 3, Value "PASS"

excel.close();
```

## Tạo file Excel mới với dữ liệu khác

Bạn có thể tạo file Excel mới bằng cách:

### Cách 1: Sửa đổi ExcelDataGenerator.java

Mở file [ExcelDataGenerator.java](src/main/java/com/happyorder/utils/ExcelDataGenerator.java) và thay đổi:

```java
// Thay đổi tên cột
String[] columns = {"STT", "username", "password", "email", "role"};

// Thay đổi dữ liệu mẫu
Object[][] testData = {
    {1, "admin@example.com", "Admin@123", "admin@test.com", "Admin"},
    {2, "user01@test.com", "User@2024", "user01@test.com", "User"},
    {3, "testuser@demo.com", "Test@Pass123", "testuser@test.com", "Tester"}
};
```

### Cách 2: Tạo file Excel thủ công

1. Mở Excel
2. Tạo file mới với cấu trúc:
   - Dòng đầu tiên: Header (STT, username, password)
   - Các dòng tiếp theo: Dữ liệu test
3. Lưu file dưới dạng `.xlsx`
4. Copy vào thư mục `src/test/resources/testdata/`

## Ví dụ hoàn chỉnh: Login Test với Excel Data

```java
package com.happyorder.tests;

import com.happyorder.base.BaseTest;
import com.happyorder.pages.LoginPage;
import com.happyorder.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
        ExcelUtils excel = new ExcelUtils(filePath, "TestData");
        Object[][] data = excel.getTestData("TestData");
        excel.close();
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String stt, String username, String password) {
        driver.get("https://example.com/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // Verify login successful
        Assert.assertTrue(loginPage.isLoginSuccessful(),
            "Login failed for user: " + username);

        System.out.println("Test #" + stt + " - Login successful: " + username);
    }
}
```

## Lưu ý

1. **Đóng ExcelUtils**: Luôn nhớ gọi `excel.close()` sau khi sử dụng xong
2. **Row Index**: Row index bắt đầu từ 0 (row 0 là header)
3. **Column Index**: Column index bắt đầu từ 0
4. **File Path**: Sử dụng `System.getProperty("user.dir")` để lấy đường dẫn tuyệt đối
5. **Sheet Name**: Đảm bảo tên sheet trong Excel khớp với tên trong code

## Mở rộng

### Thêm nhiều sheet trong cùng một file Excel:

```java
// Sheet 1: Login Data
ExcelUtils loginData = new ExcelUtils(filePath, "LoginData");

// Sheet 2: Product Data
ExcelUtils productData = new ExcelUtils(filePath, "ProductData");
```

### Sử dụng nhiều file Excel:

```java
String loginFile = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
String productFile = System.getProperty("user.dir") + "/src/test/resources/testdata/ProductData.xlsx";

ExcelUtils loginExcel = new ExcelUtils(loginFile, "TestData");
ExcelUtils productExcel = new ExcelUtils(productFile, "Products");
```

## Troubleshooting

### Lỗi: File not found
- Kiểm tra đường dẫn file có đúng không
- Đảm bảo file Excel đã được tạo trong thư mục `src/test/resources/testdata/`

### Lỗi: Sheet not found
- Kiểm tra tên sheet trong Excel
- Tên sheet phân biệt hoa thường

### Lỗi: Invalid cell data
- Kiểm tra định dạng cell trong Excel
- Sử dụng DataFormatter để đọc tất cả các kiểu dữ liệu
