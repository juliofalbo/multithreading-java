package com.juliofalbo.multithreading;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerExample {

    public static void main(String[] args) throws InterruptedException {

        AnotherProcessor processor = new AnotherProcessor();

        Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}


class AnotherProcessor {

    private List<Integer> list = new ArrayList<>();
    private final int LIMIT = 5;
    private final int BOTTOM = 0;
    private final Object lock = new Object();
    private int value;

    void produce() throws InterruptedException {

        synchronized (lock) {
            while (true) {
                if (list.size() == LIMIT) {
                    System.out.println("Waiting for removing items from the list.");
                    lock.wait();
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value++);
                    lock.notify();
                }

                Thread.sleep(500);
            }
        }


    }

    void consume() throws InterruptedException {

        synchronized (lock) {
            while (true) {
                if (list.size() == BOTTOM) {
                    System.out.println("Waiting for adding items from the list.");
                    lock.wait();
                } else {
                    System.out.println("Removed: " + list.remove(--value));
                    lock.notify();
                }

                Thread.sleep(500);
            }
        }

    }

}