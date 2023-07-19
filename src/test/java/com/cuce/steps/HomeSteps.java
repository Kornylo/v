package com.cuce.steps;

import com.cuce.driver.SharedDriver;
import com.cuce.pages.HomePage;
public class HomeSteps {
    private GlobalSteps globalSteps;
    private HomePage homePage;

    public HomeSteps(SharedDriver driver) {
        globalSteps = new GlobalSteps(driver);
        homePage = globalSteps.getHomePage();
    }

}