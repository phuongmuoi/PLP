package com.happyorder.tests;

import com.happyorder.base.BaseTest;
import com.happyorder.dataproviders.LoginDataProvider;
import com.happyorder.pages.HappyOrderLoginPage;
import com.happyorder.utils.ExcelUtils;
import com.happyorder.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class HappyOrderLoginTest extends BaseTest {

    // Constants
    private static final int PAGE_LOAD_WAIT = 3000;
    private static final String SCREENSHOT_PREFIX = "HappyOrder_";
    private static final String LOGIN_DATA_FILE = "/src/test/resources/testdata/LoginData.xlsx";
    private static final String LOGIN_SHEET = "TestData";

    // ==================== TEST METHODS ====================

    @Test(dataProvider = "loginTestData",
          dataProviderClass = LoginDataProvider.class,
          priority = 3,
          description = "Test login to HappyOrder with Excel data")
    public void testHappyOrderLogin(String title, String step, String username,
                                     String password, String expectedMessage, String result) throws InterruptedException {
        printTestHeader(title, step, username, expectedMessage);

        HappyOrderLoginPage loginPage = new HappyOrderLoginPage(driver);
        String testResult = "Failed";
        String actualMessage = "";

        try {
            // Perform login and wait
            performLogin(loginPage, username, password);

            // Capture screenshot and print debug info
            captureScreenshotWithTitle(title);
            printPageInfo(loginPage);

            // Validate and determine result
            if (loginPage.isLoginSuccessful()) {
                actualMessage = buildSuccessMessage(loginPage);
                testResult = isExpectedSuccess(expectedMessage) ? "Pass" : "Failed";
                printSuccess(actualMessage);
            } else {
                actualMessage = buildFailureMessage(loginPage);
                testResult = isExpectedFailure(expectedMessage) ? "Pass" : "Failed";
                printFailure(actualMessage, expectedMessage);
            }

            // Print results
            printTestResult(expectedMessage, actualMessage, testResult);

            // Update Excel
            updateTestResult(title, testResult, actualMessage);

        } catch (Exception e) {
            handleTestException(e, title);
            updateTestResult(title, "Failed", "Exception: " + e.getMessage());
            Assert.fail("Login test failed with exception: " + e.getMessage());
        }
    }

    @Test(dataProvider = "singleLoginData",
          dataProviderClass = LoginDataProvider.class,
          priority = 1,
          groups = "smoke",
          description = "Test login with first credential from Excel - Smoke Test")
    public void testSingleLogin(String title, String step, String username,
                                String password, String expectedMessage, String result) throws InterruptedException {
        printSmokeTestHeader(title, username);

        HappyOrderLoginPage loginPage = new HappyOrderLoginPage(driver);

        try {
            performLogin(loginPage, username, password);
            ScreenshotUtils.captureScreenshot(driver, SCREENSHOT_PREFIX + "Single_Login");
            printPageInfo(loginPage);

            if (loginPage.isLoginSuccessful()) {
                System.out.println("✓ Login successful!");
            } else {
                System.out.println("Login may have failed or requires verification");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, SCREENSHOT_PREFIX + "Single_Login_Error");
            throw e;
        }
    }

    @Test(priority = 2,
          groups = "smoke",
          description = "Verify login page loads correctly")
    public void testLoginPageLoad() {
        printSectionHeader("Testing HappyOrder Login Page Load");

        HappyOrderLoginPage loginPage = new HappyOrderLoginPage(driver);
        loginPage.navigateToLoginPage();

        ScreenshotUtils.captureScreenshot(driver, SCREENSHOT_PREFIX + "LoginPage");
        printPageInfo(loginPage);

        Assert.assertTrue(loginPage.getCurrentUrl().contains("login"),
                "Not on login page. Current URL: " + loginPage.getCurrentUrl());

        System.out.println("✓ Login page loaded successfully");
        printSectionFooter();
    }

    // ==================== HELPER METHODS - LOGIN ACTIONS ====================

    /**
     * Perform login and wait for page to load
     */
    private void performLogin(HappyOrderLoginPage loginPage, String username, String password)
            throws InterruptedException {
        loginPage.login(username, password);
        Thread.sleep(PAGE_LOAD_WAIT);
    }

    // ==================== HELPER METHODS - SCREENSHOT ====================

    /**
     * Capture screenshot with sanitized title
     */
    private void captureScreenshotWithTitle(String title) {
        String screenshotName = sanitizeFileName(title);
        ScreenshotUtils.captureScreenshot(driver, SCREENSHOT_PREFIX + screenshotName);
    }

    /**
     * Sanitize filename by removing special characters
     */
    private String sanitizeFileName(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "_");
    }

    // ==================== HELPER METHODS - VALIDATION ====================

    /**
     * Check if expected message indicates success
     */
    private boolean isExpectedSuccess(String expectedMessage) {
        String lower = expectedMessage.toLowerCase();
        return lower.contains("profile") ||
               lower.contains("success") ||
               lower.contains("thành công") ||
               lower.contains("login successful");
    }

    /**
     * Check if expected message indicates failure
     */
    private boolean isExpectedFailure(String expectedMessage) {
        String lower = expectedMessage.toLowerCase();
        return lower.contains("error") ||
               lower.contains("invalid") ||
               lower.contains("không chính xác") ||
               lower.contains("sai") ||
               lower.contains("lỗi") ||
               lower.contains("hiển thị lỗi");
    }

    // ==================== HELPER METHODS - MESSAGE BUILDERS ====================

    /**
     * Build success message
     */
    private String buildSuccessMessage(HappyOrderLoginPage loginPage) {
        return "Login successful, redirect to " + loginPage.getCurrentUrl();
    }

    /**
     * Build failure message
     */
    private String buildFailureMessage(HappyOrderLoginPage loginPage) {
        return "Login failed: Stayed on login page (URL: " + loginPage.getCurrentUrl() + ")";
    }

    // ==================== HELPER METHODS - PRINTING ====================

    /**
     * Print test header with details
     */
    private void printTestHeader(String title, String step, String username, String expectedMessage) {
        System.out.println("\n===============================================");
        System.out.println("Test Case: " + title);
        System.out.println("Steps:\n" + step);
        System.out.println("Username: " + username);
        System.out.println("Expected: " + expectedMessage);
        System.out.println("===============================================");
    }

    /**
     * Print smoke test header
     */
    private void printSmokeTestHeader(String title, String username) {
        System.out.println("\n===============================================");
        System.out.println("Single Login Test (Smoke)");
        System.out.println("Test Case: " + title);
        System.out.println("Username: " + username);
        System.out.println("===============================================");
    }

    /**
     * Print section header
     */
    private void printSectionHeader(String title) {
        System.out.println("\n===============================================");
        System.out.println(title);
        System.out.println("===============================================");
    }

    /**
     * Print section footer
     */
    private void printSectionFooter() {
        System.out.println("===============================================\n");
    }

    /**
     * Print page information
     */
    private void printPageInfo(HappyOrderLoginPage loginPage) {
        System.out.println("Current URL: " + loginPage.getCurrentUrl());
        System.out.println("Page Title: " + loginPage.getPageTitle());
    }

    /**
     * Print success message
     */
    private void printSuccess(String message) {
        System.out.println("✓ " + message);
    }

    /**
     * Print failure message
     */
    private void printFailure(String actualMessage, String expectedMessage) {
        System.out.println("✗ " + actualMessage);
        if (isExpectedFailure(expectedMessage)) {
            System.out.println("✓ Test passed: Login correctly rejected invalid credentials");
        }
    }

    /**
     * Print test result summary
     */
    private void printTestResult(String expectedMessage, String actualMessage, String testResult) {
        System.out.println("Expected: " + expectedMessage);
        System.out.println("Actual: " + actualMessage);
        System.out.println("Result: " + testResult);
        printSectionFooter();
    }

    // ==================== HELPER METHODS - EXCEPTION HANDLING ====================

    /**
     * Handle test exception with screenshot and debug info
     */
    private void handleTestException(Exception e, String title) {
        System.out.println("Error during login test: " + e.getMessage());
        e.printStackTrace();

        // Capture error screenshot
        String screenshotName = sanitizeFileName(title);
        ScreenshotUtils.captureScreenshot(driver, SCREENSHOT_PREFIX + "Error_" + screenshotName);

        // Print page source for debugging
        printPageSourceDebug();
    }

    /**
     * Print page source for debugging (first 500 chars)
     */
    private void printPageSourceDebug() {
        System.out.println("\n--- Page Source (first 500 chars) ---");
        String pageSource = driver.getPageSource();
        System.out.println(pageSource.substring(0, Math.min(500, pageSource.length())));
        System.out.println("--- End Page Source ---\n");
    }

    // ==================== HELPER METHODS - EXCEL OPERATIONS ====================

    /**
     * Update test result in Excel file
     */
    private void updateTestResult(String title, String result, String actualMessage) {
        try {
            String filePath = System.getProperty("user.dir") + LOGIN_DATA_FILE;
            ExcelUtils excel = new ExcelUtils(filePath, LOGIN_SHEET);

            int rowCount = excel.getRowCount();
            for (int i = 1; i < rowCount; i++) {
                String cellTitle = excel.getCellData(i, "Title");
                if (cellTitle.equals(title)) {
                    excel.setCellData(i, 5, result); // Column 5 is Result
                    break;
                }
            }

            excel.close();
        } catch (Exception e) {
            System.out.println("Could not update Excel result: " + e.getMessage());
        }
    }
}
