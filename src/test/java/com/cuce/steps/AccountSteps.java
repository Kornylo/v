package com.cuce.steps;

import com.cuce.driver.SharedDriver;
import com.cuce.pages.AccountPage;
import com.cuce.pages.GlobalPage;
public class AccountSteps {

    private AccountPage accountPage;
    public GlobalPage globalPage;
    public AccountSteps(SharedDriver driver) {
        GlobalSteps globalSteps = new GlobalSteps(driver);
        accountPage = globalSteps.getAccountPage();
        globalPage = globalSteps.getGlobalPage();
    }


}