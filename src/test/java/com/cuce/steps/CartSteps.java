package com.cuce.steps;

import com.cuce.driver.DriverWrapper;
import com.cuce.driver.SharedDriver;
import com.cuce.pages.CartPage;
import com.cuce.pages.GlobalPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

public class CartSteps {

    private CartPage cartPage;
    private GlobalPage globalPage;
    @Getter
    private DriverWrapper driverWrapper;
    @Getter
    private WebDriver driver;

    public CartSteps(SharedDriver driver) {

        GlobalSteps globalSteps = new GlobalSteps(driver);
        cartPage = globalSteps.getCartPage();
        globalPage = globalSteps.getGlobalPage();
        driverWrapper = new DriverWrapper(driver);
        this.driver = driver;

    }

}