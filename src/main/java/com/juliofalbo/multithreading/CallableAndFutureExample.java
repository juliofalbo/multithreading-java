package com.juliofalbo.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class CallableAndFutureExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<String>> ids = new ArrayList<>();

        IntStream.range(0, 5).forEach(i -> {
            ids.add(executorService.submit(new SomeWorker(i+1)));
        });

        ids.forEach(stringFuture -> {
            try {
                System.out.println(stringFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

}


class SomeWorker implements Callable<String>{

    private int id;

    public SomeWorker(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return String.format("id: %s", this.id);
    }
}