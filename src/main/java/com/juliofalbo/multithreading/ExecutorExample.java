package com.juliofalbo.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ExecutorExample {

    //Choose one and compare the execution
    public static void main(String[] args) {
        ExecutorService fixedExecutorService = Executors.newFixedThreadPool(3);
        ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();
        ExecutorService cachedExecutorService = Executors.newCachedThreadPool();

//        IntStream.range(0, 5).forEach(i -> fixedExecutorService.execute(new WorkerExec()));
//        IntStream.range(0, 5).forEach(i -> singleExecutorService.execute(new WorkerExec()));
        IntStream.range(0, 5).forEach(i -> cachedExecutorService.execute(new WorkerExec()));

        fixedExecutorService.shutdown();
        singleExecutorService.shutdown();
        cachedExecutorService.shutdown();
    }

}

class WorkerExec implements Runnable{

    @Override
    public void run() {
        IntStream.range(0, 10).forEach(i -> {
            System.out.println(i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
