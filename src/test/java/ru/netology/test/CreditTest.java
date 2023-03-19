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

    @Test
    @DisplayName("Поле Номер карты 15 цифр")
    public void cardWith15Numbers() {
        var CardInfo = DataHelper.getCardWithLessChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardNumber();
    }

    @Test
    @DisplayName("Поле Номер карты 17 цифр")
    public void cardWith17Numbers() {
        var CardInfo = DataHelper.getCardWithMoreChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    @Test
    @DisplayName("Поле Номер карты буквы")
    public void cardWithLetters() {
        var CardInfo = DataHelper.getCardWithLetters();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardNumber();
    }

    @Test
    @DisplayName("Поле Номер карты спецсимволами")
    public void cardWithSymbols() {
        var CardInfo = DataHelper.getCardWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardNumber();
    }

    @Test
    @DisplayName("Поле Номер карты пустое")
    public void cardEmpty() {
        var CardInfo = DataHelper.getCardEmpty();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredCard();
    }

    @Test
    @DisplayName("Поле Месяц число 13-99")
    public void invalidMonth() {
        var CardInfo = DataHelper.getInvalidMonthBetween13And99();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardExpirationDateMonth();
    }

    @Test
    @DisplayName("Поле Месяц 1 цифра")
    public void monthWith1Number() {
        var CardInfo = DataHelper.getMonthWithLessChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidMonth();
    }

    @Test
    @DisplayName("Поле Месяц 3 цифры")
    public void monthWith3Number() {
        var CardInfo = DataHelper.getMonthWithMoreChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    @Test
    @DisplayName("Поле Месяц буквами")
    public void monthWithLetters() {
        var CardInfo = DataHelper.getMonthWithLetters();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidMonth();
    }

    @Test
    @DisplayName("Поле Месяц спецсимволами")
    public void monthWithSymbols() {
        var CardInfo = DataHelper.getMonthWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidMonth();
    }


    @Test
    @DisplayName("Поле Месяц пустое")
    public void emptyMonth() {
        var CardInfo = DataHelper.getMonthEmpty();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredMonth();
    }

    @Test
    @DisplayName("Поле Год Истек срок действия")
    public void cardExpiredByYear() {
        var CardInfo = DataHelper.getCardExpiredByYear();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.cardExpired();
    }

    @Test
    @DisplayName("Поле Год число 29-99")
    public void invalidExpirationDate() {
        var CardInfo = DataHelper.getInvalidExpirationDate();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardExpirationDateYear();
    }

    @Test
    @DisplayName("Поле Год 3 цифры")
    public void yearWithMoreChars() {
        var CardInfo = DataHelper.getYearWithMoreChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    @Test
    @DisplayName("Поле Год 1 цифра")
    public void yearWithLessChars() {
        var CardInfo = DataHelper.getYearWithLessChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidYear();
    }

    @Test
    @DisplayName("Поле Год спецсимволами")
    public void yearWithSymbols() {
        var CardInfo = DataHelper.getYearWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidYear();
    }

    @Test
    @DisplayName("Поле Год буквами")
    public void yearWithLetters() {
        var CardInfo = DataHelper.getYearWithLetters();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidYear();
    }

    @Test
    @DisplayName("Поле Год пустое")
    public void yearEmpty() {
        var CardInfo = DataHelper.getYearEmpty();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredYear();
    }


    @Test
    @DisplayName("Поле Владелец кириллицей")
    public void cardholderRus() {
        var CardInfo = DataHelper.getCardholderRussian();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardholder();
    }

    @Test
    @DisplayName("Поле Владелец цифрами")
    public void cardholderWithNumbers() {
        var CardInfo = DataHelper.getCardholderWithNumbers();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardholder();
    }

    @Test
    @DisplayName("Поле Владелец спецсимволами")
    public void cardholderWithSymbols() {
        var CardInfo = DataHelper.getCardholderWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCardholder();
    }

    @Test
    @DisplayName("Поле Владелец пустое")
    public void cardholderEmpty() {
        var CardInfo = DataHelper.getEmptyCardholder();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredHolder();
    }

    @Test
    @DisplayName("Поле CCV/CVC 2 цифрами")
    public void codeWith2Numbers() {
        var CardInfo = DataHelper.getCodeWithLessChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCode();
    }

    @Test
    @DisplayName("Поле CCV/CVC 4 цифрами")
    public void codeWith4Numbers() {
        var CardInfo = DataHelper.getCodeWithMoreChars();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    @Test
    @DisplayName("Поле CCV/CVC буквами")
    public void codeWithLetters() {
        var CardInfo = DataHelper.getCodeWithLetters();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCode();
    }

    @Test
    @DisplayName("Поле CCV/CVC спецсимволами")
    public void codeWithSymbols() {
        var CardInfo = DataHelper.getCodeWithSymbols();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.invalidCode();
    }

    @Test
    @DisplayName("Поле CCV/CVC пустое")
    public void codeEmpty() {
        var CardInfo = DataHelper.getEmptyCode();
        CreditPage creditPage = homePage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.requiredCode();
    }

}
