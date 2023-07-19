package com.cuce.pages;

import com.cuce.driver.DriverWrapper;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.cuce.util.PropertiesUtils.*;

public class GlobalPage {

    private final Logger logger = Logger.getLogger(GlobalPage.class);
    @Getter
    private Actions action;
    @Getter
    private WebDriver driver;
    @Getter
    private DriverWrapper driverWrapper;
    private String textBoolCheck = "xpath:>//*[contains(text(),\"%s\")]";
    private String elementById = "id:>%s";
    private String elementByCss = "css:>%s";
    private String elementByXpath = "xpath:>%s";
    private String elementByText = "xpath:>//body//*[contains(text(),\"%s\")]";
    private String userName1 = "DB username";
    private String password1 = "DB password";
    private String connectionUrl1 = "jdbc:mysql://URL";
    private String connectionUrlProd1 = "jdbc:mysql://URL";
    private String userNameProd1 = "DB username";
    private String passwordProd1 = "DB password";
    private String elemCloseBrowserPopupUserWithoutCookies = "xpath:>//span[text()='Hyväksy']";

    private String cartIconQtyFined = "//*[@class=\"action showcart\"]//*[text()>'0']";
    private String cartIconQty = "//a[@class='action showcart']";
    private String baseDevURL = "https://goodahead:qa123123@kk-int.goodahead.dev/";
    private String gottenYear;
    private String gottenModel;
    private String gottenMake;
    private String gottenYMM;
    @Getter
    private static String actualText1;
    @Getter
    private static String actualText2;
    private String actualText5;
    private String actualText6;
    private double priseCart;
    private double priseCatalodPage;
    private String getProductPriceXpath = "xpath:>(//td[@class='amount']//span)[1]";
    private String getCartSubtotal = "xpath:>(//td[@class='amount']//span)[1]";
    private String getDiscountXpath = "xpath:>//tr[@class='totals discount']//th[1]/../td";
    private String getTotalXpath = "xpath:>(//span[@data-bind='text: getValue()'])[3]";
    private String getGiftCartXpath = "xpath:>(//td[@class='amount'])[3]";
    private String getGiftCartXpath2 = "xpath:>(//td[@class='amount'])[2]";

    private String getTaxXpath = "xpath:>(//td[@class='amount'])[4]";

    public GlobalPage(WebDriver driver) {
        this.driver = driver;
        action = new Actions(driver);
        driverWrapper = new DriverWrapper(driver);
    }

    /**
     * Follow the direct link
     *
     * @param host = direct link
     */
    public void getUrl(String host) {
        driver.get(host);
    }

    /**
     * Native browser button with page refresh
     */
    public void refreshBrowserPage() {
        driver.navigate().refresh();
    }

    /**
     * Search for text on the current page
     *
     * @param text = search text
     */
    public void searchText(String text) {
        if (driverWrapper.findElementByUntilWait(elementByText, text).isDisplayed()) {
            assert driverWrapper.findElementByUntilWait(elementByText, text).isDisplayed();
        } else {
            refreshBrowserPage();
            waitElementOnCurrentPage(elementByText);
            logger.info("Caught exception: ");
            assert driverWrapper.findElementByUntilWait(elementByText, text).isDisplayed();
        }

    }

    /**
     * Entering data by id field
     *
     * @param id         = id field
     * @param fieldValue = value
     */
    public void setDataInFormWithIds(String id, String fieldValue) {
        driverWrapper.findElementByUntilWait(elementById, id).clear();
        driverWrapper.findElementByUntilWait(elementById, id).sendKeys(fieldValue);
    }

    /**
     * Extract current link from browser address bar
     *
     * @return url
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Entering data by id field
     *
     * @param xpath      = xpath field
     * @param fieldValue = value
     */
    public void setDataInFormWithIdsXpath(String xpath, String fieldValue) {
        String inputText = driverWrapper.findElementByUntilWait(elementByXpath, xpath).getAttribute("value");
        driverWrapper.findElementByUntilWait(elementByXpath, xpath).sendKeys(Keys.chord(Keys.CONTROL, "a"), "");
        if (inputText != null) {
            for (int i = 0; i < inputText.length(); i++) {
                driverWrapper.findElementByUntilWait(elementByXpath, xpath).sendKeys(Keys.BACK_SPACE);
            }
        }
        driverWrapper.findElementByUntilWait(elementByXpath, xpath).clear();
        driverWrapper.findElementByUntilWait(elementByXpath, xpath).sendKeys(fieldValue);

    }


