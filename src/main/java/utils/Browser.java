package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Browser {
    public static void openBrowser(String url) throws InterruptedException {

        // С версии Chrome 111 невозможно открыть ссылки без авторизации запроса
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        Configuration.timeout = 5000; // 5 секунд

        try {
            // Открытие страницы
            Selenide.open(url);
        } catch (TimeoutException e) {
            // Очистка кэша и перезагрузка страницы при долгом ожидании открытия страницы
            Selenide.clearBrowserLocalStorage();
            Selenide.refresh();
            Thread.sleep(2000);
        }

        // Ожидание появления элемента на странице
        getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // Открытие браузера в полноэкранном режиме
        getWebDriver().manage().window().maximize();
    }
}
