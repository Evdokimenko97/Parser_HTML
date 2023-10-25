import java.io.IOException;

public class ElementFinder {
    public static void main(String[] args) throws IOException {
        String elementButton = Buttons.findButton("https://www.avito.ru/", "Авто");
        System.out.println(elementButton);
    }
}
