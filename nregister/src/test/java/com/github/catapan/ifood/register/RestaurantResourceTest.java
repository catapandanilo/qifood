package com.github.catapan.ifood.register;

import static io.restassured.RestAssured.given;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
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
}
