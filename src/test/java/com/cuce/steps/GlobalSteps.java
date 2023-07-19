package com.cuce.steps;

import com.cuce.driver.SharedDriver;
import com.cuce.pages.*;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.cuce.util.PropertiesUtils.*;
import static org.testng.FileAssert.fail;


public class GlobalSteps {

    private final Logger logger = Logger.getLogger(GlobalSteps.class);
    @Getter
    private GlobalPage globalPage;
    @Getter
    private AccountPage accountPage;
    @Getter
    private HomePage homePage;
    @Getter
    private ItemListPage itemListPage;
    @Getter
    private ItemPage itemPage;
    @Getter
    private OrderPage orderPage;
    @Getter
    private WebDriver driver;
    @Getter
    private String emailXpath = "//input[@type='email']";
    @Getter
    private CartPage cartPage;
    @Getter
    private RegistrationPage registrationPage;
    @Getter
    private CheckoutPage checkoutPage;
    @Getter
    private LoginPage loginPage;

    public GlobalSteps(SharedDriver driver) {
        globalPage = new GlobalPage(driver);
        this.driver = driver;
    }

    /**
     * The method accepts direct links or fragments thereof.
     * If only the route is specified, the method will determine the domain by the environment.
     *
     * @param page = direct URL or URI (e.g. /my-account/login)
     */
    @When("^Go to '(.*)' page.*$")
    public void goToPage(String page) {
        String host = getTargetTestHost();
        String product = getTargetTestProduct();

        switch (page) {
            case "HOME":
                driver.manage().deleteAllCookies();
                globalPage.getUrl(host);
                driver.manage().deleteAllCookies();
                break;
            case "PRODUCT":
                driver.manage().deleteAllCookies();
                globalPage.getUrl(host + product);
                driver.manage().deleteAllCookies();
                break;
            default:
                boolean isUrl = Pattern.compile("(http|www)").matcher(page).find();
                if (isUrl) {
                    if (!page.startsWith("http")) {
                        page = "http://" + page;
                        driver.manage().deleteAllCookies();
                    }
                    driver.manage().deleteAllCookies();
                    logger.info("Go to " + page);
                    globalPage.getUrl(page);

                } else {
                    driver.manage().deleteAllCookies();
                    logger.info("Go to " + host + page);
                    globalPage.getUrl(host + page);
                }
        }
    }

    @And("^Wait (\\d+) Second$")
    public void implicitWait(int wait) {
        logger.info("Waiting " + wait + " Second");
        try {
            TimeUnit.SECONDS.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @And("^Refresh Current Page$")
    public void refreshCurrentPage() {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.refreshBrowserPage();
    }

    @And("^Switch to '(.*)' iFrame .*$")
    public void switchToIframe(String frame) {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.switchToIframe(frame);
    }


    @And("^Switch to Default iFrame .*$")
    public void switchToDefaultIframe() {
        driver.switchTo().defaultContent();
    }

    @And("^Press on '(.*)' Button By ID$")
    public void pressOnButtonById(String buttonId) {
        checkPageIsReady();
        globalPage.checkElemById(buttonId);
        globalPage.pressOnIdButton(buttonId);
    }


    @And("^Press on Elementa '(.*)' if Displayed By ID$")
    public void pressOnelementByIdIfDisplayed(String buttonId) {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.pressOnIdButtonifDisplayed(buttonId);
    }


    @And("^Check Element By Xpath '(.*)' on the Page .*$")
    public void checkElemByXpath(String buttonId) {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.checkElemByXpath(buttonId);
    }

    @And("^Check Prise on the Catalog Page !=0$")
    public void checkPriseOnCatalogPage(String buttonId) {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.checkElemByXpath(buttonId);
    }

    @When("^Enter '(.*)' text by id '(.*)' Field$")
    public void enterTextById(String txt, String id) {
        checkPageIsReady();
        globalPage.setDataInFormWithIds(id, txt);
    }


    @When("^Scroll To Element By Xpath '(.*)'$")
    public void autoScrollToWebElement(String xpath) {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.autoScrollToWebElement(xpath);
    }

    @When("^Enter '(.*)' text by Xpath '(.*)' Field .*$")
    public void enterTextByXpath(String txt, String xpath) {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.setDataInFormWithIdsXpath(xpath, txt);
    }


    @When("^Enter '(.*)' text in field form by id '(.*)' with replace space$")
    public void enterTextInCrCdFormById(String txt, String id) {
        checkPageIsReady();
        globalPage.waitElementOnCurrentPage(id);
        globalPage.setDataInFormByIdsWithReplace(id, txt);
    }


    @When("^Fill out Form on Current page:$")
    public void fillOutForm(DataTable dataTable) {
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            globalPage.setDataInFormWithIds(entry.getKey(), entry.getValue());
        }
    }

    @And("^Select '(.*)' by Visible Text in '(.*)' Drop Down List$")
    public void selectIdByVisibleTextInDropDownList(String value, String fieldId) {
        checkPageIsReady();
        globalPage.checkElemById(fieldId);
        globalPage.selectVisibleTextInDropDownListById(value, fieldId);
    }

    @Then("^Check '(.*)' Text on Current page$")
    public void checkTextOnCurrentPage(String text) {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.searchText(text);
    }

    @And("^Press on '(.*)' Button By Xpath.*$")
    public void pressOnButtonByXpath(String xpath) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.pressOnButtonByXpath(xpath);
    }

