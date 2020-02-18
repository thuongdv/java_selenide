package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
    SelenideElement firstNameTxt = $(By.name("customer.firstName"));
    SelenideElement lastNameTxt = $(By.id("customer.lastName"));

    public void register() {
        firstNameTxt.sendKeys("Test");
        lastNameTxt.sendKeys("Test");
    }
}
