package com.juliofalbo.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        IntStream.range(0, 12).forEach(index -> executorService.execute(() -> Downloader.INSTANCE.downloadData(index)));
        executorService.shutdown();
    }

}


enum Downloader {
    INSTANCE;

    private Semaphore semaphore = new Semaphore(3, true);

    public void downloadData(int i) {
        try {
            semaphore.acquire();
            download(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private void download(int i) throws InterruptedException {
        System.out.println(i + " Downloading...");
        Thread.sleep(2000);
    }
}