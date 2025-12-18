package com.happyorder.pages;

import com.happyorder.utils.WebElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HappyOrderLoginPage {
    private WebDriver driver;
    private WebElementUtils elementUtils;
    private WebDriverWait wait;

    // URL
    private final String LOGIN_URL = "https://happyorder.vn/client-area/auth/login";

    // Locators - Dựa trên cấu trúc thực tế của trang HappyOrder
    private By usernameField = By.cssSelector("input[type='text'], input[type='email']");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginButton = By.cssSelector("button.btn-danger.btn-auth, button.btn.btn-danger, .btn-auth");

    // Alternative locators nếu cần
    private By usernameFieldAlt = By.xpath("//input[@type='text' or @type='email']");
    private By passwordFieldAlt = By.xpath("//input[@type='password']");
    private By loginButtonAlt = By.xpath("//button[contains(@class, 'btn-danger') and contains(@class, 'btn-auth')]");

    // Success/Error message locators - Updated for HappyOrder
    private By errorMessage = By.xpath("//*[contains(text(), 'Thông báo') or contains(text(), 'không chính xác') or contains(@class, 'error') or contains(@class, 'alert') or contains(@class, 'message')]");
    private By errorMessageAlt = By.cssSelector(".alert, .error, .notification, [role='alert'], .message");
    private By successMessage = By.xpath("//*[contains(@class, 'success') or contains(@class, 'alert-success')]");

    public HappyOrderLoginPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new WebElementUtils(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void navigateToLoginPage() {
        driver.get(LOGIN_URL);
        System.out.println("Navigated to HappyOrder Login page: " + LOGIN_URL);

        // Wait for page to load
        elementUtils.waitForPageLoad();

        // Đợi thêm để JavaScript load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterUsername(String username) {
        try {
            elementUtils.waitForElementVisible(usernameField, 15);
            elementUtils.sendKeys(usernameField, username);
            System.out.println("Entered username: " + username);
        } catch (Exception e) {
            System.out.println("Trying alternative username field locator...");
            try {
                elementUtils.waitForElementVisible(usernameFieldAlt, 10);
                elementUtils.sendKeys(usernameFieldAlt, username);
                System.out.println("Entered username using alternative locator: " + username);
            } catch (Exception ex) {
                System.out.println("Error entering username: " + ex.getMessage());
                throw ex;
            }
        }
    }

    public void enterPassword(String password) {
        try {
            elementUtils.waitForElementVisible(passwordField, 15);
            elementUtils.sendKeys(passwordField, password);
            System.out.println("Entered password: " + "****");
        } catch (Exception e) {
            System.out.println("Trying alternative password field locator...");
            try {
                elementUtils.waitForElementVisible(passwordFieldAlt, 10);
                elementUtils.sendKeys(passwordFieldAlt, password);
                System.out.println("Entered password using alternative locator");
            } catch (Exception ex) {
                System.out.println("Error entering password: " + ex.getMessage());
                throw ex;
            }
        }
    }

    public void clickLoginButton() {
        try {
            elementUtils.waitForElementClickable(loginButton, 15);
            elementUtils.clickElement(loginButton);
            System.out.println("Clicked login button");
        } catch (Exception e) {
            System.out.println("Trying alternative login button locator...");
            try {
                elementUtils.waitForElementClickable(loginButtonAlt, 10);
                elementUtils.clickElement(loginButtonAlt);
                System.out.println("Clicked login button using alternative locator");
            } catch (Exception ex) {
                System.out.println("Trying JavaScript click...");
                try {
                    elementUtils.clickByJavaScript(loginButton);
                    System.out.println("Clicked login button using JavaScript");
                } catch (Exception exc) {
                    System.out.println("Error clicking login button: " + exc.getMessage());
                    throw exc;
                }
            }
        }

        // Wait a bit after clicking
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void login(String username, String password) {
        navigateToLoginPage();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            if (elementUtils.isElementDisplayed(errorMessage)) {
                return true;
            }
        } catch (Exception e1) {
            try {
                // Try alternative locator
                return elementUtils.isElementDisplayed(errorMessageAlt);
            } catch (Exception e2) {
                return false;
            }
        }
        return false;
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            return elementUtils.isElementDisplayed(successMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            String msg = elementUtils.getText(errorMessage);
            if (msg != null && !msg.isEmpty()) {
                return msg;
            }
        } catch (Exception e1) {
            try {
                // Try alternative locator
                return elementUtils.getText(errorMessageAlt);
            } catch (Exception e2) {
                // Last resort: check page source for error text
                String pageSource = driver.getPageSource();
                if (pageSource.contains("không chính xác")) {
                    return "Tên tài khoản hoặc mật khẩu không chính xác";
                } else if (pageSource.contains("Thông báo")) {
                    // Try to extract message from page source
                    return "Error message found in page";
                }
            }
        }
        return "";
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isLoginSuccessful() {
        // Kiểm tra nếu URL đã thay đổi sau khi login
        try {
            Thread.sleep(3000); // Đợi redirect
            String currentUrl = getCurrentUrl();
            System.out.println("Current URL after login: " + currentUrl);

            // Nếu không còn ở trang login thì coi như thành công
            return !currentUrl.contains("/auth/login");
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForPageLoad() {
        elementUtils.waitForPageLoad();
    }
}
