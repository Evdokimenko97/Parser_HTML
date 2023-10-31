package elements;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Buttons {
    private static final String tags = "button[type],input,a,span,div,img,svg";

    public static String findButton(Element body, String nameButton) {
        ArrayList<String> buttonsFirst = new ArrayList<>(); // Список точного совпадения найденных кнопок с атрибутами
        ArrayList<String> buttonsSecond = new ArrayList<>(); // Список вероятных совпадений найденных кнопок с атрибутами

        if (nameButton.isEmpty()) {
            return "Отсутствует название кнопки!";
        }

        // Поиск количества всех элементов с атрибутами 'onClick=myFunction()' и 'border-sizing=border-box'
        Elements elementsButton = body.getAllElements()
                .attr("onclick", "myFunction()")
                .attr("border-sizing", "border-box");
        System.out.println("Общее количество элементов имеющих функцию onClick и border: " + elementsButton.size());

        // Выбор списка тегов
        Elements buttonsType = elementsButton.select(tags);
        System.out.println("Количество кнопок с тегами '" + tags + "': " + buttonsType.size());

        // Перебор кнопок
        if (!buttonsType.isEmpty()) {
            for (Element button : buttonsType) {
                // Удаление всех дочерних элементов
                button.children().remove();

                // Поиск элемента по тексту и по всем атрибутама элемента содержащим текст 'nameButton'
                if (button.text().equals(nameButton)) {
                    buttonsFirst.add(button + "\n");
                } else if (button.toString().contains(nameButton)) {
                    buttonsSecond.add(button + "\n");
                }
            }
        } else {
            return "По набору тегов '" + tags + "' кнопки не найдены!";
        }

        // Возвращаем элементы найденных кнопок
        if (!buttonsFirst.isEmpty()) {
            System.out.println("Точное совпадение кнопок: " + buttonsFirst.size());
            return buttonsFirst.toString();
        } else {
            System.out.println("Наиболее вероятные кнопки: " + buttonsSecond.size());
            return buttonsSecond.toString();
        }
    }
}