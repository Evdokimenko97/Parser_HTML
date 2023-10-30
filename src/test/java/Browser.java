import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
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
        Configuration.pageLoadStrategy = "eager";


        // Открытие страницы
        Selenide.open(url);
        // Ожидание появления элемента на странице
        getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // Открытие браузера в полноэкранном режиме
        getWebDriver().manage().window().maximize();
    }
}
