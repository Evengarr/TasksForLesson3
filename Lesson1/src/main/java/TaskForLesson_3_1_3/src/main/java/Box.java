package TaskForLesson_3_1_3.src.main.java;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    private final ArrayList<T> fruits;

    public Box(T... fruits) {
        this.fruits = new ArrayList<>(Arrays.asList(fruits));
    }


    public float getWeight() {      //расчет массы
        float weight = 0.0f;

        for (T f : fruits) {
            weight += f.getWeight();
        }
        return weight;
    }

    public boolean compare(Box<T> anotherBox) {        //сравнение объемов фруктов
        return getWeight() == anotherBox.getWeight();

    }

    public void pour(Box<T> anotherBox) {       //пересыпание фруктов
        this.fruits.addAll(anotherBox.fruits);
        fruits.clear();

    }

    public void addFruit(T fruit, int amount) {     //добавление фруктов в ящик

        for (int i = 0; i < amount; i++) {
            fruits.add(fruit);
        }

        System.out.print(amount + " фруктов ");
    }
}


