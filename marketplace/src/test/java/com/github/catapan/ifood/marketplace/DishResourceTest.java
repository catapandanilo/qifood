package com.github.catapan.ifood.marketplace;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class DishResourceTest {

    @Test
    public void dishHelloEndpoint() {
        String body = given()
          .when().get("/dishes")
          .then()
          .statusCode(200).extract().asString();
        System.out.println(body);
    }

}