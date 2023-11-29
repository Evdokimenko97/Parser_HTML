import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.time.LocalTime;

public class Classifier {

    private static final int labelIndex = 4; // Указываем индекс столбца с метками классов в данных (Начинается с 0)
    private static final int numClasses = 3; // Указываем количество классов
    private static final int batchSize = 150; // Указываем размер пакета данных для обучения

    private static final int numInputs = 4;  // Указываем количество входных параметров модели
    private static final int outputNum = 3;  // Указываем количество выходных классов
    private static final long seed = 6;  // Указываем начальное значение для генерации случайных чисел
    private static final int epochCount = 1000;  // Указываем количество эпох
    private static final String trainFile = "src/main/resources/iris-train.csv"; // Тренировочные данные
    private static final String testFile = "src/main/resources/irisTest.csv"; // Тестовые данные


    public static void main(String[] args) throws Exception {  // Объявляем точку входа программы
        StringBuilder sb = new StringBuilder();

        sb.append("Чтение файла: ").append(LocalTime.now().withNano(0)).append("\n");
        int numLinesToSkip = 0;  // Указываем количество строк для пропуска при чтении CSV-файла
        char delimiter = ',';  // Указываем разделитель в CSV-файле
        RecordReader recordReader = new CSVRecordReader(numLinesToSkip, delimiter);  // Создаем RecordReader для чтения CSV-файла
        recordReader.initialize(new FileSplit(new File(trainFile)));  // Инициализируем RecordReader чтением файла

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);  // Создаем итератор DataSet из RecordReader
        DataSet allData = iterator.next();  // Получаем весь набор данных
        allData.shuffle();  // Перемешиваем данные

        sb.append("Нормализация данных: ").append(LocalTime.now().withNano(0)).append("\n");
        DataNormalization normalizer = new NormalizerStandardize();  // Создаем объект для нормализации данных
        normalizer.fit(allData);  // Вычисляем статистику для нормализации на тренировочных данных
        normalizer.transform(allData);  // Нормализуем тренировочные данные

        sb.append("Построение модели: ").append(LocalTime.now().withNano(0)).append("\n");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()  // Создаем конфигурацию для многослойной сети
                .seed(seed)  // Устанавливаем seed для воспроизводимости результатов
                .activation(Activation.TANH)  // Указываем функцию активации для скрытых слоев
                .weightInit(WeightInit.XAVIER)  // Указываем метод инициализации весов
                .updater(new Sgd(0.1))  // Указываем метод оптимизации (стохастический градиентный спуск) и скорость обучения
                .l2(1e-4)  // Указываем коэффициент регуляризации L2
                .list()  // Создаем список слоев
                .layer(new DenseLayer.Builder().nIn(numInputs).nOut(4).build())  // Добавляем плотный (полносвязанный) слой
                .layer(new DenseLayer.Builder().nIn(4).nOut(3).build())  // Добавляем второй плотный слой
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)  // Добавляем выходной слой
                        .activation(Activation.SOFTMAX)  // Указываем функцию активации для выходного слоя
                        .nIn(3).nOut(outputNum).build())  // Указываем количество входных и выходных узлов
                .build();  // Строим конфигурацию

        sb.append("Создание многослойной нейронной сети: ").append(LocalTime.now().withNano(0)).append("\n");
        MultiLayerNetwork model = new MultiLayerNetwork(conf);  // Создаем многослойную нейронную сеть
        model.init();  // Инициализируем сеть
        model.setListeners(new ScoreIterationListener(100));  // Устанавливаем прослушиватель для вывода прогресса обучения

        sb.append("Обучение модели: ").append(LocalTime.now().withNano(0)).append("\n");
        for (int i = 0; i < epochCount; i++) {  // Запускаем обучение модели
            model.fit(allData);
        }

        sb.append("Чтение тестового файла: ").append(LocalTime.now().withNano(0)).append("\n");
        RecordReader newRecordReader = new CSVRecordReader(numLinesToSkip, delimiter);  // Создаем новый RecordReader для новых данных
        newRecordReader.initialize(new FileSplit(new File(testFile)));  // Инициализируем RecordReader новым файлом

        DataSetIterator newDataSetIterator = new RecordReaderDataSetIterator(newRecordReader, batchSize, labelIndex, numClasses);  // Создаем итератор для новых данных
        DataSet newData = newDataSetIterator.next();  // Получаем новые данные
        normalizer.transform(newData);  // Нормализуем новые данные

        sb.append("Результаты: ").append(LocalTime.now().withNano(0)).append("\n");
        INDArray newOutput = model.output(newData.getFeatures());  // Получаем предсказания модели на новых данных
        Evaluation newEval = new Evaluation(numClasses);  // Создаем объект для оценки результатов на новых данных
        newEval.eval(newData.getLabels(), newOutput);  // Оцениваем результаты классификации на новых данных

        System.out.println(newEval.stats()); // Выводим в консоль информацию о результатах на новых данных
        System.out.println(sb); // Выводим в консоль время выполнения задач
    }
}