package com.happyorder.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePages {
protected WebDriver driver;
    protected WebDriverWait wait;

    private static final int DEFAULT_TIMEOUT = 10;

    public BasePages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    // ==================== WAIT METHODS ====================

    /**
     * Chờ element visible và return element
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Chờ element clickable và return element
     */
    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Chờ URL chứa text cụ thể
     */
    protected void waitForUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Chờ URL chứa text cụ thể
     */
    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    // ==================== INPUT METHODS ====================

    /**
     * Input text vào field (với wait)
     */
    protected void sendKeys(By locator, String text) {
        waitForElementVisible(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    /**
     * Click vào element (với wait)
     */
    protected void click(By locator) {
        waitForElementClickable(locator).click();
    }

    /**
     * Get text từ element (với wait)
     */
    protected String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    /**
     * Check element có hiển thị không
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== DROPDOWN METHODS ====================

    /**
     * Select dropdown by visible text
     */
    protected void selectByVisibleText(By locator, String text) {
        if (text != null && !text.trim().isEmpty()) {
            WebElement element = waitForElementClickable(locator);
            Select select = new Select(element);
            select.selectByVisibleText(text);
        }
    }

    /**
     * Select dropdown by value
     */
    protected void selectByValue(By locator, String value) {
        WebElement element = waitForElementClickable(locator);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    // ==================== TOAST MESSAGE METHODS ====================

    /**
     * Lấy text của toast message/notification (sử dụng JavaScript)
     */
    public String getToastMessageText(By locator) {
        try {
            WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String message = (String) js.executeScript("return arguments[0].innerText;", messageElement);
            return message.trim();
        } catch (TimeoutException e) {
            System.err.println("Lỗi: Không tìm thấy toast message sau " + DEFAULT_TIMEOUT + " giây.");
            return "";
        } catch (StaleElementReferenceException e) {
            System.err.println("Lỗi: Toast message đã biến mất trước khi truy cập.");
            return "";
        }
    }

    /**
     * Lấy toast message với custom timeout
     */
    public String getToastMessageText(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement messageElement = customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String message = (String) js.executeScript("return arguments[0].innerText;", messageElement);
            return message.trim();
        } catch (TimeoutException e) {
            return "";
        } catch (StaleElementReferenceException e) {
            return "";
        }
    }

    // ==================== CSS/STYLE METHODS ====================

    /**
     * Lấy CSS value của element
     */
    protected String getCssValue(By locator, String propertyName) {
        return waitForElementVisible(locator).getCssValue(propertyName);
    }

    /**
     * Check element có border color cụ thể không (dùng cho validation)
     */
    protected boolean hasRedBorder(By locator) {
        String borderColor = getCssValue(locator, "border-color");
        return borderColor.equals("rgb(220, 53, 69)") || borderColor.contains("#dc3545");
    }

    // ==================== JAVASCRIPT METHODS ====================

    /**
     * Scroll đến element
     */
    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll đến cuối trang
     */
    protected void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Click bằng JavaScript (khi click thông thường không hoạt động)
     */
    protected void clickByJS(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
}

