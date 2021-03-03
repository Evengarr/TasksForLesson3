import java.util.concurrent.CyclicBarrier;

public class TaskForLesson5 {

    public static final int CARS_COUNT = 4;
    public static final int HALF_CARS_COUNT = CARS_COUNT / 2;

    public static void main(String[] args) {
        System.err.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        CyclicBarrier cb = new CyclicBarrier(5);
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cb);
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
        try {
            cb.await();
            System.err.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            cb.await();
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}