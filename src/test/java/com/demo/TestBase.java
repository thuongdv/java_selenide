package com.demo;

import com.codeborne.selenide.Configuration;
import com.demo.reports.Report;
import org.testng.annotations.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class TestBase {
    @BeforeSuite
    public void beforeSuite() {
        String reportFolder = String.join(
            File.separator, "ExtentReport",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MMM_dd-HH_mm_ss"))
        );
        Report.setReport(reportFolder);
        Configuration.browserSize = "1440x900";
        Configuration.reportsFolder = reportFolder;
    }

    @Parameters({"browser", "headless"})
    @BeforeTest(alwaysRun = true)
    public void config(String browser, @Optional Boolean headless) {
        Configuration.browser = browser;
        Configuration.headless = headless == null ? Configuration.headless : headless;
    }

    @BeforeMethod(alwaysRun = true)
    public void openBrowser() {
        open("https://parabank.parasoft.com/parabank/index.htm");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @AfterSuite
    public void afterSuite() {
        Report.flushReport();
    }
}
