package com.github.catapan.ifood.register;

import static io.restassured.RestAssured.given;

import com.github.catapan.ifood.register.restaurant.Restaurant;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import javax.ws.rs.core.Response.Status;
import org.approvaltests.Approvals;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager.class)
public class RestaurantResourceTest {

  @Test
  @DataSet("restaurants-scenery-1.yml")
  public void testGetRestaurants() {
    String result = given()
      .when().get("/restaurants")
      .then()
      .statusCode(Status.OK.getStatusCode())
      .extract().asString();
    Approvals.verifyJson(result);
  }

  private RequestSpecification given() {
    return RestAssured.given().contentType(ContentType.JSON);
  }

  @Test
  @DataSet("restaurants-scenery-1.yml")
  public void testUpdateRestaurant() {
    Restaurant dto = new Restaurant();
    dto.name = "newName";
    Long parameterValue = 123L;
    given()
      .with().pathParam("id", parameterValue)
      .body(dto)
      .when().put("/restaurants/{id}")
      .then()
      .statusCode(Status.NO_CONTENT.getStatusCode())
      .extract().asString();

    Restaurant findById = Restaurant.findById(parameterValue);

    Assertions.assertEquals(dto.name, findById.name);
  }
}
