package com.demo.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.demo.reports.Report;
import com.demo.utils.DateTimeUtil;
import org.openqa.selenium.By;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage extends CommonPage {
    private final SelenideElement firstNameTxt = $(By.cssSelector("input[id='customer.firstName']"));
    private final SelenideElement lastNameTxt = $(By.cssSelector("input[id='customer.lastName']"));
    private final SelenideElement addressTxt = $(By.cssSelector("input[id='customer.address.street']"));
    private final SelenideElement cityTxt = $(By.cssSelector("input[id='customer.address.city']"));
    private final SelenideElement stateTxt = $(By.cssSelector("input[id='customer.address.state']"));
    private final SelenideElement zipCodeTxt = $(By.cssSelector("input[id='customer.address.zipCode']"));
    private final SelenideElement phoneTxt = $(By.cssSelector("input[id='customer.phoneNumber']"));
    private final SelenideElement ssnTxt = $(By.cssSelector("input[id='customer.ssn']"));
    private final SelenideElement usernameTxt = $(By.cssSelector("input[id='customer.username']"));
    private final SelenideElement passwordTxt = $(By.cssSelector("input[id='customer.password']"));
    private final SelenideElement repeatPasswordTxt = $(By.cssSelector("input[id='repeatedPassword']"));
    private final SelenideElement registerBtn = $(By.cssSelector("input[type='submit'][value='Register']"));

    public HashMap<String, String> register() {
        HashMap<String, String> accInfo = defaultAccountInfo();
        this.register(accInfo);

        return accInfo;
    }

    public void register(HashMap<String, String> accInfo) {
        Report.debug("Register with the following info: " + accInfo.toString());
        this.enterIfHasKey(accInfo, "firstName", firstNameTxt);
        this.enterIfHasKey(accInfo, "lastName", lastNameTxt);
        this.enterIfHasKey(accInfo, "address", addressTxt);
        this.enterIfHasKey(accInfo, "city", cityTxt);
        this.enterIfHasKey(accInfo, "state", stateTxt);
        this.enterIfHasKey(accInfo, "zipCode", zipCodeTxt);
        this.enterIfHasKey(accInfo, "phone", phoneTxt);
        this.enterIfHasKey(accInfo, "ssn", ssnTxt);
        this.enterIfHasKey(accInfo, "username", usernameTxt);
        this.enterIfHasKey(accInfo, "password", passwordTxt);
        this.enterIfHasKey(accInfo, "repeatPassword", repeatPasswordTxt);
        registerBtn.click();
    }

    public static HashMap<String, String> defaultAccountInfo() {
        HashMap<String, String> accInfo = new HashMap<>();
        accInfo.put("firstName", "ftest");
        accInfo.put("lastName", "ltest");
        accInfo.put("address", "address");
        accInfo.put("city", "city");
        accInfo.put("state", "state");
        accInfo.put("zipCode", "55555");
        accInfo.put("phone", "0123456789");
        accInfo.put("ssn", "123456789");
        accInfo.put("username", DateTimeUtil.current("yyyy-MM-dd_HH-mm-ss"));
        accInfo.put("password", "123456");
        accInfo.put("repeatPassword", "123456");

        return accInfo;
    }

    public void verifyRegisterSuccessfully(HashMap<String, String> accInfo) {
        String expected = String.format("Welcome %s %s===============", accInfo.get("firstName"), accInfo.get("lastName"));

        Report.debug("Verify welcome message displays: " + expected);
        this.lblWelcome.shouldHave(Condition.text(expected));
    }
}
