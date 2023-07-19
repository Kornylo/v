package com.cuce.steps;

import com.cuce.driver.SharedDriver;

import com.cuce.pages.ItemListPage;
import com.cuce.pages.ItemPage;

public class ItemListSteps {
    private GlobalSteps globalSteps;
    private ItemListPage itemListPage;
    private ItemPage itemPage;

    public ItemListSteps(SharedDriver driver) {
        globalSteps = new GlobalSteps(driver);
        itemListPage = globalSteps.getItemListPage();
        itemPage = globalSteps.getItemPage();
    }

}