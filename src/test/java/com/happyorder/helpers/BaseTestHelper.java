package com.happyorder.helpers;

import com.happyorder.utils.ExcelUtils;
import com.happyorder.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;

/**
 * Base Test Helper - Contains all common reusable test logic
 * This class centralizes common functionality used across all test classes
 * Following DRY principle to avoid code duplication
 *
 * @author HappyOrder QA Team
 * @since 1.0
 */
public class BaseTestHelper {

    // ==================== CONSTANTS ====================

    protected static final int PAGE_LOAD_WAIT = 3000;
    protected static final String SCREENSHOT_PREFIX = "HappyOrder_";

    // ==================== COMMON ACTIONS ====================

    /**
     * Wait for page to load
     */
    public static void waitForPageLoad(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    /**
     * Wait for page to load with default time
     */
    public static void waitForPageLoad() throws InterruptedException {
        Thread.sleep(PAGE_LOAD_WAIT);
    }

    // ==================== SCREENSHOT METHODS ====================

    /**
     * Capture screenshot with sanitized title
     */
    public static void captureScreenshotWithTitle(WebDriver driver, String title, String prefix) {
        String screenshotName = sanitizeFileName(title);
        ScreenshotUtils.captureScreenshot(driver, prefix + screenshotName);
    }

    /**
     * Capture screenshot with default prefix
     */
    public static void captureScreenshotWithTitle(WebDriver driver, String title) {
        captureScreenshotWithTitle(driver, title, SCREENSHOT_PREFIX);
    }

    /**
     * Capture screenshot with custom name
     */
    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        ScreenshotUtils.captureScreenshot(driver, screenshotName);
    }

