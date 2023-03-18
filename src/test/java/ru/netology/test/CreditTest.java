package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.HomePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {
    HomePage homePage;
    CreditPage creditPage;

    @BeforeEach
    void openPage() {
        DbHelper.cleanDatabase();
        homePage = open("http://localhost:8080", HomePage.class);
        creditPage = homePage.creditButtonClick();
    }

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @DisplayName("Покупка тура через вкладку Купить в кредит на главной странице сервиса с approved картой")
    public void approvedCard() {
        homePage.creditButtonClick();
        creditPage.inputData(DataHelper.getApprovedCard());
        creditPage.getSuccessNotification();

        assertEquals("APPROVED", DbHelper.getCreditStatus());
        assertEquals(1, DbHelper.getOrderCount());
        assertEquals(1, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("Покупка тура через вкладку Купить в кредит на главной странице сервиса с declined картой")
    public void declinedCard() {
        homePage.creditButtonClick();
        creditPage.inputData(DataHelper.getDeclinedCard());
        creditPage.getErrorNotification();


        assertEquals("DECLINED", DbHelper.getCreditStatus());
        assertEquals(0, DbHelper.getOrderCount());
        assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("Покупка тура через вкладку Купить в кредит на главной странице сервиса с валидной, но не одобренной картой")
    public void validCard() {
        homePage.creditButtonClick();
        creditPage.inputData(DataHelper.getValidData());
        creditPage.getErrorNotification();

        assertEquals(0, DbHelper.getOrderCount());
        assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("Отправка пустых значений")
    public void allFieldsAreEmpty() {

        CreditPage creditPage = homePage.creditButtonClick();
        var CardInfo = DataHelper.getAllEmpty();
        creditPage.inputData(CardInfo);
        $(byText("Номер карты")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("Месяц")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("Год")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("Владелец")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("CVC/CVV")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
    }

    //"Поле Номер карты 15 цифр"
    @Test
    public void cardWith15Numbers() {
        var CardInfo = DataHelper.getCardWithLessChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardNumber();
    }

    //"Поле Номер карты 17 цифр"
    @Test
    public void cardWith17Numbers() {
        var CardInfo = DataHelper.getCardWithMoreChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    //"Поле Номер карты буквы"
    @Test
    public void cardWithLetters() {
        var CardInfo = DataHelper.getCardWithLetters();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardNumber();
    }

    //"Поле Номер карты символы"
    @Test
    public void cardWithSymbols() {
        var CardInfo = DataHelper.getCardWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardNumber();
    }

    //"Поле Номер карты пустое"
    @Test
    public void cardEmpty() {
        var CardInfo = DataHelper.getCardEmpty();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredCard();
    }

    //"Поле Месяц число 13-99
    @Test
    public void invalidMonth() {
        var CardInfo = DataHelper.getInvalidMonthBetween13And99();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardExpirationDateMonth();
    }

    //"Поле Месяц 1 число"
    @Test
    public void monthWith1Number() {
        var CardInfo = DataHelper.getMonthWithLessChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidMonth();
    }

    //"Поле Месяц 3 число"
    @Test
    public void monthWith3Number() {
        var CardInfo = DataHelper.getMonthWithMoreChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    // "Поле Месяц буквы"
    @Test
    public void monthWithLetters() {
        var CardInfo = DataHelper.getMonthWithLetters();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidMonth();
    }

    // "Поле Месяц символы"
    @Test
    public void monthWithSymbols() {
        var CardInfo = DataHelper.getMonthWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidMonth();
    }


    //"Поле Месяц пустое"
    @Test
    public void emptyMonth() {
        var CardInfo = DataHelper.getMonthEmpty();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredMonth();
    }

    // "Поле Год Истек срок действия"
    @Test
    public void cardExpiredByYear() {
        var CardInfo = DataHelper.getCardExpiredByYear();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.cardExpired();
    }

    //"Поле Год 29-99"
    @Test
    public void invalidExpirationDate() {
        var CardInfo = DataHelper.getInvalidExpirationDate();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardExpirationDateYear();
    }

    //"Поле Год 3 числа"
    @Test
    public void yearWithMoreChars() {
        var CardInfo = DataHelper.getYearWithMoreChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    //"Поле Год 1 число"
    @Test
    public void yearWithLessChars() {
        var CardInfo = DataHelper.getYearWithLessChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidYear();
    }

    // "Поле Год символы"
    @Test
    public void yearWithSymbols() {
        var CardInfo = DataHelper.getYearWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidYear();
    }

    // "Поле Год буквы"
    @Test
    public void yearWithLetters() {
        var CardInfo = DataHelper.getYearWithLetters();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidYear();
    }

    // "Поле Год пустое"
    @Test
    public void yearEmpty() {
        var CardInfo = DataHelper.getYearEmpty();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredYear();
    }


    //"Поле Владелец символами кириллицы"
    @Test
    public void cardholderRus() {
        var CardInfo = DataHelper.getCardholderRussian();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardholder();
    }

    //"Поле Владелец цифры"
    @Test
    public void cardholderWithNumbers() {
        var CardInfo = DataHelper.getCardholderWithNumbers();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardholder();
    }

    //"Поле Владелец символы"
    @Test
    public void cardholderWithSymbols() {
        var CardInfo = DataHelper.getCardholderWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardholder();
    }

    //"Поле Владелец пустое"
    @Test
    public void cardholderEmpty() {
        var CardInfo = DataHelper.getEmptyCardholder();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredHolder();
    }


    @Test
    public void codeWith2Numbers() {
        var CardInfo = DataHelper.getCodeWithLessChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCode();
    }

    @Test
    public void codeWith4Numbers() {
        var CardInfo = DataHelper.getCodeWithMoreChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    @Test
    public void codeWithLetters() {
        var CardInfo = DataHelper.getCodeWithLetters();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCode();
    }

    @Test
    public void codeWithSymbols() {
        var CardInfo = DataHelper.getCodeWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCode();
    }


    @Test
    public void codeEmpty() {

        var CardInfo = DataHelper.getEmptyCode();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredCode();
    }

}
