package TaskForLesson_3_1_3.src.main.java;

public class TaskForLesson_3_1_3 {

    /**
     * Большая задача:
     * a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
     * b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
     * c. Для хранения фруктов внутри коробки можете использовать ArrayList;
     * d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f,
     * не важно в каких это единицах);
     * e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра,
     * true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
     * f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку фруктов, нельзя яблоки высыпать
     * в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
     * g. Не забываем про метод добавления фрукта в коробку.
     */

    public static void main(String[] args) {
        Box<Orange> orange = new Box<>();
        Box<Orange> orange1 = new Box<>();
        Box<Apple> apple = new Box<>();
        Box<Apple> apple1 = new Box<>();


        System.out.print("В ящике 1: ");
        orange.addFruit(new Orange(), 20);
        System.out.println("и весят: " + orange.getWeight());
        System.out.print("В ящике 2: ");
        orange1.addFruit(new Orange(), 20);
        System.out.println("и весят:  " + orange1.getWeight());
        System.out.print("В ящике 3: ");
        apple.addFruit(new Apple(), 35);
        System.out.println("и весят: " + apple.getWeight());
        System.out.print("В ящике 4: ");
        apple1.addFruit(new Apple(), 20);
        System.out.println("и весят: " + apple1.getWeight());
        System.out.println(orange.compare(orange1));
        System.out.println(apple.compare(apple1));

        orange.pour(orange1);
        apple1.pour(apple);


    }



}
