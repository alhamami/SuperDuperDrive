package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FilesPage {

    @FindBy(id="logout-button")
    private WebElement logoutButton;

    private WebDriver webDriver;

    public FilesPage(WebDriver webDriver) {

        this.webDriver = webDriver;

        PageFactory.initElements(webDriver, this);
    }

    public void logout() {

        logoutButton.click();
    }

    public boolean isDisplayed() {

        String currentUrl = webDriver.getCurrentUrl();

        boolean isHome = currentUrl.contains("home");

        return isHome;
    }


}
