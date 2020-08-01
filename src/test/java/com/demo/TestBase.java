package com.demo;

import com.codeborne.selenide.Configuration;
import com.demo.reports.Report;
import com.demo.utils.Constants;
import org.testng.annotations.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class TestBase {
    @BeforeSuite
    public void beforeSuite() {
        String reportFolder = String.join(
            File.separator, Constants.REPORT_FOLDER_ROOT,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.REPORT_FOLDER_FORMAT))
        );
        Report.setReport(reportFolder);
        Configuration.browserSize = Constants.BROWSER_SIZE;
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
        open(Constants.URL);
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
