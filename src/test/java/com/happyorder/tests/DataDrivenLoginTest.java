package com.happyorder.tests;

import com.happyorder.base.BaseTest;
import com.happyorder.dataproviders.LoginDataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class DataDrivenLoginTest extends BaseTest {

    @Test(dataProvider = "loginBasicData",
          dataProviderClass = LoginDataProvider.class,
          description = "Data-driven login test from Excel")
    public void testLoginWithExcelData(String stt, String username, String password) {
        System.out.println("===============================================");
        System.out.println("Test Case #" + stt);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("===============================================");

        // Example: Navigate to a login page
        // driver.get("https://example.com/login");

        // Example: Perform login actions
        // LoginPage loginPage = new LoginPage(driver);
        // loginPage.login(username, password);

        // Add your assertions here
        // Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed for user: " + username);

        // For demonstration, just print the data
        System.out.println("Login test executed with credentials: " + username);
    }

    @Test(description = "Example: Read specific credential using LoginDataProvider")
    public void testReadSpecificData() throws IOException {
        // Using the new LoginDataProvider to get credentials by username
        // This is just an example - you would pass actual username
        System.out.println("Example: Using LoginDataProvider to read specific credentials");
        System.out.println("See LoginDataProvider.getCredentialsByUsername() method");

        // Example usage (commented out as it needs actual username):
        // LoginCredentials creds = LoginDataProvider.getCredentialsByUsername("admin");
        // System.out.println("Username: " + creds.getUsername());
        // System.out.println("Password: " + creds.getPassword());
    }

    @Test(description = "Example: Print all login data using LoginDataProvider")
    public void testPrintAllExcelData() throws IOException {
        System.out.println("Example: Printing all login test data using LoginDataProvider");
        System.out.println("-------------------");

        // Get all login test data from LoginDataProvider
        Object[][] allData = LoginDataProvider.loginTestData();

        System.out.println("Total test cases: " + allData.length);
        System.out.println("\nAll Login Test Data:");
        System.out.println("-------------------");

        for (int i = 0; i < allData.length; i++) {
            System.out.println("\nTest Case #" + (i + 1));
            System.out.println("Title: " + allData[i][0]);
            System.out.println("Step: " + allData[i][1]);
            System.out.println("Username: " + allData[i][2]);
            System.out.println("Password: " + allData[i][3]);
            System.out.println("Expected: " + allData[i][4]);
            System.out.println("Result: " + allData[i][5]);
            System.out.println("-------------------");
        }
    }
}
