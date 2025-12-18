package com.happyorder.tests;

import com.happyorder.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {

    @Test(priority = 1, groups = {"smoke"}, description = "Sample test to verify browser launch")
    public void testBrowserLaunch() {
        driver.get("https://www.example.com");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Example"), "Page title doesn't match expected");
        System.out.println("Page Title: " + title);
    }

    @Test(priority = 2, groups = {"regression"}, description = "Test navigation to different URL")
    public void testNavigation() {
        driver.get("https://www.example.com");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("example.com"), "URL doesn't match");
        System.out.println("Current URL: " + currentUrl);
    }

    @Test(priority = 3, groups = {"smoke", "regression"}, description = "Test page source verification")
    public void testPageSource() {
        driver.get("https://www.example.com");
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Example Domain"), "Page source doesn't contain expected text");
        System.out.println("Page source contains 'Example Domain'");
    }
}
