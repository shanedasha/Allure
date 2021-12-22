import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    String planningDate = generateDate(4);
    String newPlaningDate = generateNewDate(6);
    private static Faker faker;

    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String phoneNumber = faker.phoneNumber().phoneNumber();
    String city = faker.address().city();


    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String generateNewDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeAll
    public  static  void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    public void shouldChangeDateAndSet() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").sendKeys(city);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").sendKeys(lastName);
        $("[data-test-id='name'] input").sendKeys(Keys.ESCAPE);
        $("[data-test-id='name'] input").sendKeys(firstName);
        $("[data-test-id='phone'] input").sendKeys(phoneNumber);
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(newPlaningDate);
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("div.notification .button").click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно забронирована на " + newPlaningDate));

    }

}
