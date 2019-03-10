package com.juliofalbo.multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LocksExample {

    private static int counter = 0;
    private static Lock lock = new ReentrantLock();

    private static void increment() {

        try {
            lock.lock();
            IntStream.range(0, 10000).forEach(index -> counter++);
        } catch (Exception ignored) {

        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(LocksExample::increment);
        Thread t2 = new Thread(LocksExample::increment);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Counter is: " + counter);
    }

}
