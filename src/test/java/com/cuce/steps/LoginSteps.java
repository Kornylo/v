package com.cuce.steps;

import com.cuce.driver.SharedDriver;
import com.cuce.pages.LoginPage;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;

public class LoginSteps {

    private LoginPage loginPage;

    public LoginSteps(SharedDriver driver) {
        GlobalSteps globalSteps = new GlobalSteps(driver);
        loginPage = globalSteps.getLoginPage();
    }

}