package com.juliofalbo.multithreading;

import java.util.stream.IntStream;

public class SynchronizedExample {

    private static int counter1 = 0;
    private static int counter2 = 0;
    private static int counter3 = 0;
    private static int counter4 = 0;

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        withBlockLock();
        withClassLock();
    }

    private static void withClassLock() throws InterruptedException {
        long time = executeThreads(new Thread(SynchronizedExample::process2), new Thread(SynchronizedExample::process2));
        System.out.println(String.format("ClassLock: Counter 3 = %s | Counter 4 = %s | Time = %s milliseconds", counter3, counter4, time));
    }

    private static void process2() {
        IntStream.range(0, 10000000).forEach(index -> {
            incrementCounter3();
            incrementCounter4();
        });
    }

    private synchronized static void incrementCounter3() {
        counter3++;
    }

    private synchronized static void incrementCounter4() {
        counter4++;
    }

    private static void withBlockLock() throws InterruptedException {
        long time = executeThreads(new Thread(SynchronizedExample::process1), new Thread(SynchronizedExample::process1));
        System.out.println(String.format("BlockLock: Counter 1 = %s | Counter 2 = %s | Time = %s milliseconds", counter1, counter2, time));
    }

    private static void process1() {
        IntStream.range(0, 10000000).forEach(index -> {
            incrementCounter1();
            incrementCounter2();
        });
    }

    private static void incrementCounter1() {
        synchronized (lock1) {
            counter1++;
        }
    }

    private static void incrementCounter2() {
        synchronized (lock2) {
            counter2++;
        }
    }

    private static long executeThreads(Thread t1, Thread t2) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

}
