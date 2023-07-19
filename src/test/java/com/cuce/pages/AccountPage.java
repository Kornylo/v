package com.cuce.pages;

import com.cuce.driver.DriverWrapper;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
public class AccountPage extends GlobalPage {
    @Getter
    private Actions action;
    @Getter
    private DriverWrapper driverWrapper;
    @Getter
    private WebDriver driver;

    public AccountPage(WebDriver driver) {
        super(driver);
        action = new Actions(driver);
        driverWrapper = new DriverWrapper(driver);
        this.driver = driver;
    }

}