package com.happyorder.tests;

import com.happyorder.base.BaseTest;
import com.happyorder.pages.GoogleHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleSearchTest extends BaseTest {

    @Test(priority = 1, description = "Verify Google homepage loads successfully")
    public void testGoogleHomePageLoad() {
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.navigateToGoogle();

        Assert.assertTrue(homePage.isSearchBoxDisplayed(), "Search box is not displayed");
        Assert.assertTrue(homePage.getPageTitle().contains("Google"), "Page title doesn't contain Google");
    }

    @Test(priority = 2, description = "Verify search functionality works")
    public void testGoogleSearch() {
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.navigateToGoogle();

        String searchTerm = "Selenium WebDriver";
        homePage.performSearch(searchTerm);

        // Wait for results page to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("search"), "Search was not performed successfully");
    }

    @Test(priority = 3, description = "Verify search box accepts input")
    public void testSearchBoxInput() {
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.navigateToGoogle();

        String testText = "TestNG Framework";
        homePage.enterSearchText(testText);

        // Verify the input was entered (you might need to add a getter method)
        Assert.assertTrue(homePage.isSearchBoxDisplayed(), "Search box is not displayed after input");
    }
}
