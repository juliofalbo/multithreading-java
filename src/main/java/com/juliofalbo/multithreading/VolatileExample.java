package com.juliofalbo.multithreading;

public class VolatileExample {
//    static boolean running = true;
    static boolean running = true;

    private void test() {
        new Thread(new Worker1()).start();
        new Thread(new Worker2()).start();
    }

    public static void main(String[] args) {
        new VolatileExample().test();
    }
}

class Worker1 implements Runnable {
    @Override
    public void run() {
        System.out.println("Worker 1 started");
        int counter = 0;
        while (VolatileExample.running) {
            counter++;
        }
        System.out.println("Worker 1 finished. Job Time:" + counter);
    }
}

class Worker2 implements Runnable {
    @Override
    public void run() {
        System.out.println("Worker 2 started");
        try {
            //Wait the execution of the loop in Worker1
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        System.out.println("Worker 2 finishing");
        VolatileExample.running = false;
    }
}