package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HomePage {

    private final SelenideElement heading = $$("h2").find(text("Путешествие дня"));
    private final SelenideElement paymentButton = $$("button").find(text("Купить"));
    private final SelenideElement creditButton = $$("button").find(text("Купить в кредит"));
    private final SelenideElement form = $("form");
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

    public HomePage() {
        heading.shouldBe(visible);
        paymentButton.shouldBe(visible);
        creditButton.shouldBe(visible);
        successNotification.shouldBe(hidden);
        errorNotification.shouldBe(hidden);
    }

    public PaymentPage paymentButtonClick() {
        paymentButton.click();
        form.shouldBe(visible);
        return new PaymentPage();
    }

    public CreditPage creditButtonClick() {
        creditButton.click();
        form.shouldBe(visible);
        return new CreditPage();
    }
}
