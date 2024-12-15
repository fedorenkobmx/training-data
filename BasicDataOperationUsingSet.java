import java.io.*;
import java.util.*;

public class BasicDataOperationUsingSet {
    static final String PATH_TO_DATA_FILE = "list/float.data";

    Float floatValueToSearch;
    Float[] floatArray;
    Set<Float> floatSet = new HashSet<>();

    public static void main(String[] args) {
        BasicDataOperationUsingSet basicDataOperationUsingSet = new BasicDataOperationUsingSet(args);
        basicDataOperationUsingSet.doDataOperation();
    }

    BasicDataOperationUsingSet(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Відсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.floatValueToSearch = Float.parseFloat(valueToSearch);

        floatArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        floatSet = new HashSet<>(Arrays.asList(floatArray));
    }

    private void doDataOperation() {
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        searchSet();
        findMinAndMaxInSet();

        sortHashSet();

        compareArrayAndSet();

        Utils.writeArrayToFile(floatArray, PATH_TO_DATA_FILE + ".sorted");
    }

    private void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(floatArray, Collections.reverseOrder());

        Utils.printOperationDuration(startTime, "сортування масиву типу float в зворотній бік.");
    }

    private void sortHashSet() {
        long startTime = System.nanoTime();

        List<Float> floatList = new ArrayList<>(floatSet);

        Collections.sort(floatList, Collections.reverseOrder());

        Utils.printOperationDuration(startTime, "сортування колекції типу HashSet в зворотній бік.");
    }

    private void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(floatArray, floatValueToSearch);

        Utils.printOperationDuration(startTime, "пошук у масиві типу float");

        if (index >= 0) {
            System.out.println("Значення '" + floatValueToSearch + "' знайдено в масиві за індексом: " + index);
        } else {
            System.out.println("Значення '" + floatValueToSearch + "' у масиві не знайдено.");
        }
    }

    private void findMinAndMaxInArray() {
        if (floatArray == null || floatArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();

        Float min = floatArray[0];
        Float max = floatArray[0];

        for (Float floatValue : floatArray) {
            if (floatValue < min) {
                min = floatValue;
            }
            if (floatValue > max) {
                max = floatValue;
            }
        }

        Utils.printOperationDuration(startTime, "пошук мінімального і максимального значення у масиві типу float");

        System.out.println("Мінімальне значення у масиві: " + min);
        System.out.println("Максимальне значення у масиві: " + max);
    }

    private void searchSet() {
        long startTime = System.nanoTime();

        boolean isFound = floatSet.contains(floatValueToSearch);

        Utils.printOperationDuration(startTime, "пошук у HashSet типу float");

        if (isFound) {
            System.out.println("Значення '" + floatValueToSearch + "' знайдено у HashSet");
        } else {
            System.out.println("Значення '" + floatValueToSearch + "' у HashSet не знайдено.");
        }
    }

    private void findMinAndMaxInSet() {
        if (floatSet == null || floatSet.isEmpty()) {
            System.out.println("HashSet порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();

        Float min = Collections.min(floatSet);
        Float max = Collections.max(floatSet);

        Utils.printOperationDuration(startTime, "пошук мінімального і максимального значення у HashSet типу float");

        System.out.println("Мінімальне значення у HashSet: " + min);
        System.out.println("Максимальне значення у HashSet: " + max);
    }

    private void compareArrayAndSet() {
        System.out.println("Кількість елементів у масиві: " + floatArray.length);
        System.out.println("Кількість елементів у HashSet: " + floatSet.size());

        boolean allElementsMatch = true;
        for (Float floatValue : floatArray) {
            if (!floatSet.contains(floatValue)) {
                allElementsMatch = false;
                break;
            }
        }

        if (allElementsMatch) {
            System.out.println("Всі елементи масиву присутні в HashSet.");
        } else {
            System.out.println("Не всі елементи масиву присутні в HashSet.");
        }
    }

}

class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        System.out.println("\n>>>>>>>>>> Час виконання операції '" + operationName + "': " + (endTime - startTime)
                + " наносекунд");
    }

    static Float[] readArrayFromFile(String pathToFile) {
        Float[] tempArray = new Float[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                tempArray[index++] = Float.parseFloat(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Arrays.copyOf(tempArray, index);
    }

    static void writeArrayToFile(Float[] floatArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (Float value : floatArray) {
                writer.write(value.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
