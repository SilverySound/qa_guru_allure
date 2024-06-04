package ru.anutik;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class SelenideTest extends TestBase {


    @Test
    public void testIssueSearch() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com");
        $(".search-input").click();
        $("#query-builder-test").sendKeys("eroshenkoam/allure-example");
        $("#query-builder-test").pressEnter();
        $(linkText("eroshenkoam/allure-example")).click();
        $("#issues-tab").click();
        $(withText("#80")).should(Condition.exist);

    }

    private static final String REPOSITORY = "eroshenkoam/allure-example";
    private static final int ISSUE = 80 ;


    @Test
    public void testLambdaSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открыть главную страницу", () ->
                open("https://github.com"));
        step("Кликнуть на поиск", () -> {
            $(".search-input").click();
        });
        step("Ввести поисковый запрос", () -> {
            $("#query-builder-test").sendKeys("eroshenkoam/allure-example");
        });
        step("Нажать Enter", () -> {
            $("#query-builder-test").pressEnter();
        });
        step("Кликнуть на результат поиска по ссылке репозитория", () -> {
            $(linkText("eroshenkoam/allure-example")).click();
        });
        step("Открыть таб Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверить наличие Issue с номером" + ISSUE, () -> {
            $(withText("#" + ISSUE)).should(Condition.exist);
            Allure.getLifecycle().addAttachment(
                    "Исходники страницы",
                    "text/html",
                    "html",
                    WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)
            );
        });
    }


    @Test
    public void testSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        StepsTest steps = new StepsTest();

        steps.openMainPage();
        steps.searchInputClick();
        steps.searchInputSendKeys(REPOSITORY);
        steps.searchInputPressEnter();
        steps.searchOutputClick(REPOSITORY);
        steps.openIssueTab();
        steps.shouldSeeIssueWithNumber(ISSUE);

    }


}