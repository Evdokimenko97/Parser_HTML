import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$$;

public class SelenideButton {
    private static final String tags = "button[type],input,a,span,div,img,svg";

    public static String findButtons(String nameButton) {
        ArrayList<String> buttons = new ArrayList<>(); // Список найденных кнопок с атрибутами в виде html

        if (nameButton.isEmpty()) {
            return "Отсутствует название кнопки!";
        }

        // Перебор элементов полей ввода по тегам и CSS атрибуту 'cursor: pointer'
        ElementsCollection selenideElements = $$(tags)
                .filterBy(Condition.cssValue("cursor", "pointer"));

        if (!selenideElements.isEmpty()) {
            for (SelenideElement el : selenideElements) {
                buttons.add(el.describe() + "\n");

                // Поиск по 'text()' и по contains в html элемента
                if (el.text().equals(nameButton)) {
                    System.out.println("Найдена кнопка: " + el.describe());
                } else if(el.describe().contains(nameButton)) {
                    System.out.println("Наиболее вероятная кнопка: " + el.describe());
                }
            }
        } else {
            return "По набору тегов '" + tags + "' и CSS функции 'cursor: text;' поля ввода не найдены!";
        }

        return buttons.toString();
    }
}
