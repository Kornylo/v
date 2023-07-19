package com.cuce.steps;

import com.cuce.driver.SharedDriver;
import com.cuce.pages.*;
import cucumber.api.java.en.And;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static org.testng.FileAssert.fail;

public class EqualSteps {
    private final Logger logger = Logger.getLogger(EqualSteps.class);

    @Getter
    private HomePage homePage;
    @Getter
    private RegistrationPage registrationPage;
    @Getter
    private LoginPage loginPage;
    @Getter
    private AccountPage accountPage;
    @Getter
    private GlobalPage globalPage;
    @Getter
    private CartPage cartPage;
    @Getter
    private ItemPage itemPage;
    @Getter
    private CheckoutPage checkoutPage;
    @Getter
    private ItemListPage itemListPage;
    @Getter
    private OrderPage orderPage;
    @Getter
    private EqualPage equalPage;
    @Getter
    private WebDriver driver;


    public EqualSteps(SharedDriver driver) {
        homePage = new HomePage(driver);
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        globalPage = new GlobalPage(driver);
        cartPage = new CartPage(driver);
        itemPage = new ItemPage(driver);
        checkoutPage = new CheckoutPage(driver);
        itemListPage = new ItemListPage(driver);
        orderPage = new OrderPage(driver);
        equalPage = new EqualPage(driver);
        this.driver = driver;
    }

    @And("^Check equal between '(.*)' texts.*$")
    public void equalBetween(String equalValue) {
        switch (equalValue.toUpperCase()) {
            case "1AND2":
                globalPage.equalStringContains1_2();
                break;
            case "5AND6":
                globalPage.equalStringContains5_6();
                break;
            case "3AND4":
                globalPage.equalPrise();
                break;
            default:
                logger.info("Invalid entered section!");
                fail();
        }
    }


}



