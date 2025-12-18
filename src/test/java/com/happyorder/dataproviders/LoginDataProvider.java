package com.happyorder.dataproviders;

import com.happyorder.models.LoginCredentials;
import com.happyorder.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;

/**
 * Data Provider for Login Test Cases
 * Provides test data from LoginData.xlsx in various formats
 * Extends BaseDataProvider for common utility methods
 *
 * Usage Examples:
 * - @Test(dataProvider = "loginTestData", dataProviderClass = LoginDataProvider.class)
 * - @Test(dataProvider = "loginCredentialsObjects", dataProviderClass = LoginDataProvider.class)
 *
 * @author HappyOrder QA Team
 * @since 1.0
 */
public class LoginDataProvider extends BaseDataProvider {

    private static final String LOGIN_DATA_FILE = "LoginData.xlsx";
    private static final String LOGIN_SHEET = "TestData";

    /**
     * Provides complete login test data with all columns
     * Returns: Title, Step, UserName, Password, ExpectedMessage, Result
     *
     * @return Object[][] - Array of login test data
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "loginTestData")
    public static Object[][] loginTestData() throws IOException {
        String[] columns = {"Title", "Step", "UserName", "Password",
                           "Expected Message", "Result(pass/Failed)"};
        return readExcelData(LOGIN_DATA_FILE, LOGIN_SHEET, columns);
    }

    /**
     * Provides basic login credentials only (username and password)
     * Returns: STT, UserName, Password
     * Used for simple login tests without validation
     *
     * @return Object[][] - Array with basic credentials
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "loginBasicData")
    public static Object[][] loginBasicData() throws IOException {
        return readExcelDataByIndex(LOGIN_DATA_FILE, LOGIN_SHEET, 3);
    }

    /**
     * Provides login data as LoginCredentials objects (Type-safe approach)
     * Better for maintainability and code readability
     *
     * Usage:
     * @Test(dataProvider = "loginCredentialsObjects", dataProviderClass = LoginDataProvider.class)
     * public void testLogin(LoginCredentials credentials) { ... }
     *
     * @return Object[][] - Array containing LoginCredentials objects
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "loginCredentialsObjects")
    public static Object[][] loginCredentialsObjects() throws IOException {
        Object[][] rawData = loginTestData();
        Object[][] objectData = new Object[rawData.length][1];

        for (int i = 0; i < rawData.length; i++) {
            LoginCredentials credentials = new LoginCredentials.Builder()
                    .title((String) rawData[i][0])
                    .step((String) rawData[i][1])
                    .username((String) rawData[i][2])
                    .password((String) rawData[i][3])
                    .expectedMessage((String) rawData[i][4])
                    .result((String) rawData[i][5])
                    .build();
            objectData[i][0] = credentials;
        }

        return objectData;
    }

    /**
     * Provides only valid login credentials (where expected result is success)
     * Filters test data to include only positive test cases
     *
     * @return Object[][] - Filtered array with valid credentials only
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "validLoginData")
    public static Object[][] validLoginData() throws IOException {
        Object[][] allData = loginTestData();
        // Filter: Keep only rows where expectedMessage contains success indicators
        return filterValidLogins(allData);
    }

    /**
     * Provides only invalid login credentials (for negative testing)
     * Filters test data to include only negative test cases
     *
     * @return Object[][] - Filtered array with invalid credentials only
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() throws IOException {
        Object[][] allData = loginTestData();
        // Filter: Keep only rows where expectedMessage contains error indicators
        return filterInvalidLogins(allData);
    }

    /**
     * Provides first row of login data (for quick single test)
     * Useful for smoke testing or debugging
     *
     * @return Object[][] - Single row of test data
     * @throws IOException if Excel file cannot be read
     */
    @DataProvider(name = "singleLoginData")
    public static Object[][] singleLoginData() throws IOException {
        String[] columns = {"Title", "Step", "UserName", "Password",
                           "Expected Message", "Result(pass/Failed)"};
        Object[] singleRow = readSingleRow(LOGIN_DATA_FILE, LOGIN_SHEET, 1, columns);
        return new Object[][]{singleRow};
    }

    /**
     * Read specific credentials by username
     *
     * @param username Username to search for
     * @return LoginCredentials object or null if not found
     * @throws IOException if Excel file cannot be read
     */
    public static LoginCredentials getCredentialsByUsername(String username) throws IOException {
        String filePath = getTestDataFilePath(LOGIN_DATA_FILE);
        ExcelUtils excel = new ExcelUtils(filePath, LOGIN_SHEET);

        int rowCount = excel.getRowCount();
        LoginCredentials credentials = null;

        for (int i = 1; i < rowCount; i++) {
            String cellUsername = excel.getCellData(i, "UserName");
            if (cellUsername.equals(username)) {
                credentials = new LoginCredentials.Builder()
                        .title(excel.getCellData(i, "Title"))
                        .step(excel.getCellData(i, "Step"))
                        .username(cellUsername)
                        .password(excel.getCellData(i, "Password"))
                        .expectedMessage(excel.getCellData(i, "Expected Message"))
                        .result(excel.getCellData(i, "Result(pass/Failed)"))
                        .build();
                break;
            }
        }

        excel.close();
        return credentials;
    }

    // Private helper methods

    /**
     * Filter valid login test cases
     */
    private static Object[][] filterValidLogins(Object[][] data) {
        java.util.List<Object[]> validList = new java.util.ArrayList<>();

        for (Object[] row : data) {
            String expectedMessage = row[4].toString().toLowerCase();
            if (expectedMessage.contains("success") ||
                expectedMessage.contains("profile") ||
                expectedMessage.contains("thành công") ||
                expectedMessage.contains("login successful")) {
                validList.add(row);
            }
        }

        return validList.toArray(new Object[0][]);
    }

    /**
     * Filter invalid login test cases
     */
    private static Object[][] filterInvalidLogins(Object[][] data) {
        java.util.List<Object[]> invalidList = new java.util.ArrayList<>();

        for (Object[] row : data) {
            String expectedMessage = row[4].toString().toLowerCase();
            if (expectedMessage.contains("error") ||
                expectedMessage.contains("invalid") ||
                expectedMessage.contains("không chính xác") ||
                expectedMessage.contains("sai") ||
                expectedMessage.contains("lỗi")) {
                invalidList.add(row);
            }
        }

        return invalidList.toArray(new Object[0][]);
    }
}
