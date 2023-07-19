package com.cuce.driver;

import com.cuce.runs.CucumberTestRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.Getter;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.openqa.selenium.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import static java.lang.System.*;
import static org.testng.Assert.assertEquals;

public class SharedDriver extends EventFiringWebDriver {
    public static final long TIMEOUT_VALUE = 20;//30
    private static WebDriver DRIVER;
    private WebDriver driver;
    String imagesUrl;
    static String imegesCURL;
    static List<String> list = new ArrayList<String>();
    static String imegesCURLTransfer;
    static String errorUrlImage;
    LogEntries logs;
    @Getter
    private DriverWrapper driverWrapper;

    static {
        try {
            DRIVER = DriverFactory.create((getProperty("browser")).toUpperCase());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            DRIVER.quit();
        }
    };

    static String allureReportResultsFolder = "allure-results";

    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    private Logger logger = Logger.getLogger(SharedDriver.class);

    public SharedDriver() {
        super(DRIVER);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    @Before
    public void deleteAllCookies() throws Exception {
        get("about:blank");
        Thread.sleep(3000);
    }


/*
    public static void sendToTelegramPhotos(String apiToken, String chatId, String photo, String caption) {
        String urlString = "https://api.telegram.org/bot%s/sendPhoto?chat_id=%s&photo=%s&caption=%s";
        urlString = String.format(urlString, apiToken, chatId, photo, caption);
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String response = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/



    public static void sendToSlackPhotos(Scenario scenario) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\t\"blocks\": [\r\n\t\t{\r\n\t\t\t\"type\": \"image\",\r\n\t\t\t\"title\": {\r\n\t\t\t\t\"type\": \"plain_text\",\r\n\t\t\t\t\"text\": \""+"Scenario: '" + scenario.getName() + "' is: " + scenario.getStatus()+"\",\r\n\t\t\t\t\"emoji\": true\r\n\t\t\t},\r\n\t\t\t\"image_url\": \""+imegesCURLTransfer+"\",\r\n\t\t\t\"alt_text\": \"marg\"\r\n\t\t}\r\n\t]\r\n}");
        Request request = new Request.Builder()
                .url("https://hooks.slack.com/services/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")
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        Response response = client.newCall(request).execute();
    }


    @After
    public void tearDown(Scenario scenario) throws Exception {
        String scenarioName = scenario.getName();
        String scenarioStatus = scenario.getStatus();
        logger.info("Scenario: '" + scenario.getName() + "' is: " + scenario.getStatus());
        logger.info("\n----------------------------------------------------------------------------------");
        if (scenario.isFailed()) {
            Allure.addAttachment("Console log: ", String.valueOf(DRIVER.manage().logs().get(LogType.BROWSER).getAll()));
            makeScreenshot(scenario);
            makeScreenshotForTelegrambot();
           // uploadErrorImagesToTransfer();
            //sendToSlackPhotos(scenario);
         //   DRIVER.manage().deleteAllCookies();

        }
        //    DRIVER.manage().deleteAllCookies();
            closeExtraTabs();


        }




    private void makeScreenshot(Scenario scenario) {
        try {
            scenario.write("Current URL is " + DRIVER.getCurrentUrl());
            byte[] screenshot = ((TakesScreenshot) DRIVER).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            err.println(somePlatformsDontSupportScreenshots.getMessage());
        }
    }


    public static String uploadErrorImagesToTransfer() throws IOException, JSONException {
        String url = "https://transfer.sh/";
        //  String[] command = ("curl -i -F filedata=@"+System.getProperty("user.dir")+"javanullpointer.png" ,url)
        String shellcmd = "curl -i -F filedata=@" + System.getProperty("user.dir") + "/javanullpointer.png https://transfer.sh/";
        System.out.println(shellcmd);
        Process process = Runtime.getRuntime().exec(shellcmd);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            list.add(line);
            errorUrlImage =line ;
        }


        imegesCURLTransfer = list.get(16);


        return url;
    }


    private void makeScreenshotForTelegrambot() throws IOException {
        TakesScreenshot scrShot = ((TakesScreenshot) DRIVER);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(System.getProperty("user.dir") + "/javanullpointer.png");
        FileUtils.copyFile(SrcFile, DestFile);

    }


    private void closeExtraTabs() {
        List<String> tabs = new ArrayList<>(DRIVER.getWindowHandles());

        if (tabs.size() > 1) {
            for (int i = 1; i < tabs.size(); i++) {
                DRIVER.switchTo().window(tabs.get(i));
                DRIVER.close();
            }
            DRIVER.switchTo().window(tabs.get(0));
            DRIVER.switchTo().defaultContent();
        }
    }


}