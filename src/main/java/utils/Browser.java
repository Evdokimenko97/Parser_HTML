package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Browser {
    public static void openBrowser(String url) throws InterruptedException {
        // Автоматическое обновление ChromeDriver
        WebDriverManager.chromedriver().setup();

        // С версии Chrome 111 невозможно открыть ссылки без авторизации запроса
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        Configuration.pageLoadStrategy = "normal";

        try {
            // Открытие страницы
            Selenide.open(url);
            Thread.sleep(7000);
        } catch (TimeoutException e) {
            // Очистка кэша и перезагрузка страницы при долгом ожидании открытия страницы
            Selenide.clearBrowserLocalStorage();
            Selenide.refresh();
            Thread.sleep(7000);
        }
        // Ожидание появления элемента на странице
        getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // Открытие браузера в полноэкранном режиме
        getWebDriver().manage().window().maximize();
    }
}
