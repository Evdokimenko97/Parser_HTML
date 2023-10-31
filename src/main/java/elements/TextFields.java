package elements;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class TextFields {
    private static final String tags = "input[type='text'],input[type='tel'],input[type='email'],input[type='password']," +
            "input[type='month'],input[type='number'],input[placeholder],textarea";

    public static String findTextField(Element body, String nameTextField) {
        ArrayList<String> textFieldsFirst = new ArrayList<>();  // Список точного совпадения найденных полей ввода с атрибутами
        ArrayList<String> textFieldsSecond = new ArrayList<>();  // Список вероятного совпадения найденных полей ввода с атрибутами

        if (nameTextField.isEmpty()) {
            return "Отсутствует название поля ввода!";
        }

        // Поиск количества всех элементов с атрибутами 'onkeydown=myFunction()' и 'border-sizing=border-box'
        Elements elementsTextField = body.getAllElements()
                .attr("onkeydown", "myFunction()")
                .attr("border-sizing", "border-box");
        System.out.println("Общее количество элементов имеющих функцию onkeydown и border: " + elementsTextField.size());

        // Выбор списка тегов
        Elements textFieldType = elementsTextField.select(tags);
        System.out.println("Количество полей ввода с тегами '" + tags + "': " + textFieldType.size());

        // Перебор текстовых полей
        if (!textFieldType.isEmpty()) {
            for (Element textField : textFieldType) {
                // Удаление всех дочерних элементов
                textField.children().remove();

                // Поиск элемента по 'placeholder' или 'value' с конкретным названия поля ввода
                if (textField.toString().contains("placeholder=\"" + nameTextField + "\"") || textField.toString().contains("value=\"" + nameTextField + "\"")) {
                    textFieldsFirst.add(textField + "\n");
                // Поиск названия поля ввода в атрибутах элемента
                } else if (textField.toString().contains(nameTextField)) {
                    textFieldsSecond.add(textField + "\n");
                }
            }
        } else {
            return "По набору тегов '" + tags + "' поля ввода не найдены!";
        }


        // Возвращаем элементы найденных кнопок
        if (!textFieldsFirst.isEmpty()) {
            System.out.println("Точное совпадение кнопок: " + textFieldsFirst.size());
            return textFieldsFirst.toString();
        } else {
            System.out.println("Наиболее вероятные кнопки: " + textFieldsSecond.size());
            return textFieldsSecond.toString();
        }
    }
}
