import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class ElementFinder {
    /**
     * Сайты
     * 1. Кнопки - https://www.avito.ru/
     * 2. Поля ввода - https://www.jotform.com/form-templates/new-customer-registration-form
     */
    public static void main(String[] args) throws IOException {
        // Получаем body страницы
//        Element body = Jsoup.connect("...").get().body();

//        // Кнопки
//        Element body = Jsoup.connect("https://www.avito.ru/").get().body();
//        String elementButton = Buttons.findButton(body, "Работа");
//        System.out.println(elementButton);

        // TextField
        Element body = Jsoup.connect("https://www.avito.ru/").get();
        String elementTextField = TextField.findTextField(body, "Full Name");
        System.out.println(elementTextField);
    }
}
