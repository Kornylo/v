package com.cuce.steps;

import com.cuce.driver.SharedDriver;
import com.cuce.pages.CheckoutPage;
import com.cuce.runs.CucumberTestRunner;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.log4j.Logger;

import java.util.Map;

public class CheckoutSteps {

    private final Logger logger = Logger.getLogger(CheckoutSteps.class);
    private CheckoutPage checkoutPage;

    public CheckoutSteps(SharedDriver driver) {
        GlobalSteps globalSteps = new GlobalSteps(driver);
        checkoutPage = globalSteps.getCheckoutPage();
    }


}