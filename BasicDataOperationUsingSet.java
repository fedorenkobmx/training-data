import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        long startTime = System.nanoTime();
        Arrays.sort(dataArray);
        Utils.printOperationDuration(startTime, "сортування масиву значень типу float");
    }

    private void searchArray() {
        long startTime = System.nanoTime();
        int index = Arrays.binarySearch(this.dataArray, dataValueToSearch);
        Utils.printOperationDuration(startTime, "пошук в масивi значень типу float");

        if (index >= 0) {
            System.out.println("Значення '" + dataValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + dataValueToSearch + "' в масивi не знайдено.");
        }
    }

    private void findMinAndMaxInArray() {
        if (dataArray == null || dataArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        float min = dataArray[0];
        float max = dataArray[0];

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної величини в масивi");

        for (float value : dataArray) {
            if (value < min)
                min = value;
            if (value > max)
                max = value;
        }

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
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

        long startTime = System.nanoTime();

        float min = Collections.min(dataSet);
        float max = Collections.max(dataSet);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної величини в HashSet");

        System.out.println("Мiнiмальне значення в HashSet: " + min);
        System.out.println("Максимальне значення в HashSet: " + max);
    }

    private void compareArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + dataArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + dataSet.size());

        boolean allElementsMatch = true;
        for (float value : dataArray) {
            if (!dataSet.contains(value)) {
                allElementsMatch = false;
                break;
            }
        }

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
        float[] tempArray = new float[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                float value = Float.parseFloat(line);
                tempArray[index++] = value;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        float[] finalArray = new float[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        return finalArray;
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