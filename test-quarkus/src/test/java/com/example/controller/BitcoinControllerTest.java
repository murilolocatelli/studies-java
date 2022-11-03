package com.example.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class BitcoinControllerTest {

    @Test
    public void testStatusCode200() {
        RestAssured
            .given().get("/bitcoins")
            .then()
            .statusCode(200);
    }

    @Test
    public void testBodyIsNotEmpty() {
        RestAssured
            .given().get("/bitcoins")
            .then()
            .statusCode(200)
            .body(CoreMatchers.not(Matchers.empty()));
    }

}
