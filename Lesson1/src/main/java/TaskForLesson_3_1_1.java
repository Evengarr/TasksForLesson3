import java.util.Arrays;

public class TaskForLesson_3_1_1 {

    /**
     * Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
     */
    public static void main(String[] args) {

        int PRIMIS = 5;
        int SECUNDUS = 3;

        String[] strArray = {"A", "B", "C", "D", "E", "F"};
        System.out.println("Исходный массивЖ " + Arrays.toString(strArray));
        revers(strArray, PRIMIS, SECUNDUS);
        Integer[] intsArray = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("Исходный массив: " + Arrays.toString(intsArray));
        revers(intsArray, PRIMIS, SECUNDUS);

    }

    static void revers(Object[] arr, int a, int b) {
        Object tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
        System.out.println("Измененный массив: " + Arrays.toString(arr));
    }
}
