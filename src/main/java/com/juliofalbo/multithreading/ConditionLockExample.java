package com.juliofalbo.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionLockExample {

    static Worker worker = new Worker();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                worker.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                worker.consumer();
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

class Worker {
    private static int counter = 0;
    private static Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    void producer() throws InterruptedException {
        lock.lock();
        System.out.println("Producer Locked");
        condition.await();
        System.out.println("Producer Unlocked");
        lock.unlock();
    }

    void consumer() throws InterruptedException {
        Thread.sleep(100);
        lock.lock();
        System.out.println("Consumer will unlock Producer");
        condition.signalAll();
        Thread.sleep(100);
        System.out.println("Consumer unlocked Producer");
        lock.unlock();
    }
}