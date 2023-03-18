package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CreditPage {

    private final SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement cardholderField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement codeField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");
    private final SelenideElement successButton = successNotification.$("button");
    private final SelenideElement errorButton = errorNotification.$("button");
    private final SelenideElement cardNumberFieldError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    private final SelenideElement monthFieldError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    private final SelenideElement yearFieldError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    private final SelenideElement cardholderFieldError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    private final SelenideElement codeFieldError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");

    public CreditPage() {
        heading.shouldBe(visible);
        cardNumberField.shouldBe(visible);
        monthField.shouldBe(visible);
        yearField.shouldBe(visible);
        cardholderField.shouldBe(visible);
        codeField.shouldBe(visible);
        continueButton.shouldBe(visible);
        successNotification.shouldBe(hidden);
        errorNotification.shouldBe(hidden);
    }

    public void inputData(DataHelper.CardInfo card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        cardholderField.setValue(card.getCardholder());
        codeField.setValue(card.getCode());
        continueButton.click();
    }

    public void getSuccessNotification() {
        successNotification.should(visible, Duration.ofSeconds(15));
        successNotification.$("[class=notification__title]").should(text("Успешно"));
        successNotification.$("[class=notification__content]").should(text("Операция одобрена Банком."));
        successButton.click();
        successNotification.should(hidden);
    }

    public void getErrorNotification() {
        errorNotification.should(visible, Duration.ofSeconds(15));
        errorNotification.$("[class=notification__title]").should(text("Ошибка"));
        errorNotification.$("[class=notification__content]").should(text("Ошибка! Банк отказал в проведении операции."));
        errorButton.click();
        errorNotification.should(hidden);
    }
    public void invalidCardNumber() {

        cardNumberFieldError.shouldHave(text("Неверный формат")).shouldBe(visible, Duration.ofSeconds(6));
    }
    public void requiredCard() {
        cardNumberFieldError.shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(6));
    }
    public void invalidMonth() {
        monthFieldError.shouldHave(text("Неверный формат")).shouldBe(visible, Duration.ofSeconds(6));
    }
    public void requiredMonth() {
        monthFieldError.shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(6));
    }
    public void invalidCardExpirationDateMonth() {
        monthFieldError.shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible, Duration.ofSeconds(6));
    }

    public void invalidYear() {
        yearFieldError.shouldHave(text("Неверный формат")).shouldBe(visible, Duration.ofSeconds(6));
    }

    public void cardExpired() {
        yearFieldError.shouldHave(text("Истёк срок действия карты")).shouldBe(visible, Duration.ofSeconds(6));
    }

    public void invalidCardExpirationDateYear() {
        yearFieldError.shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible, Duration.ofSeconds(6));
    }
    public void requiredYear() {
        yearFieldError.shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(6));
    }

    public void requiredHolder() {
        cardholderFieldError.shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(6));
    }

    public void invalidCardholder() {
        cardholderFieldError.shouldHave(text("Неверный формат")).shouldBe(visible, Duration.ofSeconds(10));
    }

    public void invalidCode() {
        codeFieldError.shouldHave(text("Неверный формат")).shouldBe(visible, Duration.ofSeconds(6));
    }
    public void requiredCode() {
        codeFieldError.shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(6));
    }

}
