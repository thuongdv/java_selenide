package com.demo.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.$;

public class CommonPage {
    protected final SelenideElement lblWelcome = $(By.cssSelector("#leftPanel p.smallText"));

    protected void enterIfHasKey(HashMap<String, String> hashMap, String key, SelenideElement selector) {
        if (hashMap.get(key) == null) return;

        selector.sendKeys(hashMap.get(key));
    }
}
