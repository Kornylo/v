package com.cuce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends GlobalPage {

    private String pageUrl = "%s/my-account/registration/";
    private String txtCheckTwoStepRegPage = "xpath:>//*[contains(text(),\"Welcome, %s\")]";
    private String fieldIdEmail = "id:>email";
    private String fieldIdPassword = "id:>passwd1";
    private String fieldIdConfirmPass = "id:>passwd2";
    private String fieldIdFirstName = "id:>s_firstname";
    private String fieldIdLastName = "id:>s_lastname";
    private String fieldIdZip = "id:>s_zipcode";
    private String btnXpathCreateAcc = "xpath:>//*[contains(text(),\"Create your account\")]";
    private String btnXpathSaveAndContinue = "xpath:>//span[contains(text(),\"Save and Continue\")]";
    private String elemValidationEmailForm = "css:>.-not-empty";
    private String userIsLogib = "xpath:>//div[@class='my-acc-heading']";


    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void openPage(String host) {
        getUrl(pageUrl);
    }
    private String fieldUsername = "id:>username";
    private String fieldUsernameXpath = "//*[@id=\"username\"]";
    private String fieldPassword = "id:>password";
    private String fieldPasswordXpath = "//*[@id=\"password\"]";


    public void enterReLogin(String getUsernameReLogin) {
        getDriverWrapper().findElementByUntilWait(fieldUsername).click();
        getDriverWrapper().findElementByUntilWait(fieldUsername).sendKeys(getUsernameReLogin);
        String getUserName = getDriver().findElement(By.xpath(fieldUsernameXpath)).getAttribute("value");
        if(!getUserName.equals(getUsernameReLogin)){
            getDriverWrapper().findElementByUntilWait(fieldUsername).clear();
            getDriverWrapper().findElementByUntilWait(fieldUsername).sendKeys(getUsernameReLogin);
        }
    }

    public void enterRePassword(String getPassReLogin) {
        getDriverWrapper().findElementByUntilWait(fieldPassword).click();
        getDriverWrapper().findElementByUntilWait(fieldPassword).sendKeys(getPassReLogin);
        String getPassword = getDriver().findElement(By.xpath(fieldPasswordXpath)).getAttribute("value");
        if (!getPassword.equals(getPassReLogin)) {
            getDriverWrapper().findElementByUntilWait(fieldUsername).clear();
            getDriverWrapper().findElementByUntilWait(fieldUsername).sendKeys(getPassReLogin);
        }
    }

    public void enterEmail(String email) {
        getDriverWrapper().findElementByUntilWait(fieldIdEmail).clear();
        getDriverWrapper().findElementByUntilWait(fieldIdEmail).sendKeys(email);
    }

    public void clickOnEmailField() {
        getDriverWrapper().findElementByUntilWait(fieldIdEmail).isDisplayed();
        getDriverWrapper().findElementByUntilWait(fieldIdEmail).click();
    }

    public void enterPassword(String password) {
        getDriverWrapper().findElementByUntilWait(fieldIdPassword).clear();
        getDriverWrapper().findElementByUntilWait(fieldIdPassword).sendKeys(password);
    }

    public void clickOnPasswordField() {
        getDriverWrapper().findElementByUntilWait(fieldIdPassword).click();
    }

    public void confirmPassword(String confirmPass) {
        getDriverWrapper().findElementByUntilWait(fieldIdConfirmPass).clear();
        getDriverWrapper().findElementByUntilWait(fieldIdConfirmPass).sendKeys(confirmPass);
    }

    public void pressOnCreateAccBtn() {
        getDriverWrapper().findElementByUntilWait(btnXpathCreateAcc).click();
    }

    public void checkSecondRegPage(String user) {
        getDriverWrapper().findElementByUntilWait(txtCheckTwoStepRegPage, user).isDisplayed();
    }

    public void enterFirstName(String fName) {
        getDriverWrapper().findElementByUntilWait(fieldIdFirstName).clear();
        getDriverWrapper().findElementByUntilWait(fieldIdFirstName).sendKeys(fName);
    }

    public void enterLastName(String lName) {
        getDriverWrapper().findElementByUntilWait(fieldIdLastName).clear();
        getDriverWrapper().findElementByUntilWait(fieldIdLastName).sendKeys(lName);
    }

    public void enterZip(String zip) {
        getDriverWrapper().findElementByUntilWait(fieldIdZip).clear();
        getDriverWrapper().findElementByUntilWait(fieldIdZip).sendKeys(zip);
    }

    public void pressSaveContinueBtn() {
        getDriverWrapper().findElementByUntilWait(btnXpathSaveAndContinue).click();
    }



}