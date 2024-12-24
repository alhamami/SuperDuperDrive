package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class CredentialsPage {

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialsSection;

    @FindBy(id="buttonAddCredential")
    private WebElement addCredentialButton;

    @FindBy(id="credential-url")
    private WebElement credentialUrl;

    @FindBy(id="credential-username")
    private WebElement credentialUsername;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    @FindBy(xpath = "(//th[@id='createdCredentialUrl'])[last()]")
    private WebElement createdCredentialUrl;

    @FindBy(xpath = "(//td[@id='createdCredentialUsername'])[last()]")
    private WebElement createdCredentialUsername;

    @FindBy(xpath = "(//td[@id='createdCredentialPassword'])[last()]")
    private WebElement createdCredentialPassword;

    @FindBy(xpath = "(//td[@id='createdCredentialkeySecret'])[last()]")
    private WebElement createdCredentialkeySecret;

    @FindBy(id="credentialSubmit")
    private WebElement saveCredentialButton;

    @FindBy(id="closedCredentialButton")
    private WebElement closedCredentialButton;

    @FindBy(xpath = "(//button[@id='editedCredential'])[last()]")
    private WebElement editedCredential;

    @FindBy(xpath = "(//a[@id='deletedCredential'])[last()]")
    private WebElement deletedCredential;

    private final JavascriptExecutor jsExecutor;

    private WebDriver webDriver;


    public CredentialsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
        jsExecutor = (JavascriptExecutor) webDriver;
    }


    public void createCredential(String url, String username, String password) {

        jsExecutor.executeScript("arguments[0].click();", credentialsSection);

        jsExecutor.executeScript("arguments[0].click();", addCredentialButton);

        jsExecutor.executeScript("arguments[0].value='" + url + "';", credentialUrl);

        jsExecutor.executeScript("arguments[0].value='"+ username +"';", credentialUsername);

        jsExecutor.executeScript("arguments[0].value='"+ password +"';", credentialPassword);

        jsExecutor.executeScript("arguments[0].click();", saveCredentialButton);
    }

    public boolean isCredentialDisplayed(String url, String username, String password) {

        jsExecutor.executeScript("arguments[0].click();", credentialsSection);

        String credentialUrlValue = createdCredentialUrl.getAttribute("innerHTML");
        String credentialUsernameValue = createdCredentialUsername.getAttribute("innerHTML");
        String credentialPasswordValue = createdCredentialPassword.getAttribute("innerHTML");




        boolean isCredential = credentialUrlValue.equals(url) && credentialUsernameValue.equals(username) && !(credentialPasswordValue.equals(password));


        return isCredential;
    }

    public boolean isCredentialPasswordEncrypted( String password) {

        jsExecutor.executeScript("arguments[0].click();", credentialsSection);

        String credentialPasswordValue = createdCredentialPassword.getAttribute("innerHTML");

        boolean isCredential = credentialPasswordValue.equals(password);


        return !isCredential;
    }


    public void editCredential(String url, String username, String password) {

        jsExecutor.executeScript("arguments[0].click();", credentialsSection);

        jsExecutor.executeScript("arguments[0].click();", editedCredential);

        jsExecutor.executeScript("arguments[0].value='" + url + "';", credentialUrl);

        jsExecutor.executeScript("arguments[0].value='"+ username +"';", credentialUsername);

        jsExecutor.executeScript("arguments[0].value='"+ password +"';", credentialPassword);

        jsExecutor.executeScript("arguments[0].click();", saveCredentialButton);

    }

    public boolean isCredentialPasswordDecrypted(String expectedPassword) {
        boolean isDecrypted = false;

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(100));

        WebElement credentialsSectionElem = wait.until(ExpectedConditions.visibilityOf(credentialsSection));
        credentialsSectionElem.click();

        WebElement credentialsEditElem = wait.until(ExpectedConditions.visibilityOf(editedCredential));
        credentialsEditElem.click();

        WebElement decryptedPassword = wait.until(ExpectedConditions.visibilityOf(credentialPassword));
        String decryptedPasswordValue = decryptedPassword.getAttribute("value");

        if (decryptedPasswordValue.equals(expectedPassword)) {
            isDecrypted = true;
        }

        return isDecrypted;
    }


    public void deleteCredential() {

        jsExecutor.executeScript("arguments[0].click();", credentialsSection);

        jsExecutor.executeScript("arguments[0].click();", deletedCredential);

    }


}
