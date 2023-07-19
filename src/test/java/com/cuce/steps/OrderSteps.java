package com.cuce.steps;

import com.cuce.driver.DriverWrapper;
import com.cuce.driver.SharedDriver;
import com.cuce.pages.*;
import lombok.Getter;
import org.openqa.selenium.WebDriver;


public class OrderSteps {

    private GlobalSteps globalSteps;
    private OrderPage orderPage;
    private CheckoutPage checkoutPage;
    private LoginPage loginPage;
    private GlobalPage globalPage;
    @Getter
    private WebDriver driver;
    @Getter
    private EqualPage equalPage;
    @Getter
    private DriverWrapper driverWrapper;


    public OrderSteps(SharedDriver driver) {
        equalPage = new EqualPage(driver);
        globalSteps = new GlobalSteps(driver);
        orderPage = globalSteps.getOrderPage();
        checkoutPage = globalSteps.getCheckoutPage();
        driverWrapper = new DriverWrapper(driver);
        loginPage = globalSteps.getLoginPage();
        globalPage = globalSteps.getGlobalPage();
        this.driver = driver;
    }




}