    public void setDataInFormByIdsWithReplace(String id, String fieldValue) {
        driverWrapper.findElementByUntilWait(elementById, id).clear();
        driverWrapper.findElementByUntilWait(elementById, id).sendKeys(fieldValue);
        String getEnteredValue = getDriver().findElement(By.id(id)).getAttribute("value").replace(" ", "");
        logger.info("Gotten number of card is: " + getEnteredValue);
        if (!getEnteredValue.equals(fieldValue)) {
            driverWrapper.findElementByUntilWait(elementById, id).clear();
            driverWrapper.findElementByUntilWait(elementById, id).sendKeys(fieldValue);
        }
    }

    /**
     * Parameter selection by text in dropdown by id
     *
     * @param value = visible text
     * @param id    = dropdown id
     */
    public void selectVisibleTextInDropDownListById(String value, String id) {
        new Select(driverWrapper.findElement(elementById, id)).selectByVisibleText(value);
    }

    /**
     * Pressing on an element by id
     *
     * @param idBtn = id
     */
    public void pressOnIdButton(String idBtn) {
        action.moveToElement(driverWrapper.findElementByUntilWait(elementById, idBtn)).perform();
        driverWrapper.findElementByUntilWait(elementById, idBtn).click();
    }


    public void pressOnIdButtonifDisplayed(String idBtn) {
        if (driverWrapper.findElementByImplicitWait(elementById, idBtn, 2) != null) {
            driverWrapper.findElementByUntilWait(elementById, idBtn).click();
            logger.info("elementById is Displayed");
        }

    }


    /**
     * Check Element By Id
     *
     * @param id = Element id
     */
    public void checkElemById(String id) {
        waitElementOnCurrentPage(id);
        driverWrapper.findElementByImplicitWait(elementById, id, 10);
        driverWrapper.findElementByUntilWait(elementById, id).isEnabled();
    }


    /**
     * Check Element By Id
     *
     * @param xpath = Element elementByXpath
     */
    public void checkElemByXpath(String xpath) {
        driverWrapper.findElementByImplicitWait(elementByXpath, xpath, 10);
        assert driverWrapper.findElementByUntilWait(elementByXpath, xpath).isEnabled();
    }


    /**
     * Switch to Iframe zone
     *
     * @param frameName = iframe name
     */
    public void switchToIframe(String frameName) {
        logger.info("Switch to iFrame: " + frameName);
        try {
            driver.switchTo().frame(frameName);
            checkPageIsReadyLong();
        } catch (StaleElementReferenceException e) {
            logger.info("Retry...switch to iFrame: " + frameName);
            checkPageIsReadyLong();
            driver.switchTo().frame(frameName);
            checkPageIsReadyLong();

        }
    }


    /**
     * Wait for an web element for all types of locator (This is a bad method, if possible it should be eliminated)
     *
     * @param valueElem = web element
     */
    public void waitElementOnCurrentPage(String valueElem) {
        if (driverWrapper.findElementByImplicitWait(elementById, valueElem, 2) != null) {
            driverWrapper.findElementByUntilWait(elementById, valueElem).isDisplayed();
            logger.info("elementById is Displayed");
        } else if (driverWrapper.findElementByImplicitWait(elementByCss, valueElem, 2) != null) {
            driverWrapper.findElementByUntilWait(elementByCss, valueElem).isDisplayed();
            logger.info("elementByCss is Displayed");
        } else if (driverWrapper.findElementByImplicitWait(elementByText, valueElem, 2) != null) {
            driverWrapper.findElementByUntilWait(elementByText, valueElem).isDisplayed();
            logger.info("elementByText is Displayed");
        } else if (driverWrapper.findElementByImplicitWait(elementByXpath, valueElem, 2) != null) {
            driverWrapper.findElementByUntilWait(elementByXpath, valueElem).isDisplayed();
            logger.info("elementByXpath is Displayed");
        } else {
            Assert.fail("Element " + valueElem + " NOT Displayed");
        }
    }


