import com.codeborne.selenide.*;

import java.util.ArrayList;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;

public class SelenideTextField {
    public static final String tags = "input[type='text'],input[type='tel'],input[type='email']," +
            "input[type='password'],input[type='month'],input[type='number'],input[placeholder],textarea,label";

    public static String findTextField(String nameTextField) {
        ArrayList<String> textFields = new ArrayList<>(); // Список всех найденных полей ввода в виде html

        if (nameTextField.isEmpty()) {
            return "Отсутствует название поля ввода!";
        }

        // Перебор элементов полей ввода по тегам и CSS атрибуту 'cursor: text'
        ElementsCollection selenideElements = $$(tags)
                .filterBy(Condition.cssValue("cursor", "text"));

        if (!selenideElements.isEmpty()) {
            for (SelenideElement el : selenideElements) {
                textFields.add(el.describe() + "\n");

                // Поиск по 'placeholder' наиболее вероятных полей ввода
                if (Objects.equals(el.attr("placeholder"), nameTextField)) {
                    System.out.println("Наиболее вероятное поле ввода: " + el.describe());
                }
            }
        } else {
            return "По набору тегов '" + tags + "' и CSS функции 'cursor: text;' поля ввода не найдены!";
        }

        System.out.println("\nКоличество найденных полей ввода: " + textFields.size());
        return textFields.toString();
    }
}
