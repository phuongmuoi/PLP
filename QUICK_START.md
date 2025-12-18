# Quick Start Guide - HappyOrder Automation Testing

## Bước 1: Cài đặt dependencies

```bash
mvn clean install
```

## Bước 2: Cập nhật dữ liệu test

1. Mở file Excel: `src/test/resources/testdata/LoginData.xlsx`
2. Cập nhật username và password thực tế của bạn:

| STT | username | password |
|-----|----------|----------|
| 1 | **your-email@example.com** | **YourPassword123** |
| 2 | **user02@test.com** | **Password456** |
| 3 | **user03@test.com** | **Password789** |

3. Lưu file

## Bước 3: Chạy test HappyOrder Login

```bash
mvn test -DsuiteXmlFile=testng-happyorder.xml
```

## Bước 4: Xem kết quả

### Console Output
Kết quả hiển thị trực tiếp trong terminal:
```
===============================================
Test Case #1
Testing login with username: your-email@example.com
===============================================
Navigated to HappyOrder Login page
Entered username: your-email@example.com
Entered password: ****
Clicked login button
✓ Login successful for Test #1
===============================================
```

### Screenshots
Tất cả screenshots tự động được lưu tại:
```
test-output/screenshots/
```

### TestNG Report
Mở file này trong browser để xem báo cáo chi tiết:
```
test-output/index.html
```

## Các lệnh chạy test khác

### Test với user đầu tiên trong Excel
```bash
mvn test -Dtest=HappyOrderLoginTest#testSingleLogin
```

### Test load trang login
```bash
mvn test -Dtest=HappyOrderLoginTest#testLoginPageLoad
```

### Test với browser khác
```bash
# Firefox
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=firefox

# Edge
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=edge

# Chrome Headless (không hiển thị UI)
mvn test -DsuiteXmlFile=testng-happyorder.xml -Dbrowser=chrome-headless
```

## Cấu trúc file quan trọng

```
happyorder/
├── src/test/resources/testdata/
│   └── LoginData.xlsx                  ← CẬP NHẬT DỮ LIỆU Ở ĐÂY
├── testng-happyorder.xml               ← Config cho HappyOrder tests
├── HAPPYORDER_LOGIN_GUIDE.md           ← Hướng dẫn chi tiết
└── test-output/
    ├── screenshots/                    ← Screenshots tự động
    └── index.html                      ← TestNG Report
```

## Troubleshooting nhanh

### Test không tìm thấy elements?
1. Chạy test này để xem trang có load được không:
   ```bash
   mvn test -Dtest=HappyOrderLoginTest#testLoginPageLoad
   ```
2. Xem screenshot trong `test-output/screenshots/`
3. Đọc hướng dẫn điều chỉnh locators trong [HAPPYORDER_LOGIN_GUIDE.md](HAPPYORDER_LOGIN_GUIDE.md)

### Test báo lỗi?
1. Xem console output
2. Kiểm tra screenshot
3. Mở TestNG report: `test-output/index.html`

### Username/Password sai?
1. Mở lại `src/test/resources/testdata/LoginData.xlsx`
2. Kiểm tra và cập nhật thông tin đúng
3. Lưu file và chạy lại test

## Tài liệu đầy đủ

- [README.md](README.md) - Tổng quan dự án
- [HAPPYORDER_LOGIN_GUIDE.md](HAPPYORDER_LOGIN_GUIDE.md) - Chi tiết test HappyOrder
- [EXCEL_DATA_GUIDE.md](EXCEL_DATA_GUIDE.md) - Hướng dẫn Excel data-driven

## Tóm tắt

1. **Cài đặt**: `mvn clean install`
2. **Cập nhật Excel**: `src/test/resources/testdata/LoginData.xlsx`
3. **Chạy test**: `mvn test -DsuiteXmlFile=testng-happyorder.xml`
4. **Xem kết quả**: `test-output/index.html` và `test-output/screenshots/`

Chúc bạn test thành công! 🎉
