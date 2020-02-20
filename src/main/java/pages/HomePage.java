package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePage extends CommonPage {
    private final SelenideElement usernameTxt = $(By.name("username"));
    private final SelenideElement passwordTxt = $(By.name("password"));
    private final SelenideElement loginBtn = $("input[value='Log In']");
    private final SelenideElement registerLnk = $("a[href^='register.htm']");

    public void login(String username, String password) {
        usernameTxt.sendKeys(username);
        passwordTxt.sendKeys(password);
        loginBtn.click();
    }

    public void goToRegisterPage() {
        registerLnk.click();
    }
}