    @And("^If Element '(.*)' Display By Xpath Prass$")
    public void ifElementDisplayedpressOnButtonByXpath(String xpath) {
        globalPage.checkPageIsReadyLong();
        globalPage.pressOnButtonByXpathIfElementDisplayed(xpath);
    }


    @And("^Get Product Name On Catalog Page '(.*)' By Xpath.*$")
    public void getProductNameOnCatalodPage(String xpathFirstText) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.getFerstText(xpathFirstText);

    }

    @And("^Get Product Name On Product Page '(.*)' By Xpath.*$")
    public void getProductNameOnProductPage(String xpathFirstText) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.getFerstText(xpathFirstText);

    }


    @And("^Get Optional Product Name On Product Page '(.*)' By Xpath.*$")
    public void getOptionalProductNameOnProductPage(String xpathFirstText) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.get5Text(xpathFirstText);

    }

    @And("^Get Optional Product Name On Mini Cart '(.*)' By Xpath.*$")
    public void getOptionalProductNameOnMiniCart(String xpathFirstText) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.get6Text(xpathFirstText);

    }

    @And("^Get Product Price On Catalog Page '(.*)' By Xpath.*$")
    public void getProductPriceOnCatalodPage(String xpathFirstText) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.getPriceCatalogPage(xpathFirstText);

    }


    @And("^Get Product Name On Cart '(.*)' By Xpath.*$")
    public void getProductNameOnCartPage(String xpathSecondText) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.getSecondText(xpathSecondText);

    }


    @And("^Check Product Price On Cart '(.*)' By Xpath Not 0.*$")
    public void getProductPriceOnCartPageNot0(String xpathSecondText) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.checkPriceOnCatalogsPageOntNull(xpathSecondText);

    }

    @And("^Get Product Price On Cart '(.*)' By Xpath.*$")
    public void getProductPriceOnCartPage(String xpathSecondText) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.getPriceCart(xpathSecondText);

    }


    @And("^Verify subcategory '(.*)' on main menu.*$")
    public void countCatalogOnManu(String equalValue) throws InterruptedException {
        switch (equalValue.toUpperCase()) {
            case "RANNEKELLOT":
                checkPageIsReady();
                globalPage.subcategoryLinkOnMainMenuRannekellot();
                break;
            case "ALYKELLOT":
                checkPageIsReady();
                globalPage.subcategoryLinkOnMainMenuÄlykellot();
                break;
            case "URHEILUKELLOT":
                checkPageIsReady();
                globalPage.subcategoryLinkOnMainMenuUrheilukellot();
                break;
            case "KORUT":
                checkPageIsReady();
                globalPage.subcategoryLinkOnMainMenuKorut();
                break;
            case "SORMUKSET":
                checkPageIsReady();
                globalPage.subcategoryLinkOnMainMenuSormukset();
                break;
            case "LAHJAT":
                checkPageIsReady();
                globalPage.subcategoryLinkOnMainMenuLahjat();
                break;
            case "MUUT":
                checkPageIsReady();
                globalPage.subcategoryLinkOnMainMenuMuut();
                break;
            default:
                logger.info("Invalid entered section!");
                fail();
        }
    }


    @And("^Hover on '(.*)' By Xpath.*$")
    public void hoverOnElement(String xpath) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.hoverOnTheElementByXpath(xpath);
    }

    public void checkPageIsReady() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
        );
    }


    @Then("^Check current url with parameters '(.*)'$")
    public void getAndCheckCurrentUrl(String URL) {
        String currentUrl = globalPage.getCurrentUrl();
        boolean url = currentUrl.contains(URL);
        if (!url) {
            throw new AssertionError("\nER: " + URL + "\nAR: Wrong URL: " + currentUrl);
        }
    }


    @And("^Check Products in the Carousel Block Are Slide '(.*)'.*$")
    public void checkProductsCarouselAreSlide(String xpathSlider) throws InterruptedException {
        checkPageIsReady();
        globalPage.checkPageIsReadyLong();
        globalPage.checkCarouselBlockAreSlide(xpathSlider);

    }

    @Then("^Close Badge Notification$")
    public void pressCloseButton() {
        globalPage.checkPageIsReadyLong();
        globalPage.closePopupBrowserBadge();
    }

    @And("^Clear Cart$")
    public void clearCartAndGarage() throws InterruptedException {
        globalPage.checkPageIsReadyLong();
        globalPage.clearCart();
    }

    @And("^Close cookies Popup$")
    public void cookiePupap() throws InterruptedException {
        globalPage.checkPageIsReadyLong();
        globalPage.cookiefirstActioPopup();
    }

    @And("^Close Dialog Popup Form")
    public void closeDialogPopupForm() throws InterruptedException {
        globalPage.checkPageIsReadyLong();
        globalPage.dialogPopupForm();
    }


    @And("^Check Product Out Of Stock by Xpath '(.*)'.*$")
    public void productOutOfStockByXpath(String xpath) {
        globalPage.checkPageIsReadyLong();
        globalPage.checkElementNotDisplayedOnPage(xpath);
    }

    @And("^Check Product discount on Checkout Page.*$")
    public void checkDiscount() {
        globalPage.checkPageIsReadyLong();
        globalPage.getPriceAndCheckDiscount();
    }

    @And("^Check Product discount With Gift Cart on Checkout Page.*$")
    public void checkDiscountWithGiftCart() {
        globalPage.checkPageIsReadyLong();
        globalPage.getPriceAndCheckGiftCart();
    }

    @And("^Check Product discount With Gift Cart and Coupon Code on Checkout Page.*$")
    public void checkDiscountWithGiftCartAndCoupon() {
        globalPage.checkPageIsReadyLong();
        globalPage.getPriceAndCheckDiscountAndGiftCart();
    }


    @And("^Check Product discount Removed on Checkout Page.*$")
    public void checkDiscountIsRemoved() {
        globalPage.checkPageIsReadyLong();
        globalPage.getPriceAndCheckDiscountIsRemoved();
    }

    @And("^Add Casio Product to Cart.*$")
    public void addCasioProduct() {
        globalPage.checkPageIsReadyLong();
        globalPage.addProductToCartCasio();
    }


    @And("^Remove Gift Card If Already Exist on Checkout Page.*$")
    public void removeGiftCardIfAlreadyExist() {
        globalPage.checkPageIsReadyLong();
        globalPage.removeGiftCardIfAlreadyExist();
    }

    @And("^Press on Calendar By Xpath.*$")
    public void cortCalendar() throws InterruptedException {
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("June 2023")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("July 2023")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("August 2023")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("September 2023")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("October 2023")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("November 2023")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("December 2023")) {
            sortCalendarOnPage();
        }
      /*  if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("January 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains( "February 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("March 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("April 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains( "May 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("June 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains( "July 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("August 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("September 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains("October 2024")) {
            sortCalendarOnPage();
        }
        if (driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']//div/div/div)[1]")).getText().contains( "November 2024")) {
            sortCalendarOnPage();
        }*/
    }

    private void sortCalendarOnPage() {
        List<WebElement> cartQty = driver.findElements(By.xpath("//a[@class='ui-state-default']"));
        if (cartQty.size() > 0 && cartQty.get(0).isDisplayed()) {
            driver.findElement(By.xpath("//a[@class='ui-state-default']")).click();
        } else {
            driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
        }
    }


}