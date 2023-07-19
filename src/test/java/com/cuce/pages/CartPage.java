package com.cuce.pages;

import org.openqa.selenium.*;


public class CartPage extends GlobalPage {
    private GlobalPage globalPage;
    public CartPage(WebDriver driver) {
        super(driver);
        globalPage = new GlobalPage(driver);
    }


}