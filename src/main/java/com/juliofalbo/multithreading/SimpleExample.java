package com.juliofalbo.multithreading;

public class SimpleExample {

    public static void main(String[] args) {
        SequencialRunner1 sequencialRunner1 = new SequencialRunner1();
        SequencialRunner2 sequencialRunner2 = new SequencialRunner2();

//        sequencialRunner1.startRunning();
//        sequencialRunner2.startRunning();

        System.out.println("------------------------------");

        Thread t1 = new Thread(new MultithreadingRunner1());
        Thread t2 = new Thread(new MultithreadingRunner2());

//        t1.start();
//        t2.start();

        var thread1 = new Thread1();
        var thread2 = new Thread2();

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------------Final---------------");


    }

    static void loop(int until, Class clazz) {
        loop(until, clazz, 0);
    }

    static void loop(int until, Class clazz, Integer sleepTime) {
        for (int i = 0; i < until; i++) {
            System.out.println(String.format("%s: %s", clazz.getSimpleName(), i));

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class SequencialRunner1 {
    void startRunning() {
        SimpleExample.loop(10, this.getClass());
    }
}

class SequencialRunner2 {
    void startRunning() {
        SimpleExample.loop(10, this.getClass());
    }
}

//Is not Parallel, is time-slicing
class MultithreadingRunner1 implements Runnable {
    public void run() {
        SimpleExample.loop(100, this.getClass());
    }
}

class MultithreadingRunner2 implements Runnable {
    public void run() {
        SimpleExample.loop(100, this.getClass());
    }
}

class Thread1 extends Thread {
    public void run() {
        SimpleExample.loop(100, this.getClass(), 50);
    }
}

class Thread2 extends Thread {
    public void run() {
        SimpleExample.loop(100, this.getClass(), 50);
    }
}