package com.demo;

import com.demo.pages.HomePage;
import com.demo.pages.RegisterPage;
import com.demo.reports.Report;
import org.testng.annotations.Test;

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

        Report.info("VP: Register successfully");
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

        Report.info("VP: Register not successfully");
    }
}
