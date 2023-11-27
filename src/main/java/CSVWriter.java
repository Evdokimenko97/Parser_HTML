import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class CSVWriter {
    public static void writeDataToCSV(List<ElementCategories> buttonDataList, String fileName) throws IOException {
        try (com.opencsv.CSVWriter writer = new com.opencsv.CSVWriter(new FileWriter(fileName))) {
            // Заголовки CSV файла
            String[] headers = {"Тег", "Тип", "Курсор", "Placeholder", "Текст"};
            writer.writeNext(headers);

            // Записываем данные о кнопках
            for (ElementCategories buttonData : buttonDataList) {
                String[] data = {
                        String.valueOf(buttonData.getTag()),
                        String.valueOf(buttonData.getType()),
                        String.valueOf(buttonData.getCursor()),
                        String.valueOf(buttonData.getPlaceholder()),
                        String.valueOf(buttonData.getOwnText())
                };
                writer.writeNext(data);
            }
        }
        System.out.println("Конец: " + LocalTime.now().withNano(0));
    }
}
