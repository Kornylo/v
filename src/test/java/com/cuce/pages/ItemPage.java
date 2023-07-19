package com.cuce.pages;

import org.openqa.selenium.WebDriver;

public class ItemPage extends GlobalPage {
    private GlobalPage globalPage;


    public ItemPage(WebDriver driver) {
        super(driver);
        globalPage = new GlobalPage(driver);
    }




}