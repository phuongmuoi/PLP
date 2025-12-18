package com.happyorder.dataproviders;

import org.testng.annotations.DataProvider;

import java.io.IOException;

/**
 * Data Provider for Home Page Test Cases
 * Provides test data from HomeData.xlsx in various formats
 * Extends BaseDataProvider for common utility methods
 *
 * Usage Examples:
 * - @Test(dataProvider = "homeTestData", dataProviderClass = HomeDataProvider.class)
 * - @Test(dataProvider = "singleHomeData", dataProviderClass = HomeDataProvider.class)
 *
 * @author HappyOrder QA Team
 * @since 1.0
 */
public class HomeDataProvider extends BaseDataProvider {

    private static final String HOME_DATA_FILE = "HomeData.xlsx";
    private static final String HOME_SHEET = "TestData";

    /**
     * Provides complete home page test data with all columns
     * Returns: Title, Step, Action, ExpectedElement, ExpectedMessage, Result
     *
     * @return Object[][] - Array of home test data
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "homeTestData")
    public static Object[][] homeTestData() throws IOException {
        String[] columns = {"Title", "Step", "Action", "ExpectedElement",
                           "ExpectedMessage", "Result(pass/Failed)"};
        return readExcelData(HOME_DATA_FILE, HOME_SHEET, columns);
    }

    /**
     * Provides navigation test cases only
     * Filters test data for navigation actions
     *
     * @return Object[][] - Filtered array with navigation tests
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "navigationTestData")
    public static Object[][] navigationTestData() throws IOException {
        Object[][] allData = homeTestData();
        return filterByAction(allData, "navigate", "click");
    }

    /**
     * Provides verification test cases only
     * Filters test data for verification actions
     *
     * @return Object[][] - Filtered array with verification tests
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "verificationTestData")
    public static Object[][] verificationTestData() throws IOException {
        Object[][] allData = homeTestData();
        return filterByAction(allData, "verify");
    }

    /**
     * Provides search test cases only
     * Filters test data for search actions
     *
     * @return Object[][] - Filtered array with search tests
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "searchTestData")
    public static Object[][] searchTestData() throws IOException {
        Object[][] allData = homeTestData();
        return filterByAction(allData, "search");
    }

    /**
     * Provides first row of home data (for quick single test)
     * Useful for smoke testing or debugging
     *
     * @return Object[][] - Single row of test data
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "singleHomeData")
    public static Object[][] singleHomeData() throws IOException {
        String[] columns = {"Title", "Step", "Action", "ExpectedElement",
                           "ExpectedMessage", "Result(pass/Failed)"};
        Object[] singleRow = readSingleRow(HOME_DATA_FILE, HOME_SHEET, 1, columns);
        return new Object[][]{singleRow};
    }

    /**
     * Provides valid test cases (expected to pass)
     *
     * @return Object[][] - Filtered array with valid tests
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "validHomeData")
    public static Object[][] validHomeData() throws IOException {
        Object[][] allData = homeTestData();
        return filterValidTests(allData);
    }

    /**
     * Provides invalid/negative test cases
     *
     * @return Object[][] - Filtered array with negative tests
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "invalidHomeData")
    public static Object[][] invalidHomeData() throws IOException {
        Object[][] allData = homeTestData();
        return filterInvalidTests(allData);
    }

    // ==================== PRIVATE HELPER METHODS ====================

    /**
     * Filter test data by action type
     * Supports multiple action keywords
     */
    private static Object[][] filterByAction(Object[][] data, String... actionKeywords) {
        java.util.List<Object[]> filteredList = new java.util.ArrayList<>();

        for (Object[] row : data) {
            String action = row[2].toString().toLowerCase();
            for (String keyword : actionKeywords) {
                if (action.contains(keyword.toLowerCase())) {
                    filteredList.add(row);
                    break;
                }
            }
        }

        return filteredList.toArray(new Object[0][]);
    }

    /**
     * Filter valid test cases (not containing error/invalid keywords)
     */
    private static Object[][] filterValidTests(Object[][] data) {
        java.util.List<Object[]> validList = new java.util.ArrayList<>();

        for (Object[] row : data) {
            String expectedMessage = row[4].toString().toLowerCase();
            if (expectedMessage.contains("success") ||
                expectedMessage.contains("displayed") ||
                expectedMessage.contains("loaded")) {
                validList.add(row);
            }
        }

        return validList.toArray(new Object[0][]);
    }

    /**
     * Filter invalid test cases (containing error keywords)
     */
    private static Object[][] filterInvalidTests(Object[][] data) {
        java.util.List<Object[]> invalidList = new java.util.ArrayList<>();

        for (Object[] row : data) {
            String expectedMessage = row[4].toString().toLowerCase();
            if (expectedMessage.contains("error") ||
                expectedMessage.contains("no results") ||
                expectedMessage.contains("denied") ||
                expectedMessage.contains("invalid")) {
                invalidList.add(row);
            }
        }

        return invalidList.toArray(new Object[0][]);
    }
}
