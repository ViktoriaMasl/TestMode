import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.DataGenerator;
import ru.netology.RegistrationDto;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    void openLocalhost() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginActiveUser() {
        RegistrationDto dto = DataGenerator.Registration.generateActiveUser();
        DataGenerator.UserGenerator.setUpAll(dto);
        $("[name=login]").setValue(dto.getLogin());
        $("[name=password]").setValue(dto.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginBlockedUser() {
        RegistrationDto dto = DataGenerator.Registration.generateBlockedUser();
        DataGenerator.UserGenerator.setUpAll(dto);
        $("[name=login]").setValue(dto.getLogin());
        $("[name=password]").setValue(dto.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginActiveUserWithInvalidLogin() {
        RegistrationDto dto = DataGenerator.Registration.generateActiveUser();
        DataGenerator.UserGenerator.setUpAll(dto);
        $("[name=login]").setValue("&fjidin( 088hbjd^");
        $("[name=password]").setValue(dto.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginActiveUserWithInvalidPassword() {
        RegistrationDto dto = DataGenerator.Registration.generateActiveUser();
        DataGenerator.UserGenerator.setUpAll(dto);
        $("[name=login]").setValue(dto.getLogin());
        $("[name=password]").setValue("jhhiwi78");
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

}
