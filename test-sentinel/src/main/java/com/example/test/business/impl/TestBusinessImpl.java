package com.example.test.business.impl;

import com.example.test.business.TestBusiness;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TestBusinessImpl implements TestBusiness {

  @HystrixCommand(fallbackMethod = "defaultCall", commandProperties = {
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
  })
  public String callService(final Long timeout) {

    System.out.println("Thread Name: " + Thread.currentThread().getName());

    String uri = "http://www.mocky.io/v2/5d78f7e63000004a0031f8bb";

    if (timeout != null) {
      uri += "?mocky-delay=" + timeout + "ms";
    }

    try {
      final HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(uri))
          .GET()
          .build();

      final HttpResponse<String> response = HttpClient.newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        throw new RuntimeException("Error call external service");
      }

      return response.body();

    } catch (Exception e) {
      e.printStackTrace();

      return e.getMessage();
    }
  }

  public String defaultCall(final Long timeout) {

    System.out.println("Thread Name: " + Thread.currentThread().getName());

    return "Default call";
  }

}
