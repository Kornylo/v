package com.cuce.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.cuce.driver.SharedDriver.TIMEOUT_VALUE;
import static com.cuce.util.PropertiesUtils.getBrowserVersion;
import static com.cuce.util.PropertiesUtils.getSelenoidUrl;

class DriverFactory {
    static WebDriver create(String browser) throws MalformedURLException {
        WebDriver instance = null;

        switch (browser) {
            case "WINDOWS":
                instance = initWebDriverWindows();
                break;
            case "CHROME":
                instance = initWebDriver(new ChromeDriver());
                break;
            case "CHROMER":
                instance = initRemoteDriver("chrome");
                break;
            case "OPERA":
                instance = initRemoteDriverOpera();
                break;
            case "FIREFOX":
                instance = initWebDriver(new FirefoxDriver());
                break;
            case "FIREFOXR":
                instance = initRemoteDriverFirefox("firefox");
                break;
            case "MOBILE":
                instance = mobileDriver();
                break;
            default:
                System.err.println("No browser specified");
        }

        return instance;
    }

    private static WebDriver initWebDriver(WebDriver driver) {
        WebDriverManager.chromedriver().setup();

        setImplicitlyWait(driver, TIMEOUT_VALUE);
        driver.manage().window().maximize();
        return driver;
    }

    static void setImplicitlyWait(WebDriver driver, long value) {
        driver.manage().timeouts().implicitlyWait(value, TimeUnit.SECONDS);

    }






    private static RemoteWebDriver mobileDriver() {
        ChromeOptions options = new ChromeOptions ();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setVersion(getBrowserVersion());
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("browser_version", "latest");
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("name", System.getProperty("customName"));
        options.addArguments("--enable-extensions");
        options.addExtensions (new File(System.getProperty("user.dir")+"/mobile.crx"));
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    URI.create(getSelenoidUrl()).toURL(),
                    capabilities
            );
            setImplicitlyWait(driver, TIMEOUT_VALUE);
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }




    private static RemoteWebDriver initRemoteDriverFirefox(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setBrowserName(browser);
        capabilities.setVersion(getBrowserVersion());
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("name", System.getProperty("customName"));
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    URI.create(getSelenoidUrl()).toURL(),
                    capabilities
            );
            setImplicitlyWait(driver, TIMEOUT_VALUE);
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }



    private static RemoteWebDriver initRemoteDriverOpera() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "opera");
        capabilities.setCapability("browserVersion", "75.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("name", System.getProperty("customName"));
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    URI.create(getSelenoidUrl()).toURL(),
                    capabilities
            );
            setImplicitlyWait(driver, TIMEOUT_VALUE);
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }


    private static RemoteWebDriver initRemoteDriver(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("browser_version", "latest");
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("name", System.getProperty("customName"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-popup-blocking");
        options.setHeadless(false);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    URI.create("http://localhost:4444/wd/hub").toURL(),
                    capabilities
            );
            setImplicitlyWait(driver, TIMEOUT_VALUE);
            driver.manage().window().setSize(new Dimension(1360, 1020));
            driver.manage().deleteAllCookies();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;

    }
    private static WebDriver initWebDriverWindows() {
        WebDriverManager.chromedriver().setup();
        //Setting system properties of ChromeDriver
        System.setProperty("webdriver.chrome.driver", "D:///chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }

}

