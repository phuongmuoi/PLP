package com.happyorder.tests;

import com.happyorder.base.BaseTest;
import com.happyorder.utils.ScreenshotUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class DebugHappyOrderTest extends BaseTest {

    @Test(description = "Debug: Analyze HappyOrder login page structure")
    public void testAnalyzeLoginPage() {
        System.out.println("\n=== Analyzing HappyOrder Login Page ===\n");

        // Navigate to login page
        driver.get("https://happyorder.vn/client-area/auth/login");
        System.out.println("Navigated to: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        // Wait for page to load
        try {
            Thread.sleep(5000); // Wait 5 seconds for JavaScript to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Capture screenshot
        ScreenshotUtils.captureScreenshot(driver, "Debug_LoginPage_Full");

        // Find all input fields
        System.out.println("\n--- All INPUT elements ---");
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        System.out.println("Total inputs found: " + inputs.size());
        for (int i = 0; i < inputs.size() && i < 10; i++) {
            WebElement input = inputs.get(i);
            System.out.println(String.format("Input %d: type='%s', name='%s', id='%s', placeholder='%s', class='%s'",
                    i + 1,
                    input.getAttribute("type"),
                    input.getAttribute("name"),
                    input.getAttribute("id"),
                    input.getAttribute("placeholder"),
                    input.getAttribute("class")));
        }

        // Find all buttons
        System.out.println("\n--- All BUTTON elements ---");
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        System.out.println("Total buttons found: " + buttons.size());
        for (int i = 0; i < buttons.size() && i < 10; i++) {
            WebElement button = buttons.get(i);
            System.out.println(String.format("Button %d: type='%s', id='%s', class='%s', text='%s'",
                    i + 1,
                    button.getAttribute("type"),
                    button.getAttribute("id"),
                    button.getAttribute("class"),
                    button.getText()));
        }

        // Find all forms
        System.out.println("\n--- All FORM elements ---");
        List<WebElement> forms = driver.findElements(By.tagName("form"));
        System.out.println("Total forms found: " + forms.size());
        for (int i = 0; i < forms.size(); i++) {
            WebElement form = forms.get(i);
            System.out.println(String.format("Form %d: action='%s', method='%s', id='%s', class='%s'",
                    i + 1,
                    form.getAttribute("action"),
                    form.getAttribute("method"),
                    form.getAttribute("id"),
                    form.getAttribute("class")));
        }

        // Print page source (first 2000 characters)
        System.out.println("\n--- Page Source (first 2000 chars) ---");
        String pageSource = driver.getPageSource();
        System.out.println(pageSource.substring(0, Math.min(2000, pageSource.length())));
        System.out.println("\n... (truncated)\n");

        System.out.println("=== Analysis Complete ===\n");
        System.out.println("Screenshot saved to: test-output/screenshots/Debug_LoginPage_Full_*.png");
        System.out.println("\nPlease check the screenshot and console output above to identify the correct locators.");
    }
}
