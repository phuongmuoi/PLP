package com.happyorder.tests;

import com.happyorder.base.BaseTest;
import com.happyorder.dataproviders.HomeDataProvider;
import com.happyorder.helpers.BaseTestHelper;
import com.happyorder.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test Class for Home Page functionality
 * Uses BaseTestHelper for common test operations
 * Follows same pattern as HappyOrderLoginTest for consistency
 *
 * @author HappyOrder QA Team
 * @since 1.0
 */
public class HomeTest extends BaseTest {

    // Constants
    private static final String HOME_DATA_FILE = "/src/test/resources/testdata/HomeData.xlsx";
    private static final String HOME_SHEET = "TestData";
    private static final String SCREENSHOT_PREFIX = "Home_";

    // ==================== TEST METHODS ====================

    @Test(dataProvider = "homeTestData",
          dataProviderClass = HomeDataProvider.class,
          priority = 3,
          description = "Test home page functionality with Excel data")
    public void testHomePageFunctionality(String title, String step, String action,
                                          String expectedElement, String expectedMessage, String result) {
        BaseTestHelper.printTestHeader(title, step, expectedMessage);

        HomePage homePage = new HomePage(driver);
        String testResult = "Failed";
        String actualMessage = "";

        try {
            // Perform action based on test data
            boolean actionSuccess = performAction(homePage, action, expectedElement);

            // Wait and capture screenshot
            BaseTestHelper.waitForPageLoad();
            BaseTestHelper.captureScreenshotWithTitle(driver, title, SCREENSHOT_PREFIX);
            BaseTestHelper.printPageInfo(driver);

            // Build message and determine result
            if (actionSuccess) {
                actualMessage = BaseTestHelper.buildSuccessMessage(action, homePage.getCurrentUrl());
                BaseTestHelper.printSuccess(actualMessage);
            } else {
                actualMessage = BaseTestHelper.buildFailureMessage(action, homePage.getCurrentUrl());
                BaseTestHelper.printFailure(actualMessage, expectedMessage);
            }

            // Determine test result
            testResult = BaseTestHelper.determineTestResult(actionSuccess, expectedMessage);

            // Print results
            BaseTestHelper.printTestResult(expectedMessage, actualMessage, testResult);

            // Update Excel
            updateTestResult(title, testResult);

        } catch (Exception e) {
            BaseTestHelper.handleTestException(driver, e, title, SCREENSHOT_PREFIX);
            updateTestResult(title, "Failed");
            Assert.fail("Home page test failed with exception: " + e.getMessage());
        }
    }

    @Test(dataProvider = "verificationTestData",
          dataProviderClass = HomeDataProvider.class,
          priority = 2,
          groups = "smoke",
          description = "Verify home page elements - Smoke Test")
    public void testHomePageVerification(String title, String step, String action,
                                         String expectedElement, String expectedMessage, String result) throws InterruptedException {
        BaseTestHelper.printSmokeTestHeader(title, "Verification Test");

        HomePage homePage = new HomePage(driver);

        try {
            homePage.navigateToHomePage();
            BaseTestHelper.waitForPageLoad();

            boolean elementPresent = verifyElement(homePage, action, expectedElement);

            BaseTestHelper.captureScreenshot(driver, SCREENSHOT_PREFIX + "Verification");
            BaseTestHelper.printPageInfo(driver);

            if (elementPresent) {
                BaseTestHelper.printSuccess(expectedMessage);
            } else {
                BaseTestHelper.printFailure("Element not found: " + expectedElement);
            }

            Assert.assertTrue(elementPresent, "Verification failed: " + expectedMessage);

        } catch (Exception e) {
            BaseTestHelper.handleTestException(driver, e, title, SCREENSHOT_PREFIX);
            throw e;
        }
    }

