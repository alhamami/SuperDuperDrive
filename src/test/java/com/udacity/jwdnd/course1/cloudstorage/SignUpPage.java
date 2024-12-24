package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id="inputFirstName")
    private WebElement firstNameField;

    @FindBy(id="inputLastName")
    private WebElement lastNameField;

    @FindBy(id="inputUsername")
    private WebElement usernameField;

    @FindBy(id="inputPassword")
    private WebElement passwordField;

    @FindBy(id="buttonSignUp")
    private WebElement signUpButton;

    public SignUpPage(WebDriver webDriver) {

        PageFactory.initElements(webDriver, this);
    }

}