    /**
     * Make a scroll of the screen to searched element
     *
     * @param xpath = element
     */
    public void autoScrollToWebElement(String xpath) {
        WebElement element = driverWrapper.findElement(elementByXpath, xpath);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Make a scroll of the screen to searched element
     *
     * @param id = element
     */
    public void autoScrollToWebElementByID(String id) {

        WebElement element = driverWrapper.findElement(elementById, id);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    /**
     * Scroll up or down
     *
     * @param scroll = direction value
     */
    public void scrollWindow(String scroll) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(" + scroll + ")");
    }


    /**
     * Press on xpath element
     *
     * @param xpath = element
     */

    public void pressOnButtonByXpath(String xpath) throws InterruptedException {
        //  dialogPopupForm();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(driverWrapper.findElement(elementByXpath, xpath)));
        element.click();

    }

    public void pressOnButtonByXpathIfElementDisplayed(String xpath) {
        List<WebElement> ElementDisplayed = driver.findElements(By.xpath(xpath));
        if (ElementDisplayed.size() > 0 && ElementDisplayed.get(0).isDisplayed()) {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(driverWrapper.findElement(elementByXpath, xpath)));
            element.click();
        }


    }


    private Statement connectToDB() throws SQLException {
        if (isProdEnvironment()) {
            Connection conn = DriverManager.getConnection(connectionUrlProd1, userNameProd1, passwordProd1);
            logger.info("Connect to prod DB!");
            return conn.createStatement();
        } else {
            Connection conn = DriverManager.getConnection(connectionUrl1, userName1, password1);
            logger.info("Connect to DEV DB!");
            return conn.createStatement();
        }
    }

    public void get5Text(String xpathFirstText) {
        WebElement option1 = driverWrapper.findElement(elementByXpath, xpathFirstText);
        actualText5 = option1.getText();
        System.out.println(actualText5);
    }


    public void get6Text(String xpathSecondText) {
        WebElement option1 = driverWrapper.findElement(elementByXpath, xpathSecondText);
        actualText6 = option1.getText();
        System.out.println(actualText6);
    }

    public void equalStringContains5_6() {
        assert actualText5.contains(actualText6);
    }

    public void getFerstText(String xpathFirstText) {
        WebElement option1 = driverWrapper.findElement(elementByXpath, xpathFirstText);
        actualText1 = option1.getText();
    }


    public void getSecondText(String xpathSecondText) {
        WebElement option1 = driverWrapper.findElement(elementByXpath, xpathSecondText);
        actualText2 = option1.getText();

    }

    public void equalStringContains1_2() {
        assert actualText1.contains(actualText2);
    }

    public void equalPrise() {
        assert priseCart == priseCatalodPage;
    }

    /**
     * Creating object of an Actions class
     * Performing the mouse hover action on the target element.
     *
     * @param xpath = element
     */
    public void hoverOnTheElementByXpath(String xpath) throws InterruptedException {
        Thread.sleep(3000);
        Actions actions = new Actions(driver);
        WebElement menuOption = driverWrapper.findElement(elementByXpath, xpath);
        actions.moveToElement(menuOption).perform();
    }

