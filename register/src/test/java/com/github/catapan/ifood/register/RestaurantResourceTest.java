package com.github.catapan.ifood.register;

import com.github.catapan.ifood.register.restaurant.DTO.UpdateRestaurantDTO;
import com.github.catapan.ifood.register.restaurant.Restaurant;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import javax.ws.rs.core.Response.Status;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TokenUtils;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager.class)
public class RestaurantResourceTest {

  private String token;

  @BeforeEach
  public void generateToken() throws Exception {
    token = TokenUtils.generateTokenString("/JWTOwnerClaims.json", null);
  }

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
    return RestAssured.given().contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
  }

  @Test
  @DataSet("restaurants-scenery-1.yml")
  public void testUpdateRestaurant() {
    UpdateRestaurantDTO updateRestaurantDTO = new UpdateRestaurantDTO();
    updateRestaurantDTO.fantasyName = "newName";
    Long parameterValue = 123L;
    given()
      .with().pathParam("id", parameterValue)
      .body(updateRestaurantDTO)
      .when().put("/restaurants/{id}")
      .then()
      .statusCode(Status.NO_CONTENT.getStatusCode())
      .extract().asString();

    Restaurant findById = Restaurant.findById(parameterValue);

    Assertions.assertEquals(updateRestaurantDTO.fantasyName, findById.name);
  }
}
