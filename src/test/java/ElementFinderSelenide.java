
public class ElementFinderSelenide {
    public static void main(String[] args) throws InterruptedException {
        // Buttons
        Browser.openBrowser("https://www.avito.ru/");
//        Browser.openBrowser("https://www.lsr.ru/spb/");
        String elementButtons = SelenideButton.findButtons("Работа");
        System.out.println(elementButtons);

//        // TextFields
//        Browser.openBrowser("https://www.avito.ru/sankt-peterburg/kvartiry/prodam/novostroyka-ASgBAQICAUSSA8YQAUDmBxSOUg?cd=1");
//        String elementTextField = SelenideTextField.findTextField("от");
//        System.out.println(elementTextField);
    }
}
