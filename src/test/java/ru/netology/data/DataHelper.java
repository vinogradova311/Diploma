package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static final Faker fakerEng = new Faker(new Locale("en"));
    private static final Faker fakerRu = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        String number;
        String month;
        String year;
        String cardholder;
        String code;
    }

    public static String getMonth(int month) {
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getCardholder() {
        return fakerEng.name().fullName().toUpperCase();
    }

    public static String getCode() {
        return fakerEng.numerify("###");
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442", getMonth(1), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getValidData() {
        return new CardInfo(fakerEng.numerify("#### #### #### ####"), getMonth(1), String.valueOf(fakerEng.number().numberBetween(24, 28)), fakerEng.name().fullName().toUpperCase(), fakerEng.numerify("###"));
    }

    public static CardInfo getCardEmpty() {
        return new CardInfo("", getMonth(1), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getCardWithLessChars() {
        return new CardInfo(fakerEng.numerify("#### #### #### ###"), getMonth(1), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getCardWithMoreChars() {
        return new CardInfo(fakerEng.numerify("4444 4444 4444 4441 #"), getMonth(1), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getCardWithLetters() {
        return new CardInfo(fakerEng.letterify("???? ???? ???? ????"), getMonth(1), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getCardWithSymbols() {
        return new CardInfo("&^!@ #$() *&^% !@#$", getMonth(1), getYear(1), getCardholder(), getCode());
    }


    public static CardInfo getMonthEmpty() {
        return new CardInfo("4444 4444 4444 4441", "", getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getMonthWithLessChars() {
        return new CardInfo("4444 4444 4444 4441", fakerEng.numerify("#"), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getMonthWithMoreChars() {
        return new CardInfo("4444 4444 4444 4441", fakerEng.numerify("04#"), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getInvalidMonthBetween13And99() {
        return new CardInfo("4444 4444 4444 4441", String.valueOf(fakerEng.number().numberBetween(13, 99)), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getMonthWithLetters() {
        return new CardInfo("4444 4444 4444 4441", fakerEng.letterify("??"), getYear(1), getCardholder(), getCode());
    }

    public static CardInfo getMonthWithSymbols() {
        return new CardInfo("4444 4444 4444 4441", "&^", getYear(1), getCardholder(), getCode());
    }


    public static CardInfo getYearEmpty() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), "", getCardholder(), getCode());
    }

    public static CardInfo getYearWithLessChars() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), fakerEng.numerify("#"), getCardholder(), getCode());
    }

    public static CardInfo getYearWithMoreChars() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), String.valueOf(fakerEng.number().numberBetween(230, 289)), getCardholder(), getCode());
    }

    public static CardInfo getCardExpiredByYear() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), String.valueOf(fakerEng.number().numberBetween(10, 23)), getCardholder(), getCode());
    }

    public static CardInfo getInvalidExpirationDate() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), String.valueOf(fakerEng.number().numberBetween(29, 99)), getCardholder(), getCode());
    }

    public static CardInfo getYearWithLetters() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), fakerEng.letterify("??"), getCardholder(), getCode());
    }

    public static CardInfo getYearWithSymbols() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), "&^", getCardholder(), getCode());
    }

    public static CardInfo getEmptyCardholder() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), "", getCode());
    }

    public static CardInfo getCardholderRussian() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), fakerRu.name().fullName().toUpperCase(), getCode());
    }

    public static CardInfo getCardholderWithNumbers() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), fakerEng.numerify("### ###"), getCode());
    }

    public static CardInfo getCardholderWithSymbols() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), "#$* !@", getCode());
    }

    public static CardInfo getCodeWithLessChars() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getCardholder(), fakerEng.numerify("##"));
    }

    public static CardInfo getCodeWithMoreChars() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getCardholder(), fakerEng.numerify("####"));
    }

    public static CardInfo getCodeWithLetters() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getCardholder(), fakerEng.letterify("???"));
    }

    public static CardInfo getEmptyCode() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getCardholder(), "");
    }

    public static CardInfo getCodeWithSymbols() {
        return new CardInfo("4444 4444 4444 4441", getMonth(1), getYear(1), getCardholder(), "$#%");
    }

    public static CardInfo getAllEmpty() {
        return new CardInfo("", "", "", "", "");
    }


}

