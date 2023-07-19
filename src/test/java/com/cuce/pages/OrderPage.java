package com.cuce.pages;

import com.cuce.driver.DriverWrapper;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;


public class OrderPage extends GlobalPage {
    @Getter
    private Actions action;
    @Getter
    private WebDriver driver;
    @Getter
    private DriverWrapper driverWrapper;

    public OrderPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        action = new Actions(driver);
        driverWrapper = new DriverWrapper(driver);
    }






}