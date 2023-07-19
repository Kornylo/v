package com.cuce.steps;

import com.cuce.driver.SharedDriver;
import com.cuce.pages.*;
import org.openqa.selenium.WebDriver;
public class RegistrationSteps {

    private RegistrationPage registrationPage;
    private CheckoutPage checkoutPage;
    private GlobalPage globalPage;
    private LoginPage loginPage;
    private WebDriver driver;
    private GlobalSteps globalSteps;
    private CartPage cartPage;


    public RegistrationSteps(SharedDriver driver) {
//        GlobalSteps globalSteps = new GlobalSteps(driver);
        globalSteps = new GlobalSteps(driver);
        globalPage = globalSteps.getGlobalPage();
        loginPage = globalSteps.getLoginPage();
        registrationPage = globalSteps.getRegistrationPage();
        checkoutPage = globalSteps.getCheckoutPage();
        cartPage = globalSteps.getCartPage();
        this.driver = driver;


    }


}