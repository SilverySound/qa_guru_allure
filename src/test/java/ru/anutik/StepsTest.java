package ru.anutik;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.attachment;
import static org.openqa.selenium.By.linkText;

public class StepsTest {

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
    @Step("Открыть главную страницу")
    public void openMainPage() {
        open("https://github.com");

    }

    @Step("Кликнуть на поиск")
    public void searchInputClick() {
        $(".search-input").click();
    }

    @Step("Ввести поисковый запрос {repo}")
    public void searchInputSendKeys(String repo) {
        $("#query-builder-test").sendKeys(repo);
    }

    @Step("Нажать Enter")
    public void searchInputPressEnter() {
        $("#query-builder-test").pressEnter();
    }

    @Step("Кликнуть на результат поиска по ссылке репозитория {repo}")
    public void searchOutputClick(String repo) {
        $(linkText(repo)).click();
    }

    @Step("Кликнуть на кнопку Issues")
    public void openIssueTab() {
        $("#issues-tab").click();
        takeScreenshot();
    }

    @Step("Проверить наличие Issue с номером {issue}")
    public void shouldSeeIssueWithNumber(int issue) {
        $(withText("#" + issue)).should(Condition.exist);
        Allure.getLifecycle().addAttachment(
                "Исходники страницы",
                "text/html",
                "html",
                WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)

        );
    }

}