package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    SelenideElement usernameTxt = $(By.name("username"));
    SelenideElement passwordTxt = $(By.name("password"));
    SelenideElement loginBtn = $("input[value='Log In']");
    SelenideElement registerLnk = $("a[href^='register.htm']");

    public void login(String username, String password) {
        usernameTxt.sendKeys(username);
        passwordTxt.sendKeys(password);
        loginBtn.click();
    }

    public void goToRegisterPage() {
        registerLnk.click();
    }
}
