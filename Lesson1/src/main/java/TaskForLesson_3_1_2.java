import java.util.*;
import java.util.stream.Collectors;

public class TaskForLesson_3_1_2 {

    /**
     * Написать метод, который преобразует массив в ArrayList;
     */
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7};
        String[] array2 = {"A", "B", "C", "D", "E", "F", "G"};

        add(array);
        add(array2);

        asList(array);
        asList(array2);

        addAll(array);
        addAll(array2);

        stream(array);
        stream(array2);
    }

    public static <T> void add(T[] arr) {         //Использование метода ArrayList.add () для ручного добавления элементов массива в ArrayList
        ArrayList<T> array = new ArrayList<>();

        for (T t : arr) array.add(t);
        System.out.println("Метод 1: " + array);
    }

    private static <T> void asList(T[] arr) {        //Использование метода Arrays.asList () класса java.utils.Arrays
        ArrayList<T> array = new ArrayList<>(Arrays.asList(arr));
        System.out.println("Метод 2: " + array);
    }

    private static <T> void addAll(T[] arr) {     //Использование метода Collections.addAll () класса java.utils.Collections:
        ArrayList<T> array = new ArrayList<>();
        Collections.addAll(array, arr);
        System.out.println("Метод 3: " + array);
    }

    private static <T> void stream(T[] arr) {     //Использование метода Arrays.stream () класса java.utils.Arrays:
        ArrayList<T> array = (ArrayList<T>)
                Arrays.stream(arr)
                        .collect(Collectors.toList());
        System.out.println("Метод 4: " + array);
    }
}
