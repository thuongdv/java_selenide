package com.demo;

import com.codeborne.selenide.Configuration;
import com.demo.reports.Report;
import com.demo.utils.Constants;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static com.codeborne.selenide.Selenide.*;

public class TestBase {

    @Parameters({"browser", "headless"})
    @BeforeTest(alwaysRun = true)
    public void config(String browser, @Optional Boolean headless) {
        Configuration.browser = browser;
        Configuration.headless = headless == null ? Configuration.headless : headless;
    }

    @BeforeMethod(alwaysRun = true)
    public void openBrowser() {
        open(Constants.URL);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        clearBrowserCookies();
        clearBrowserLocalStorage();

        // Flush the content to the consumer every test so that we know the current test run. And refrain from the
        // unexpected exception that causes the report is not generated
        Report.getInstance().flushReport();
    }

    @AfterSuite
    public void afterSuite() {
        Report.getInstance().flushReport();
    }
}
