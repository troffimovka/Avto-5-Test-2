package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;



class AuthorizationTest {
    private static Faker faker = new Faker(new Locale("en"));

    @BeforeEach
    void shouldOpenWeb() {
        open("http://localhost:9999");

    }

    @Test
    void shouldAcceptValidActiveUser() {
        UserInfo userInfo = DataGenerator.getRegisteredUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(userInfo.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(userInfo.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldRejectInvalidLoginActiveUser() {
        UserInfo userInfo = DataGenerator.getRegisteredUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(DataGenerator.getNewLogin());
        $("[data-test-id=password] [class = input__control]").setValue(userInfo.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldRejectInvalidPasswordActiveUser() {
        UserInfo userInfo = DataGenerator.getRegisteredUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(userInfo.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(DataGenerator.getNewPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldRejectValidBlockedUser() {
        UserInfo userInfo = DataGenerator.getRegisteredUser("blocked");
        $("[data-test-id=login] [class = input__control]").setValue(userInfo.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(userInfo.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

}
