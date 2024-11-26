import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.IntStream;

public class BasicDataOperationUsingSet {
    static final String PATH_TO_DATA_FILE = "list/float.data";

    float dataValueToSearch;
    float[] dataArray;
    Set<Float> dataSet = new HashSet<>();

    public static void main(String[] args) {
        BasicDataOperationUsingSet basicDataOperationUsingSet = new BasicDataOperationUsingSet(args);
        basicDataOperationUsingSet.doDataOperation();
    }

    BasicDataOperationUsingSet(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.dataValueToSearch = Float.parseFloat(valueToSearch);

        dataArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        dataSet = new HashSet<>(Arrays.asList(convertToFloatArray(dataArray)));
    }

    private Float[] convertToFloatArray(float[] floatArray) {
        Float[] result = new Float[floatArray.length];
        for (int i = 0; i < floatArray.length; i++) {
            result[i] = floatArray[i];
        }
        return result;
    }

    private void doDataOperation() {
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        searchSet();
        findMinAndMaxInSet();
        compareArrayAndSet();

        Utils.writeArrayToFile(dataArray, PATH_TO_DATA_FILE + ".sorted");
    }

    private void sortArray() {
        double[] doubleArray = new double[dataArray.length];
        for (int i = 0; i < dataArray.length; i++) {
            doubleArray[i] = dataArray[i];
        }
        long startTime = System.nanoTime();
        double[] sortedDoubleArray = Arrays.stream(doubleArray)
                .sorted()
                .toArray();
        dataArray = new float[sortedDoubleArray.length];
        for (int i = 0; i < sortedDoubleArray.length; i++) {
            dataArray[i] = (float) sortedDoubleArray[i];
        }

        Utils.printOperationDuration(startTime, "сортування масиву значень типу float через потоки");
    }

    private void searchArray() {
        double[] doubleArray = new double[dataArray.length];
        for (int i = 0; i < dataArray.length; i++) {
            doubleArray[i] = dataArray[i];
        }
        double searchValue = dataValueToSearch;

        long startTime = System.nanoTime();

        int index = IntStream.range(0, doubleArray.length)
                .filter(i -> doubleArray[i] == searchValue)
                .findFirst()
                .orElse(-1);
        Utils.printOperationDuration(startTime, "пошук в масивi значень типу float через потоки");
        if (index >= 0) {
            System.out.println("Значення '" + dataValueToSearch + "' знайдено в масивi за індексом: " + index);
        } else {
            System.out.println("Значення '" + dataValueToSearch + "' в масивi не знайдено.");
        }
    }

    private void findMinAndMaxInArray() {
        if (dataArray == null || dataArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        double[] doubleArray = new double[dataArray.length];
        for (int i = 0; i < dataArray.length; i++) {
            doubleArray[i] = dataArray[i];
        }

        long startTime = System.nanoTime();

        OptionalDouble min = Arrays.stream(doubleArray).min();
        OptionalDouble max = Arrays.stream(doubleArray).max();

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної величини в масивi");

        if (min.isPresent()) {
            System.out.println("Мiнiмальне значення в масивi: " + (float) min.getAsDouble());
        } else {
            System.out.println("Мінімальне значення не знайдено.");
        }

        if (max.isPresent()) {
            System.out.println("Максимальне значення в масивi: " + (float) max.getAsDouble());
        } else {
            System.out.println("Максимальне значення не знайдено.");
        }
    }

    private void searchSet() {
        long startTime = System.nanoTime();
        boolean isFound = this.dataSet.contains(dataValueToSearch);
        Utils.printOperationDuration(startTime, "пошук в HashSet значень типу float");

        if (isFound) {
            System.out.println("Значення '" + dataValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Значення '" + dataValueToSearch + "' в HashSet не знайдено.");
        }
    }

    private void findMinAndMaxInSet() {
        if (dataSet == null || dataSet.isEmpty()) {
            System.out.println("HashSet порожнiй або не iнiцiалiзований.");
            return;
        }
        double[] doubleArray = new double[dataSet.size()];
        int i = 0;
        for (Float value : dataSet) {
            doubleArray[i++] = value;
        }

        long startTime = System.nanoTime();

        OptionalDouble min = Arrays.stream(doubleArray).min();
        OptionalDouble max = Arrays.stream(doubleArray).max();
        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної величини в HashSet");
        if (min.isPresent()) {
            System.out.println("Мiнiмальне значення в HashSet: " + (float) min.getAsDouble());
        } else {
            System.out.println("Мінімальне значення не знайдено.");
        }
        if (max.isPresent()) {
            System.out.println("Максимальне значення в HashSet: " + (float) max.getAsDouble());
        } else {
            System.out.println("Максимальне значення не знайдено.");
        }
    }

    private void compareArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + dataArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + dataSet.size());

        double[] doubleArray = new double[dataArray.length];
        for (int i = 0; i < dataArray.length; i++) {
            doubleArray[i] = dataArray[i];
        }

        boolean allElementsMatch = Arrays.stream(doubleArray)
                .allMatch(value -> dataSet.contains((float) value));

        if (allElementsMatch) {
            System.out.println("Всi елементи масиву присутнi в HashSet.");
        } else {
            System.out.println("Не всi елементи масиву присутнi в HashSet.");
        }
    }
}

class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }

    static float[] readArrayFromFile(String pathToFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            double[] doubleArray = br.lines()
                    .map(line -> Float.parseFloat(line))
                    .mapToDouble(Float::doubleValue)
                    .toArray();

            float[] floatArray = new float[doubleArray.length];
            for (int i = 0; i < doubleArray.length; i++) {
                floatArray[i] = (float) doubleArray[i];
            }
            return floatArray;
        } catch (IOException e) {
            e.printStackTrace();
            return new float[0];
        }
    }

    static void writeArrayToFile(float[] dataArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (float value : dataArray) {
                writer.write(Float.toString(value));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}