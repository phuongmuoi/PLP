# Giải thích: Tại sao test chạy nhiều lần?

## Vấn đề ban đầu

Ban đầu có **3 test methods** trong HappyOrderLoginTest.java:

1. **testHappyOrderLogin** - Data-driven test (chạy với mỗi dòng trong Excel)
2. **testSingleLogin** - Test với user đầu tiên
3. **testLoginPageLoad** - Test load trang

→ Khi chạy `mvn test`, cả 3 methods đều được thực thi = 3 lần test

## Đã sửa

Cập nhật `testng.xml` để **chỉ chạy 1 method**:

```xml
<methods>
    <!-- Chỉ chạy test data-driven với Excel -->
    <include name="testHappyOrderLogin"/>
</methods>
```

## Kết quả hiện tại

✅ **Tests run: 1** (chỉ chạy testHappyOrderLogin)

## Nếu vẫn thấy chạy nhiều lần

Kiểm tra file Excel có bao nhiêu dòng data:

| STT | username | password |
|-----|----------|----------|
| 1 | user1 | pass1 | ← Test sẽ chạy cho dòng này
| 2 | user2 | pass2 | ← Test sẽ chạy cho dòng này
| 3 | user3 | pass3 | ← Test sẽ chạy cho dòng này

→ Nếu có 3 dòng data = test chạy 3 lần (1 lần cho mỗi user)

## Cách chỉ test với 1 user

### Cách 1: Xóa dòng thừa trong Excel (KHUYẾN NGHỊ)
Mở `src/test/resources/testdata/LoginData.xlsx` và chỉ giữ:
- Header row (STT, username, password)
- 1 dòng data duy nhất

### Cách 2: Sửa code DataProvider

Trong HappyOrderLoginTest.java, sửa getLoginData():

```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() throws IOException {
    String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
    ExcelUtils excel = new ExcelUtils(filePath, "TestData");

    // CHỈ LẤY 1 DÒNG ĐẦU TIÊN
    Object[][] data = new Object[1][3];
    data[0][0] = excel.getCellData(1, 0); // STT
    data[0][1] = excel.getCellData(1, 1); // username
    data[0][2] = excel.getCellData(1, 2); // password

    excel.close();
    return data;
}
```

### Cách 3: Chạy method khác

Nếu chỉ muốn test 1 lần nhanh, dùng `testSingleLogin`:

```xml
<methods>
    <include name="testSingleLogin"/>
</methods>
```

## Tóm tắt

- ✅ Đã fix: Test không còn chạy 3 methods nữa
- ✅ Hiện tại: Chỉ chạy testHappyOrderLogin
- ⚠️ Lưu ý: Số lần chạy = số dòng data trong Excel

Để chỉ test 1 lần → Chỉ giữ 1 dòng data trong Excel
