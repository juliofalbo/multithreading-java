package com.juliofalbo.multithreading;

public class WaitNotifyExample {

    public static void main(String[] args) throws InterruptedException {

        Processor processor = new Processor();

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


class Processor {

    void produce() throws InterruptedException {

        synchronized (this) {
            System.out.println("Produce is waiting");
            wait();
            //One good strategy is to define a timeout to wait. This strategy avoid a eternal waiting
//            wait(2000);
            System.out.println("Produce finished");
        }

    }

    void consume() throws InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("Consume will notify the Produce");

//            notify();
            //If we call 2 wait() in produce method, only one notify() will not enough, because I still have one thread waiting. It is the reason to call notifyAll method.
            notifyAll();

            Thread.sleep(1000);
            System.out.println("Consume notified Produce");
        }

    }

}