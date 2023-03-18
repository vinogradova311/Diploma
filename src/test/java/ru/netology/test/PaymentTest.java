package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.HomePage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    HomePage homePage;
    PaymentPage paymentPage;

    @BeforeEach
    void openPage() {
        DbHelper.cleanDatabase();
        homePage = open("http://localhost:8080", HomePage.class);
        paymentPage = homePage.paymentButtonClick();
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
    @DisplayName("Покупка тура через вкладку Купить на главной странице сервиса с approved картой")
    public void approvedCard() {
        homePage.paymentButtonClick();
        paymentPage.inputData(DataHelper.getApprovedCard());
        paymentPage.getSuccessNotification();

        assertEquals("APPROVED", DbHelper.getPaymentStatus());
        assertEquals(1, DbHelper.getOrderCount());
        assertEquals(1, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Покупка тура через вкладку Купить на главной странице сервиса с declined картой")
    public void declinedCard() {
        homePage.paymentButtonClick();
        paymentPage.inputData(DataHelper.getDeclinedCard());
        paymentPage.getErrorNotification();


        assertEquals("DECLINED", DbHelper.getPaymentStatus());
        assertEquals(0, DbHelper.getOrderCount());
        assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Покупка тура через вкладку Купить на главной странице сервиса с валидной, но не одобренной картой")
    public void validCard() {
        homePage.paymentButtonClick();
        paymentPage.inputData(DataHelper.getValidData());
        paymentPage.getErrorNotification();


        assertEquals(0, DbHelper.getOrderCount());
        assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("Отправка пустых значений")
    public void allFieldsAreEmpty() {
        HomePage homePage = new HomePage();
        var CardInfo = DataHelper.getAllEmpty();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
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
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCardNumber();
    }


    @Test
    @DisplayName("Поле Номер карты 17 цифр")
    public void cardWith17Numbers() {
        var CardInfo = DataHelper.getCardWithMoreChars();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }


    @Test
    @DisplayName("Поле Номер карты буквы")
    public void cardWithLetters() {
        var CardInfo = DataHelper.getCardWithLetters();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCardNumber();
    }


    @Test
    @DisplayName("Поле Номер карты спецсимволы")
    public void cardWithSymbols() {
        var CardInfo = DataHelper.getCardWithSymbols();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCardNumber();
    }

    @Test
    @DisplayName("Поле Номер карты пустое")
    public void cardEmpty() {
        var CardInfo = DataHelper.getCardEmpty();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.requiredCard();
    }

    @Test
    @DisplayName("Поле Месяц 13-99")
    public void invalidMonth() {
        var CardInfo = DataHelper.getInvalidMonthBetween13And99();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCardExpirationDateMonth();
    }

    @Test
    @DisplayName("Поле Месяц 1 цифра")
    public void monthWith1Number() {
        var CardInfo = DataHelper.getMonthWithLessChars();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidMonth();
    }

    @Test
    @DisplayName("Поле Месяц 3 цифры")
    public void monthWith3Number() {
        var CardInfo = DataHelper.getMonthWithMoreChars();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }

    @Test
    @DisplayName("Поле Месяц буквы")
    public void monthWithLetters() {
        var CardInfo = DataHelper.getMonthWithLetters();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidMonth();
    }

    @Test
    @DisplayName("Поле Месяц спецсимволы")
    public void monthWithSymbols() {
        var CardInfo = DataHelper.getMonthWithSymbols();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidMonth();
    }


    @Test
    @DisplayName("Поле Месяц пустое")
    public void emptyMonth() {
        var CardInfo = DataHelper.getMonthEmpty();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.requiredMonth();
    }

    @Test
    @DisplayName("Поле Год до текушего года")
    public void cardExpiredByYear() {
        var CardInfo = DataHelper.getCardExpiredByYear();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.cardExpired();
    }

    @Test
    @DisplayName("Поле Год 29-99")
    public void invalidExpirationDate() {
        var CardInfo = DataHelper.getInvalidExpirationDate();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCardExpirationDateYear();
    }

    @Test
    @DisplayName("Поле Год 3 цифры")
    public void yearWithMoreChars() {
        var CardInfo = DataHelper.getYearWithMoreChars();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }

    @Test
    @DisplayName("Поле Год 1 цифра")
    public void yearWithLessChars() {
        var CardInfo = DataHelper.getYearWithLessChars();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidYear();
    }

    @Test
    @DisplayName("Поле Год спецсимволы")
    public void yearWithSymbols() {
        var CardInfo = DataHelper.getYearWithSymbols();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidYear();
    }

    @Test
    @DisplayName("Поле Год буквы")
    public void yearWithLetters() {
        var CardInfo = DataHelper.getYearWithLetters();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidYear();
    }

    @Test
    @DisplayName("Поле Год пустое")
    public void yearEmpty() {
        var CardInfo = DataHelper.getYearEmpty();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.requiredYear();
    }


    @Test
    @DisplayName("Поле Владелец кириллицей")
    public void cardholderRus() {
        var CardInfo = DataHelper.getCardholderRussian();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCardholder();
    }

    @Test
    @DisplayName("Поле Владелец цифрами")
    public void cardholderWithNumbers() {
        var CardInfo = DataHelper.getCardholderWithNumbers();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCardholder();
    }

    @Test
    @DisplayName("Поле Владелец спецсимволами")
    public void cardholderWithSymbols() {
        var CardInfo = DataHelper.getCardholderWithSymbols();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCardholder();
    }

    @Test
    @DisplayName("Поле Владелец пустое")
    public void cardholderEmpty() {
        var CardInfo = DataHelper.getEmptyCardholder();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.requiredHolder();
    }

    @Test
    @DisplayName("Поле CCV/CVC 2 цифры")
    public void codeWith2Numbers() {
        var CardInfo = DataHelper.getCodeWithLessChars();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCode();
    }

    @Test
    @DisplayName("Поле CCV/CVC 4 цифры")
    public void codeWith4Numbers() {
        var CardInfo = DataHelper.getCodeWithMoreChars();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }

    @Test
    @DisplayName("Поле CCV/CVC буквами")
    public void codeWithLetters() {
        var CardInfo = DataHelper.getCodeWithLetters();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCode();
    }

    @Test
    @DisplayName("Поле CCV/CVC спецсимволами")
    public void codeWithSymbols() {
        var CardInfo = DataHelper.getCodeWithSymbols();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.invalidCode();
    }


    @Test
    @DisplayName("Поле CCV/CVC пустое")
    public void codeEmpty() {

        var CardInfo = DataHelper.getEmptyCode();
        PaymentPage paymentPage = homePage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.requiredCode();
    }

}
