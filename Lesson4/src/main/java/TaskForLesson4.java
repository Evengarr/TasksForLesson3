/**
 * 1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
 * Используйте wait/notify/notifyAll.
 */

public class TaskForLesson4 {

    static final Object monitor = new Object();
    static volatile int currentNum = 1;
    static final int N = 10;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                for (int i = 0; i < N; i++) {
                    synchronized (monitor) {
                        while (currentNum != 1) {
                            monitor.wait();
                        }
                        printSymbol("A", 2);
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < N; i++) {
                    synchronized (monitor) {
                        while (currentNum != 2) {
                            monitor.wait();
                        }
                        printSymbol("B", 3);
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < N; i++) {
                    synchronized (monitor) {
                        while (currentNum != 3) {
                            monitor.wait();
                        }
                        printSymbol("C", 4);
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < N; i++) {
                    synchronized (monitor) {
                        while (currentNum != 4) {
                            monitor.wait();
                        }
                        printSymbol("D", 1);
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void printSymbol(String a, int current) {
        System.out.print(a);
        if (current == 1){
            System.out.println();
        }
        currentNum = current;
    }
}
