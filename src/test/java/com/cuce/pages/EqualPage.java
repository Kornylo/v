package com.cuce.pages;

import com.cuce.driver.DriverWrapper;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class EqualPage {
    @Getter
    private Actions action;
    @Getter
    private WebDriver driver;
    @Getter
    private static DriverWrapper driverWrapper;
    @Getter
    private GlobalPage globalPage;
    @Getter
    private CartPage cartPage;



    public EqualPage(WebDriver driver) {
        this.driver = driver;
        action = new Actions(driver);
        driverWrapper = new DriverWrapper(driver);
        globalPage = new GlobalPage(driver);
        cartPage = new CartPage(driver);
    }


}