    @Test(dataProvider = "singleHomeData",
          dataProviderClass = HomeDataProvider.class,
          priority = 1,
          groups = "smoke",
          description = "Quick home page load test - Smoke Test")
    public void testHomePageLoad(String title, String step, String action,
                                  String expectedElement, String expectedMessage, String result) throws InterruptedException {
        BaseTestHelper.printSmokeTestHeader("Home Page Load", "Quick verification");

        HomePage homePage = new HomePage(driver);

        try {
            homePage.navigateToHomePage();
            BaseTestHelper.waitForPageLoad();

            BaseTestHelper.captureScreenshot(driver, SCREENSHOT_PREFIX + "PageLoad");
            BaseTestHelper.printPageInfo(driver);

            boolean isLoaded = homePage.isOnHomePage() && homePage.isDashboardLoaded();

            if (isLoaded) {
                BaseTestHelper.printSuccess("Home page loaded successfully");
            } else {
                BaseTestHelper.printFailure("Home page failed to load");
            }

            Assert.assertTrue(isLoaded, "Home page did not load correctly");

        } catch (Exception e) {
            BaseTestHelper.handleTestException(driver, e, title, SCREENSHOT_PREFIX);
            throw e;
        }
    }

    // ==================== HELPER METHODS - ACTION PERFORMERS ====================

    /**
     * Perform action based on action type from test data
     */
    private boolean performAction(HomePage homePage, String action, String expectedElement) {
        try {
            action = action.toLowerCase();

            if (action.contains("navigate")) {
                homePage.navigateToHomePage();
                return homePage.isOnHomePage();

            } else if (action.contains("verify_title")) {
                return homePage.isPageTitleDisplayed();

            } else if (action.contains("verify_menu")) {
                return homePage.isMainMenuDisplayed();

            } else if (action.contains("verify_welcome")) {
                return homePage.isWelcomeMessageDisplayed();

            } else if (action.contains("verify_profile")) {
                return homePage.isUserProfileDisplayed();

            } else if (action.contains("click_orders")) {
                homePage.clickOrders();
                return driver.getCurrentUrl().toLowerCase().contains("orders");

            } else if (action.contains("click_products")) {
                homePage.clickProducts();
                return driver.getCurrentUrl().toLowerCase().contains("products");

            } else if (action.contains("click_settings")) {
                homePage.clickSettings();
                return driver.getCurrentUrl().toLowerCase().contains("settings");

            } else if (action.contains("search_valid")) {
                homePage.search("valid keyword");
                return homePage.isElementPresent(".search-results");

            } else if (action.contains("search_invalid")) {
                homePage.search("xyzinvalidkeyword123");
                return homePage.isElementPresent(".no-results") ||
                       homePage.getMessageText().toLowerCase().contains("no results");

            } else if (action.contains("logout")) {
                homePage.logout();
                return driver.getCurrentUrl().toLowerCase().contains("login");

            } else if (action.contains("navigate_no_auth")) {
                // This would test unauthorized access
                return driver.getCurrentUrl().toLowerCase().contains("login") ||
                       homePage.getMessageText().toLowerCase().contains("denied");

            } else {
                System.out.println("Unknown action: " + action);
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error performing action: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify element based on action type
     */
    private boolean verifyElement(HomePage homePage, String action, String expectedElement) {
        try {
            action = action.toLowerCase();

            if (action.contains("verify_title")) {
                return homePage.isPageTitleDisplayed();
            } else if (action.contains("verify_menu")) {
                return homePage.isMainMenuDisplayed();
            } else if (action.contains("verify_welcome")) {
                return homePage.isWelcomeMessageDisplayed();
            } else if (action.contains("verify_profile")) {
                return homePage.isUserProfileDisplayed();
            } else {
                // Generic element check
                return homePage.isElementPresent(expectedElement);
            }

        } catch (Exception e) {
            return false;
        }
    }

    // ==================== HELPER METHODS - EXCEL OPERATIONS ====================

    /**
     * Update test result in Excel
     */
    private void updateTestResult(String title, String result) {
        String filePath = System.getProperty("user.dir") + HOME_DATA_FILE;
        BaseTestHelper.updateTestResult(filePath, HOME_SHEET, title, result);
    }
}
