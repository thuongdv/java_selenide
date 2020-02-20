package poc;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import reports.Report;

import java.util.HashMap;

public class RegisterTest extends TestBase {
    @Test(description = "TC1: register successfully", priority = 0)
    public void tcRegisterSuccessfully() {
        Report.info("1. Go to register page");
        HomePage homePage = new HomePage();
        homePage.goToRegisterPage();

        Report.info("2. Register");
        RegisterPage registerPage = new RegisterPage();
        HashMap<String, String> accInfo = registerPage.register();
        registerPage.verifyRegisterSuccessfully(accInfo);
    }

    @Test(description = "TC2: cannot register without required fields", priority = 1)
    public void tcCannotRegisterWithoutRequiredFields() {
        Report.info("1. Go to register page");
        HomePage homePage = new HomePage();
        homePage.goToRegisterPage();

        Report.info("2. Register");
        RegisterPage registerPage = new RegisterPage();
        registerPage.register(new HashMap<>());
    }
}
