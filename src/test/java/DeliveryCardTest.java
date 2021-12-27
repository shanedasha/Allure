import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    String planningDate = DataGenerator.generateDate(4);
    String newPlaningDate = DataGenerator.generateDate(6);


    @Test
    public void shouldChangeDateAndSet() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").sendKeys(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").sendKeys(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] input").sendKeys(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='success-notification']").shouldBe(visible).shouldHave(text("Встреча успешно запланирована на " + planningDate));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(newPlaningDate);
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("div.notification .button").click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldHave(text("Встреча успешно запланирована на " + newPlaningDate));

    }

}
