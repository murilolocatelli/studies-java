package com.example.test.business.impl;

import com.example.test.business.TestBusiness;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

@Service
public class TestBusinessImpl implements TestBusiness {

  @Override
  public void callService() {

    final CompletableFuture<Void> completableFuture =
        CompletableFuture.runAsync(() -> System.out.println(Thread.currentThread() + " Testing CompletableFuture"));

      System.out.println(Thread.currentThread() + " isDone: " + completableFuture.isDone());
  }

  @Override
  public void callService2() {

    final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

    final CompletableFuture<String> completableFuture =
        CompletableFuture.supplyAsync(() -> {

          this.sleep(1000);

          return Thread.currentThread() + " Testing CompletableFuture";
        }, newFixedThreadPool);

    try {
      System.out.println(Thread.currentThread() + " get: " + completableFuture.get());
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(Thread.currentThread() + " isDone: " + completableFuture.isDone());
  }

  @Override
  public void callService3() {

    final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

    final CompletableFuture<String> completableFuture =
        CompletableFuture.supplyAsync(() -> {

          this.sleep(3000);

          return Thread.currentThread() + " Testing CompletableFuture";
        }, newFixedThreadPool);

    completableFuture
        .thenAccept((response) -> System.out.println(Thread.currentThread() + " response: " + response));

    System.out.println(Thread.currentThread() + " isDone: " + completableFuture.isDone());
  }

  @Override
  public void callService4() {

    final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

    final CompletableFuture<String> completableFuture =
        CompletableFuture.supplyAsync(() -> {

          this.sleep(3000);

          return Thread.currentThread() + " Testing CompletableFuture";
        }, newFixedThreadPool);

    completableFuture
        .thenApply((response) -> { System.out.println(Thread.currentThread() + " response: " + response); return "complete";});

    System.out.println(Thread.currentThread() + " isDone: " + completableFuture.isDone());
  }

  @Override
  public void callServiceComposeDependent() {

    final CompletableFuture<String> completableFuture = this.completableFutureDependent1();

    completableFuture
        .thenComposeAsync((request) -> this.completableFutureDependent2(request))
        .thenAccept(System.out::println)
        .join();

    System.out.println(Thread.currentThread() + " isDone: " + completableFuture.isDone());
  }

  @Override
  public void callServiceCombineIndependent() {

    final CompletableFuture<String> completableFuture = this.completableFutureIndependent1();

    completableFuture
        .thenCombine(this.completableFutureIndependent2(), (response1, response2) -> response1 + response2)
        .exceptionally(ex -> { throw new RuntimeException("Error on call services: " + ex.getMessage()); })
        .thenAccept(System.out::println)
        .join();

    System.out.println(Thread.currentThread() + " isDone: " + completableFuture.isDone());
  }

  private CompletableFuture<String> completableFutureDependent1() {

    final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

    return CompletableFuture.supplyAsync(() -> {

      System.out.println("Testing CompletableFuture 1");

      this.sleep(5000);

      System.out.println("Testing CompletableFuture 2");

      return Thread.currentThread() + " Testing CompletableFuture";
    }, newFixedThreadPool);
  }

  private CompletableFuture<String> completableFutureDependent2(String param) {

    final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

    return CompletableFuture.supplyAsync(() -> {

      System.out.println("Testing CompletableFuture 3");

      this.sleep(5000);

      System.out.println("Testing CompletableFuture 4");

      return Thread.currentThread() + " Testing CompletableFuture2" + " result dependent: " + param;
    }, newFixedThreadPool);
  }

  private CompletableFuture<String> completableFutureIndependent1() {

    final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

    return CompletableFuture.supplyAsync(() -> {

      System.out.println("Testing CompletableFuture 1");

      this.sleep(5000);

      System.out.println("Testing CompletableFuture 2");

      return Thread.currentThread() + " Testing CompletableFuture";
    }, newFixedThreadPool);
  }

  private CompletableFuture<String> completableFutureIndependent2() {

    final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

    return CompletableFuture.supplyAsync(() -> {

      System.out.println("Testing CompletableFuture 3");

      this.sleep(5000);

      System.out.println("Testing CompletableFuture 4");

      return Thread.currentThread() + " Testing CompletableFuture2";
    }, newFixedThreadPool);
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
