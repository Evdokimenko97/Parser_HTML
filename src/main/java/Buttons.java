import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Buttons {
    private static final String tag = "button[type],input,a,span,div,img,svg";

    public static String findButton(String url, String nameButton) throws IOException {
        ArrayList<String> buttons = new ArrayList<>();
        // Получения body страницы
        Element body = Jsoup.connect(url).get().body();

        if (nameButton.isEmpty()) {
            return "Отсутствует название кнопки!";
        }

        // Поиск количества всех элементов с атрибутами 'onClick=myFunction()' и 'border-sizing=border-box'
        Elements elementsButton = body.getAllElements()
                .attr("onclick", "myFunction()")
                .attr("border-sizing", "border-box");
        System.out.println("Общее количество элементов имеющих функцию onClick и border: " + elementsButton.size());

        // Выбор списка тегов
        Elements buttonsType = elementsButton.select(tag);
        System.out.println("Кнопок с тегами '" + tag + "': " + buttonsType.size());

        // Перебор кнопок
        if (!buttonsType.isEmpty()) {
            for (Element button : buttonsType) {
                // Удаление всех дочерних элементов
                button.children().remove();

                // Поиск элемента
                if (button.text().replace("&nbsp;", " ").contains(nameButton) ||
                        button.toString().replace("&nbsp;", " ").contains(nameButton)) {
                    buttons.add(button + "|||"); // ||| - необходим для разделения элементов между собой
                }
            }
        } else {
            return "По набору тегов '" + tag + "' ничего не найдено!";
        }
        return buttons.toString();
    }
}