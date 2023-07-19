package com.cuce.pages;

import org.openqa.selenium.WebDriver;

import static com.cuce.util.PropertiesUtils.getTargetTestHost;

public class HomePage extends GlobalPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }


    public void openPage() {
        String host = getTargetTestHost();
        getDriver().get(host);
    }
}