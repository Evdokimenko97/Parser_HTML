import com.codeborne.selenide.Selenide;
import elements.Buttons;
import elements.TextFields;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import utils.Browser;

import java.io.IOException;
import java.util.Objects;

public class ElementFinder {
    public static void main(String[] args) throws IOException, InterruptedException {
//        Element body = Jsoup.connect("https://www.avito.ru/").get().body();

        // Открытие страницы
        Browser.openBrowser("https://www.avito.ru/");
        // Выполняем JavaScript-команду для получения HTML-кода страницы
        String html = Selenide.executeJavaScript("return document.documentElement.outerHTML;");


        // Используем Jsoup для парсинга полученного HTML-кода
        Element body1 = Jsoup.parse(Objects.requireNonNull(html)).body();
        // elements.Buttons - поиск кнопок
        String elementButton = Buttons.findButton(body1, "Работа");
        System.out.println(elementButton);
        System.out.println("===========================");

        // Используем Jsoup для парсинга полученного HTML-кода
        Element body2 = Jsoup.parse(Objects.requireNonNull(html)).body();
        // TextField - поиск элементов полей ввода
        String elementTextField = TextFields.findTextField(body2, "Поиск");
        System.out.println(elementTextField);
    }
}
