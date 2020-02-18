package poc;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.*;
import reports.Report;

import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeSuite
    public void beforeSuite() {
        Report.setReport();
    }

    @Parameters({"browser", "headless"})
    @BeforeTest(alwaysRun = true)
    public void openBrowser(String browser, @Optional Boolean headless) {
        Configuration.browser = browser;
        Configuration.headless = headless == null ? Configuration.headless : headless;
        open("https://parabank.parasoft.com/parabank/index.htm");
    }

    @AfterSuite
    public void afterSuite() {
        Report.flushReport();
    }
}
