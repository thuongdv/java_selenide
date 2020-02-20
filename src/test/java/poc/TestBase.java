package poc;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.*;
import reports.Report;

import static com.codeborne.selenide.Selenide.*;

public class TestBase {
    @BeforeSuite
    public void beforeSuite() {
        Report.setReport();
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
