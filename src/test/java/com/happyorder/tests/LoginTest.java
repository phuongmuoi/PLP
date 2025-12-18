package com.happyorder.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.happyorder.base.BaseTest;
import com.happyorder.dataproviders.LoginDataProvider;
import com.happyorder.pages.LoginPage;

public class LoginTest extends BaseTest{
    private LoginPage loginPage;
    private static final String REGISTER_URL = "http://10.110.10.183:20001/auth/login";

    @BeforeMethod(dependsOnMethods = "setUp")
    public void setupLoginPage() {
        driver.get(REGISTER_URL);
        loginPage = new LoginPage(driver);
    }

    /** ================= TEST DATA-DRIVEN ================= */
    @Test(dataProvider = "loginTestData",
          dataProviderClass = LoginDataProvider.class,
          priority = 3,
          description = "Test login to HappyOrder with Excel data")
    public void TestLogin(String title, String step, String username, String password,
                         String expectedMessage, String expectedResult) throws InterruptedException{
        System.out.println("========================================");
        System.out.println("Test: " + title);
        System.out.println("Bước: " + step);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Expected Message: " + expectedMessage);
        System.out.println("========================================");

        //Nhập dữ liệu
        loginPage.inputTenDangNhap(username);
        loginPage.inputMatKhau(password);
        loginPage.clickbtnDangNhap();

        // Chờ một chút để trang xử lý
        Thread.sleep(1000);

        // Kiểm tra kết quả dựa trên Expected Message
        if (expectedMessage != null && !expectedMessage.trim().isEmpty()) {
            if (expectedMessage.toLowerCase().contains("thông báo") ||
                expectedMessage.toLowerCase().contains("error") ||
                expectedMessage.toLowerCase().contains("sai")) {
                // Trường hợp login thất bại - kiểm tra thông báo lỗi
                String actualMsg = loginPage.getThongBaoSaiData();
                System.out.println("Thông báo thực tế: " + actualMsg);
                Assert.assertFalse(actualMsg.isEmpty(), "❌ Không thấy thông báo lỗi nào");
            } else if (expectedMessage.toLowerCase().contains("success") ||
                       expectedMessage.toLowerCase().contains("thành công")) {
                // Trường hợp login thành công
                System.out.println("✅ Login thành công");
            }
        }
    }
}
