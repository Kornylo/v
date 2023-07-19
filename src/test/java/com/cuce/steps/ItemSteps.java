package com.cuce.steps;

import com.cuce.driver.SharedDriver;
import com.cuce.pages.*;
import org.openqa.selenium.WebDriver;
public class ItemSteps {

    private GlobalSteps globalSteps;
    private ItemListPage itemListPage;
    private HomePage homePage;
    private ItemPage itemPage;
    private GlobalPage globalPage;
    private WebDriver driver;

    public ItemSteps(SharedDriver driver) {
        globalSteps = new GlobalSteps(driver);
        globalPage = globalSteps.getGlobalPage();
        itemListPage = globalSteps.getItemListPage();
        itemPage = globalSteps.getItemPage();
        homePage = globalSteps.getHomePage();
        this.driver = driver;
    }




}


