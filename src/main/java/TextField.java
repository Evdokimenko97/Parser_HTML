import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class TextField {
    private static final String tags = "input[type='text'],input[type='tel'],input[type='email'],input[type='password'],input[type='month'],input[type='number'],input[placeholder],textarea, label";

    public static String findTextField(Element body, String nameTextField) {
        ArrayList<String> textFields = new ArrayList<>();

        if (nameTextField.isEmpty()) {
            return "Отсутствует название поля ввода!";
        }

        Elements elementsTextField = body.getAllElements().select(tags);

        System.out.println("Общее количество полей ввода на странице имеющих: " + elementsTextField.size());

        // Перебор полей ввода
        if (!elementsTextField.isEmpty()) {
            for (Element textField : elementsTextField) {
                boolean cursorPropertyValue = textField.toString().contains("cursor");

                // Проверьте значение CSS-свойства "cursor" на text
                if (cursorPropertyValue) {
                    System.out.println("Ура!");
                    // Удаление всех дочерних элементов
                    textField.children().remove();

                    System.out.println(textField);
                }
            }
        }
//        return textFields.toString();
        return "Cool!";
    }
}