    public void checkPageIsReadyLong() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Initially bellow given if condition will check ready state of page.
        if (js.executeScript("return document.readyState").toString().equals("complete")) {
            System.out.println("Page Is loaded.");
            return;
        }
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            //To check page ready state.
            if (js.executeScript("return document.readyState").toString().equals("complete")) {
                break;
            }
        }
    }


    public void subcategoryLinkOnMainMenuRannekellot() throws InterruptedException {
        veryfCatalogUsingMainManu(
                "//span[text()='Rannekellot']",
                "//div[@class='magezon-builder active']//div[contains(@class,'sxus8sm mgz-element')]//a",
                "(//div[@class='magezon-builder active']//div[contains(@class,'sxus8sm mgz-element')]//a)");
    }

    public void subcategoryLinkOnMainMenuÄlykellot() throws InterruptedException {
        veryfCatalogUsingMainManu(
                "//span[text()='Älykellot']",
                "//div[contains(@class,'gjhqm0t mgz-element')]//a",
                "(//div[contains(@class,'mvglqrm mgz-element')]//a)"
        );
        veryfCatalogUsingMainManu(
                "//span[text()='Älykellot']",
                "//div[contains(@class,'d4emofi mgz-element')]//a",
                "(//div[contains(@class,'y506v55 mgz-element')]//a)"
        );
        veryfCatalogUsingMainManu(
                "//span[text()='Älykellot']",
                "//div[contains(@class,'uuagcwu mgz-element')]//a",
                "(//div[contains(@class,'e21u4ys mgz-element')]//a)"
        );
    }

    public void subcategoryLinkOnMainMenuUrheilukellot() throws InterruptedException {
        veryfCatalogUsingMainManu(
                "//span[text()='Urheilukellot']",
                "//div[contains(@class,'st27d1q mgz-element')]//a",
                "(//div[contains(@class,'ioyfawg mgz-element')]//a)"
        );
        veryfCatalogUsingMainManu(
                "//span[text()='Urheilukellot']",
                "//div[contains(@class,'yxqva0o mgz-element')]//a",
                "(//div[contains(@class,'yxqva0o mgz-element')]//a)"
        );
        veryfCatalogUsingMainManu(
                "//span[text()='Urheilukellot']",
                "//div[contains(@class,'t92jo5j mgz-element')]//a",
                "(//div[contains(@class,'t92jo5j mgz-element')]//a)"
        );
    }

    public void subcategoryLinkOnMainMenuKorut() throws InterruptedException {
        veryfCatalogUsingMainManu(
                "//div[contains(@class,'lrnyijm mgz-element')]//a//span[text()='Korut']",
                "//div[contains(@class,'mgz-element-inner k1hfc9y-s')]//div/div/div//a",
                "(//div[contains(@class,'mgz-element-inner k1hfc9y-s')]//div/div/div//a)"
        );
    }

    public void subcategoryLinkOnMainMenuSormukset() throws InterruptedException {
        veryfCatalogUsingMainManu(
                "//span[text()='Sormukset']",
                "//div[contains(@class,'ab12800 mgz-element')]//div/div/div//a",
                "(//div[contains(@class,'ab12800 mgz-element')]//div/div/div//a)"
        );
    }

    public void subcategoryLinkOnMainMenuLahjat() throws InterruptedException {
        veryfCatalogUsingMainManu(
                "//span[text()='Lahjat']",
                "//div[contains(@class,'j1pjcfr mgz-element')]//div/div/div//a",
                "(//div[contains(@class,'j1pjcfr mgz-element')]//div/div/div//a)"
        );
    }

    public void subcategoryLinkOnMainMenuMuut() throws InterruptedException {
        veryfCatalogUsingMainManu(
                "//span[text()='Muut']",
                "//div[contains(@class,'ddytun8 mgz-element')]//div/div//a",
                "(//div[contains(@class,'ddytun8 mgz-element')]//div/div//a)"
        );
    }

    private void veryfCatalogUsingMainManu(String xpath, String xpathExpression, String x) throws InterruptedException {
        Thread.sleep(1000);
        hoverOnTheElementByXpath(xpath);
        List<WebElement> elements = driver.findElements(By.xpath(xpathExpression));
        int elementsCount = elements.size();
        System.out.println("Total Number of Elements : " + elementsCount);
        for (int i = elementsCount; i > 0; i--) {
            //checkPageIsReady();
            hoverOnTheElementByXpath(xpath);
            Thread.sleep(1000);
            try {
                pressOnButtonByXpath(x + "[" + i + "]");
            } catch (Exception e) {
                refreshBrowserPage();
                // checkPageIsReady();
                hoverOnTheElementByXpath(xpath);
                pressOnButtonByXpath(x + "[" + i + "]");
            }
            // checkPageIsReady();
            try {
                checkElemByXpath("//div[@class='breadcrumbs']/following-sibling::main[1]");
            } catch (Exception e) {
                System.out.println("Error on the catalog " + x + i + "]");
            }
            Thread.sleep(1000);


        }
    }


    public void getPriceCatalogPage(String xpathFirstText) {
        WebElement option1 = driverWrapper.findElement(elementByXpath, xpathFirstText);
        String priseCatalodPageString = option1.getText().replaceAll(" €", "").replaceAll(",", ".").replaceAll(" ", "");
        priseCatalodPage = Double.parseDouble(priseCatalodPageString);
        System.out.println(priseCatalodPage);

    }

    public void getPriceCart(String xpathSecondText) {
        WebElement option1 = driverWrapper.findElement(elementByXpath, xpathSecondText);
        String priseCartString = option1.getText().replaceAll(" €", "").replaceAll(",", ".").replaceAll(" ", "");
        priseCart = Double.parseDouble(priseCartString);
        System.out.println(priseCart);
    }


    public void checkPriceOnCatalogsPageOntNull(String xpathFirstText) {
        WebElement option1 = driverWrapper.findElement(elementByXpath, xpathFirstText);
        actualText2 = option1.getText();
        if (actualText2.contains("0,00 €")) {
            Assert.fail("Element " + actualText2.contains("0,00 €") + " 0,00 € Displayed");
        }

    }


    public void checkCarouselBlockAreSlide(String xpathSlider) {
        WebElement slider = driverWrapper.findElement(elementByXpath, xpathSlider);
        Actions move = new Actions(driver);
        Action action = (Action) move.dragAndDropBy(slider, 30, 0).build();
        ((Actions) action).perform();

    }


    public void closePopupBrowserBadge() {
        if (getDriverWrapper().findElementByImplicitWait(elemCloseBrowserPopupUserWithoutCookies, 5) != null) {
            getDriverWrapper().findElementByUntilWait(elemCloseBrowserPopupUserWithoutCookies).click();
        }
    }

    public void implicitWait(int wait) {
        logger.info("Waiting " + wait + " Second");
        try {
            TimeUnit.SECONDS.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cookiefirstActioPopup() throws InterruptedException {
        // checkPageIsReady();
        Thread.sleep(1000);
        List<WebElement> cartQty = driver.findElements(By.xpath("//button[@data-cookiefirst-action='accept']//span[1]"));
        if (cartQty.size() > 0 && cartQty.get(0).isDisplayed()) {
            driver.findElement(By.xpath("//button[@data-cookiefirst-action='accept']//span[1]")).click();
            logger.info("cookie Popup Closed");
        } else {
            logger.info("cookie Popup missing");
        }
    }

    public void dialogPopupForm() throws InterruptedException {
        // checkPageIsReady();
        List<WebElement> cartQty = driver.findElements(By.xpath("((//div[contains(@role,'dialog')])[2]//button)[1]"));
        if (cartQty.size() > 0 && cartQty.get(0).isDisplayed()) {
            driver.findElement(By.xpath("((//div[contains(@role,'dialog')])[2]//button)[1]")).click();
            logger.info("dialog Popup Closed");
        } else {
            logger.info("Dialog Popup missing");
        }
    }


    public void clearCart() throws InterruptedException {
        // checkPageIsReady();
        Thread.sleep(3000);
        List<WebElement> cartQty = driver.findElements(By.xpath("//*[@class=\"action showcart\"]//*[text()>'0']"));
        if (cartQty.size() > 0 && cartQty.get(0).isDisplayed()) {
            driver.findElement(By.xpath(cartIconQty)).click();
            driver.findElement(By.xpath("//span[text()='Poista tuote']")).click();
            driver.findElement(By.xpath("//span[text()='OK']")).click();
            logger.info("Clear cart");
            driver.navigate().refresh();
            //   this.implicitWait(1);
        } else {
            logger.info("Cart is Empty");
        }
    }

    public void checkElementNotDisplayedOnPage(String xpath) {
        if (driverWrapper.findElementByImplicitWait(elementByText, xpath, 0) != null) {
            Assert.fail("Element  Displayed");
        }
    }

    double getDeliveryPrice;
    double total;

    public void getPriceAndCheckDiscount() {
        double getProductPrice = Double.parseDouble(getDriverWrapper().findElementByUntilWait(getProductPriceXpath).getText().replaceAll("[€,]", ""));
        double getDiscount = Double.parseDouble(getDriverWrapper().findElementByUntilWait(getDiscountXpath).getText().replaceAll("[€,]", ""));
        double getTotal = Double.parseDouble(getDriverWrapper().findElementByUntilWait(getTotalXpath).getText().replaceAll("[€,]", ""));
        System.out.println("getProductPrice " + getProductPrice);
        System.out.println("getDeliveryPrice " + getDeliveryPrice);
        System.out.println("getDiscount " + getDiscount);
        System.out.println("getTotal " + getTotal);
        total = Double.parseDouble(String.valueOf(Math.round(getProductPrice + getDeliveryPrice + (getDiscount))));
        System.out.println(total);
        assert total == getTotal;

    }

    String getDiscount;
    String getGiftCart;

    public void getPriceAndCheckDiscountAndGiftCart() {
        String getProductPrice = getDriverWrapper().findElementByUntilWait(getProductPriceXpath).getText().replaceAll("[€,]", "");
        getDiscount = getDriverWrapper().findElementByUntilWait(getDiscountXpath).getText().replaceAll("[€,]", "").replaceAll("-", "");
        getGiftCart = getDriverWrapper().findElementByUntilWait(getGiftCartXpath).getText().replaceAll("[€,]", "").replaceAll("-", "");
        String getTotal = getDriverWrapper().findElementByUntilWait(getTotalXpath).getText().replaceAll("[€,]", "");
        String getTax = getDriverWrapper().findElementByUntilWait(getTaxXpath).getText().replaceAll("[€,]", "");
        System.out.println("getProductPrice " + getTax);
        System.out.println("getProductPrice " + getProductPrice);
        System.out.println("getDeliveryPrice " + getDeliveryPrice);
        System.out.println("getDiscount " + getDiscount);
        System.out.println("getGiftCart " + getGiftCart);
        System.out.println("getTotal " + getTotal);
        total = Double.parseDouble(getProductPrice) - Double.parseDouble(getDiscount) - Double.parseDouble(getGiftCart) + Double.parseDouble(getTax);
        System.out.println(getTotal);
        System.out.println(total);
        assert total == Double.parseDouble(getTotal);

    }

    double getGiftTax;

    public void getPriceAndCheckGiftCart() {

        double getProductPrice = Double.parseDouble(getDriverWrapper().findElementByUntilWait(getCartSubtotal).getText().replaceAll("[€,]", ""));
        double getGiftCart = Double.parseDouble(getDriverWrapper().findElementByUntilWait(getGiftCartXpath2).getText().replaceAll("[€,]", "").replaceAll("-", ""));
        double getTotal = Double.parseDouble(getDriverWrapper().findElementByUntilWait(getTotalXpath).getText().replaceAll("[€,]", ""));
        List<WebElement> getGiftTaxQty = driver.findElements(By.xpath("(//td[@class='amount'])[3]"));
        if (getGiftTaxQty.size() > 0 && getGiftTaxQty.get(0).isDisplayed()) {
            getGiftTax = Double.parseDouble(getDriverWrapper().findElementByUntilWait(getGiftCartXpath).getText().replaceAll("[€,]", ""));
        }

        System.out.println("getProductPrice " + getProductPrice);
        System.out.println("getDeliveryPrice " + getDeliveryPrice);
        System.out.println("getGiftCart " + getGiftCart);
        System.out.println("getTotal " + getTotal);
        total = getProductPrice - getGiftCart + getGiftTax;
        System.out.println(total);
        System.out.println(getTotal);

        assert total == getTotal;

    }

    public void getPriceAndCheckDiscountIsRemoved() {
        double getTotal = Double.parseDouble(getDriverWrapper().findElementByUntilWait(getCartSubtotal).getText().replaceAll("[€,]", ""));
        System.out.println("total is " + total);
        System.out.println("getTotal is " + getTotal);
        assert total < getTotal;

    }

    public void addProductToCartCasio() {
        String host = getTargetTestHost();
        String product = getTargetTestProduct();
        getUrl(host + product);
        List<WebElement> cartQty = driver.findElements(By.xpath("//button[@id='product-addtocart-button']"));
        if (cartQty.size() > 0 && cartQty.get(0).isDisplayed()) {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@id='product-addtocart-button']")))).click();
        } else {
            getUrl(host + "/casio-g-shock-ga-b001-4aer?queryID=2f561607d76aad3198b588ba8e863c6f&objectID=82810&indexName=integration_kk_fi_products");
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@id='product-addtocart-button']")))).click();

        }
        System.out.println(getCurrentUrl());

    }

    public void removeGiftCardIfAlreadyExist() {
        List<WebElement> cartQty = driver.findElements(By.xpath("(//button[@type='submit']//span)[3]"));
        if (cartQty.size() > 0 && cartQty.get(0).isDisplayed()) {
            driver.findElement(By.xpath(cartIconQty)).click();
            driver.findElement(By.xpath("/(//button[@type='submit']//span)[3]")).click();
            logger.info("remove Gift Card");
            driver.navigate().refresh();
            //   this.implicitWait(1);
        } else {
            logger.info("Gift Card is Empty");
        }
    }


    public void errorMassegeMagento() {
        List<WebElement> messageError = driver.findElements(By.xpath("//div[contains(@class,'message-error error')]"));
        if (messageError.size() > 0 && messageError.get(0).isDisplayed()) {
            Assert.fail("Element " + messageError + "  Displayed");        }

    }
}



