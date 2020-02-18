package poc;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import reports.Report;

public class RegisterTest extends TestBase {
    @Test(description = "TC1: register successfully")
    public void tcRegisterSuccessfully() {
        Report.info("1. Go to homepage");
        HomePage homePage = new HomePage();
        homePage.goToRegisterPage();

        Report.info("2. Register");
        RegisterPage registerPage = new RegisterPage();
        registerPage.register();
    }
}
