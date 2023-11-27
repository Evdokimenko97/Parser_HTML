import com.codeborne.selenide.SelenideElement;
import utils.Browser;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class ElementFinder {

    private static final String selectorElements = "button, input, a, textarea, label, img, svg";
    private static final String selectorDiv = "//div[text()[contains(.,'')]]";
    private static final String selectorSpan= "//span[text()[contains(.,'')]]";

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Начало: " + LocalTime.now().withNano(0));
        // Открытие страницы
        Browser.openBrowser("https://blog.dunin.ru/2019/12/05/%D1%81%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%B4%D0%B5%D0%BC%D0%BE-%D1%81%D0%B0%D0%B9%D1%82%D0%BE%D0%B2-%D0%B4%D0%BB%D1%8F-%D1%82%D0%B5%D1%81%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D1%89%D0%B8%D0%BA%D0%BE%D0%B2/");

        // Собираем список из элементов
        ArrayList<SelenideElement> elements = new ArrayList<>();
        elements.addAll($$(selectorElements));
//        elements.addAll($$x(selectorDiv));
        elements.addAll($$x(selectorSpan));

        // Список для хранения данных об элементах
        List<ElementCategories> elementsData = new ArrayList<>();

        // Итерируемся по каждому элементу
        for (SelenideElement element : elements) {
            if (element.isDisplayed()) {
                ElementCategories categories = new ElementCategories();
                int tagName = categories.setTag(element.getTagName());
                int type = categories.setType(element.getAttribute("type"));
                int cursor = categories.setCursor(element.getCssValue("cursor"));
                int placeholder = categories.setPlaceholder(element.getAttribute("placeholder") != null);

                // Записываем текст элемента в коллекцию
                String textElement;
                if (placeholder != 0) {
                    textElement = element.getAttribute("placeholder");
                } else {
                    textElement = getText(element);
                }
                categories.setOwnText(textElement);

                // Запись категорий элемента в числовом формате
                elementsData.add(new ElementCategories(tagName, type, cursor, placeholder, textElement));
            }
        }

        System.out.println("Запись csv: " + LocalTime.now().withNano(0));
        // Записываем данные в CSV файл
        CSVWriter.writeDataToCSV(elementsData, "src/main/resources/dataTest.csv");
    }

    private static String getText(SelenideElement element) {
        String text = element.getText();
        String alt = element.getAttribute("alt");
        String title = element.getAttribute("title");
        String value = element.getValue();

        if (!text.isEmpty()) {
            return text;
        } else if (alt != null && !alt.isEmpty()) {
            return alt;
        } else if (title != null && !title.isEmpty()) {
            return title;
        } else if (value != null && !value.isEmpty()) {
            return value;
        }
            return "Текст не был найден!";
    }
}
