package com.happyorder.pages;

import com.happyorder.utils.WebElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage {
    private WebDriver driver;
    private WebElementUtils elementUtils;

    // Locators
    private By searchBox = By.name("q");
    private By searchButton = By.name("btnK");
    private By luckyButton = By.name("btnI");

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new WebElementUtils(driver);
    }

    public void navigateToGoogle() {
        driver.get("https://www.google.com");
    }

    public void enterSearchText(String searchText) {
        elementUtils.sendKeys(searchBox, searchText);
    }

    public void clickSearchButton() {
        elementUtils.clickElement(searchButton);
    }

    public void performSearch(String searchText) {
        enterSearchText(searchText);
        driver.findElement(searchBox).submit();
    }

    public boolean isSearchBoxDisplayed() {
        return elementUtils.isElementDisplayed(searchBox);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