    /**
     * Sanitize filename by removing special characters
     */
    public static String sanitizeFileName(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "_");
    }

    // ==================== VALIDATION METHODS ====================

    /**
     * Check if expected message indicates success
     */
    public static boolean isExpectedSuccess(String expectedMessage) {
        String lower = expectedMessage.toLowerCase();
        return lower.contains("profile") ||
               lower.contains("success") ||
               lower.contains("thành công") ||
               lower.contains("login successful") ||
               lower.contains("successfully") ||
               lower.contains("loaded") ||
               lower.contains("displayed");
    }

    /**
     * Check if expected message indicates failure
     */
    public static boolean isExpectedFailure(String expectedMessage) {
        String lower = expectedMessage.toLowerCase();
        return lower.contains("error") ||
               lower.contains("invalid") ||
               lower.contains("không chính xác") ||
               lower.contains("sai") ||
               lower.contains("lỗi") ||
               lower.contains("hiển thị lỗi") ||
               lower.contains("denied") ||
               lower.contains("failed") ||
               lower.contains("no results");
    }

    /**
     * Determine test result based on actual and expected
     */
    public static String determineTestResult(boolean actualSuccess, String expectedMessage) {
        boolean expectSuccess = isExpectedSuccess(expectedMessage);
        boolean expectFailure = isExpectedFailure(expectedMessage);

        if (actualSuccess && expectSuccess) {
            return "Pass";
        } else if (!actualSuccess && expectFailure) {
            return "Pass";
        } else {
            return "Failed";
        }
    }

    // ==================== PRINTING METHODS ====================

    /**
     * Print test header with details
     */
    public static void printTestHeader(String title, String step, String username, String expectedMessage) {
        System.out.println("\n===============================================");
        System.out.println("Test Case: " + title);
        System.out.println("Steps:\n" + step);
        if (username != null && !username.isEmpty()) {
            System.out.println("Username: " + username);
        }
        System.out.println("Expected: " + expectedMessage);
        System.out.println("===============================================");
    }

    /**
     * Print test header for non-login tests
     */
    public static void printTestHeader(String title, String step, String expectedMessage) {
        printTestHeader(title, step, null, expectedMessage);
    }

    /**
     * Print smoke test header
     */
    public static void printSmokeTestHeader(String title, String info) {
        System.out.println("\n===============================================");
        System.out.println("Smoke Test: " + title);
        if (info != null && !info.isEmpty()) {
            System.out.println("Info: " + info);
        }
        System.out.println("===============================================");
    }

    /**
     * Print section header
     */
    public static void printSectionHeader(String title) {
        System.out.println("\n===============================================");
        System.out.println(title);
        System.out.println("===============================================");
    }

    /**
     * Print section footer
     */
    public static void printSectionFooter() {
        System.out.println("===============================================\n");
    }

    /**
     * Print page information
     */
    public static void printPageInfo(WebDriver driver) {
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
    }

    /**
     * Print success message
     */
    public static void printSuccess(String message) {
        System.out.println("✓ " + message);
    }

    /**
     * Print failure message
     */
    public static void printFailure(String actualMessage) {
        System.out.println("✗ " + actualMessage);
    }

    /**
     * Print failure with expected validation
     */
    public static void printFailure(String actualMessage, String expectedMessage) {
        System.out.println("✗ " + actualMessage);
        if (isExpectedFailure(expectedMessage)) {
            System.out.println("✓ Test passed: Failure was expected");
        }
    }

    /**
     * Print test result summary
     */
    public static void printTestResult(String expectedMessage, String actualMessage, String testResult) {
        System.out.println("Expected: " + expectedMessage);
        System.out.println("Actual: " + actualMessage);
        System.out.println("Result: " + testResult);
        printSectionFooter();
    }

    // ==================== EXCEPTION HANDLING METHODS ====================

    /**
     * Handle test exception with screenshot and debug info
     */
    public static void handleTestException(WebDriver driver, Exception e, String title, String prefix) {
        System.out.println("Error during test: " + e.getMessage());
        e.printStackTrace();

        // Capture error screenshot
        String screenshotName = sanitizeFileName(title);
        ScreenshotUtils.captureScreenshot(driver, prefix + "Error_" + screenshotName);

        // Print page source for debugging
        printPageSourceDebug(driver);
    }

    /**
     * Handle test exception with default prefix
     */
    public static void handleTestException(WebDriver driver, Exception e, String title) {
        handleTestException(driver, e, title, SCREENSHOT_PREFIX);
    }

    /**
     * Print page source for debugging (first 500 chars)
     */
    public static void printPageSourceDebug(WebDriver driver) {
        System.out.println("\n--- Page Source (first 500 chars) ---");
        String pageSource = driver.getPageSource();
        System.out.println(pageSource.substring(0, Math.min(500, pageSource.length())));
        System.out.println("--- End Page Source ---\n");
    }

    // ==================== EXCEL OPERATIONS METHODS ====================

    /**
     * Update test result in Excel file
     * Generic method that works with any Excel file
     */
    public static void updateTestResult(String filePath, String sheetName,
                                       String title, String result, int resultColumnIndex) {
        try {
            ExcelUtils excel = new ExcelUtils(filePath, sheetName);

            int rowCount = excel.getRowCount();
            for (int i = 1; i < rowCount; i++) {
                String cellTitle = excel.getCellData(i, "Title");
                if (cellTitle.equals(title)) {
                    excel.setCellData(i, resultColumnIndex, result);
                    break;
                }
            }

            excel.close();
        } catch (Exception e) {
            System.out.println("Could not update Excel result: " + e.getMessage());
        }
    }

    /**
     * Update test result with default result column (5)
     */
    public static void updateTestResult(String filePath, String sheetName, String title, String result) {
        updateTestResult(filePath, sheetName, title, result, 5);
    }

    // ==================== MESSAGE BUILDER METHODS ====================

    /**
     * Build success message with URL
     */
    public static String buildSuccessMessage(String action, String url) {
        return action + " successful, current URL: " + url;
    }

    /**
     * Build failure message with URL
     */
    public static String buildFailureMessage(String action, String url) {
        return action + " failed, stayed on: " + url;
    }

    /**
     * Build generic message
     */
    public static String buildMessage(boolean success, String action, String details) {
        return success ?
            action + " successful: " + details :
            action + " failed: " + details;
    }
}
