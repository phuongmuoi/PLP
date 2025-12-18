package com.happyorder.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for Home Page
 * Contains all elements and actions for the home page
 *
 * @author HappyOrder QA Team
 * @since 1.0
 */
public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ==================== PAGE ELEMENTS ====================

    // Header elements
    @FindBy(css = "h1.page-title, h1.home-title, .header-title")
    private WebElement pageTitle;

    @FindBy(css = ".user-profile, .user-info, #userProfile")
    private WebElement userProfile;

    @FindBy(css = ".logout-button, #btnLogout, button[name='logout']")
    private WebElement logoutButton;

    // Navigation elements
    @FindBy(css = ".nav-menu, #mainMenu, .main-navigation")
    private WebElement mainMenu;

    @FindBy(linkText = "Orders")
    private WebElement ordersLink;

    @FindBy(linkText = "Products")
    private WebElement productsLink;

    @FindBy(linkText = "Settings")
    private WebElement settingsLink;

    // Dashboard elements
    @FindBy(css = ".dashboard, #dashboard, .dashboard-container")
    private WebElement dashboardSection;

    @FindBy(css = ".welcome-message, .greeting, #welcomeMsg")
    private WebElement welcomeMessage;

    @FindBy(css = ".stats-card, .dashboard-stats, .summary-card")
    private WebElement statsCard;

    // Search elements
    @FindBy(css = "input[type='search'], #searchBox, .search-input")
    private WebElement searchBox;

    @FindBy(css = "button.search-btn, #btnSearch, .search-button")
    private WebElement searchButton;

    // Error/Success message
    @FindBy(css = ".alert, .message, .notification, .toast")
    private WebElement messageBox;

    // ==================== CONSTRUCTOR ====================

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ==================== NAVIGATION ACTIONS ====================

    /**
     * Navigate to home page
     */
    public void navigateToHomePage() {
        driver.get("https://happyorder.com/home"); // Update with actual URL
    }

    /**
     * Click on Orders link
     */
    public void clickOrders() {
        waitForElementClickable(ordersLink);
        ordersLink.click();
    }

    /**
     * Click on Products link
     */
    public void clickProducts() {
        waitForElementClickable(productsLink);
        productsLink.click();
    }

    /**
     * Click on Settings link
     */
    public void clickSettings() {
        waitForElementClickable(settingsLink);
        settingsLink.click();
    }

    /**
     * Click logout button
     */
    public void logout() {
        waitForElementClickable(logoutButton);
        logoutButton.click();
    }

    // ==================== SEARCH ACTIONS ====================

    /**
     * Perform search with keyword
     */
    public void search(String keyword) {
        waitForElementVisible(searchBox);
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchButton.click();
    }

    // ==================== VERIFICATION METHODS ====================

    /**
     * Check if user is on home page
     */
    public boolean isOnHomePage() {
        try {
            String currentUrl = driver.getCurrentUrl().toLowerCase();
            return currentUrl.contains("home") || currentUrl.contains("dashboard");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if page title is displayed
     */
    public boolean isPageTitleDisplayed() {
        try {
            waitForElementVisible(pageTitle);
            return pageTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if main menu is displayed
     */
    public boolean isMainMenuDisplayed() {
        try {
            waitForElementVisible(mainMenu);
            return mainMenu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if welcome message is displayed
     */
    public boolean isWelcomeMessageDisplayed() {
        try {
            waitForElementVisible(welcomeMessage);
            return welcomeMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if user profile is displayed
     */
    public boolean isUserProfileDisplayed() {
        try {
            waitForElementVisible(userProfile);
            return userProfile.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if dashboard is loaded
     */
    public boolean isDashboardLoaded() {
        try {
            waitForElementVisible(dashboardSection);
            return dashboardSection.isDisplayed() &&
                   isPageTitleDisplayed() &&
                   isMainMenuDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if specific element exists by CSS selector
     */
    public boolean isElementPresent(String cssSelector) {
        try {
            driver.findElement(By.cssSelector(cssSelector));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== GETTER METHODS ====================

    /**
     * Get page title text
     */
    public String getPageTitleText() {
        try {
            waitForElementVisible(pageTitle);
            return pageTitle.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get welcome message text
     */
    public String getWelcomeMessageText() {
        try {
            waitForElementVisible(welcomeMessage);
            return welcomeMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title from browser
     */
    public String getBrowserPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get message text (error/success)
     */
    public String getMessageText() {
        try {
            waitForElementVisible(messageBox);
            return messageBox.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // ==================== WAIT METHODS ====================

    /**
     * Wait for element to be visible
     */
    private void waitForElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be clickable
     */
    private void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for page to load
     */
    public void waitForPageLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
