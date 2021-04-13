package com.github.catapan.ifood.marketplace.restaurant;

import com.github.catapan.ifood.marketplace.dish.Dish;
import com.github.catapan.ifood.marketplace.dish.dto.DishDTO;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

  @Inject
  PgPool pgPool;

  @GET
  @Path("{idRestaurant}/dishes")
  public Multi<DishDTO> getDishes(@PathParam("idRestaurant") Long idRestaurant) {
    return Dish.findAll(pgPool, idRestaurant);
  }

}
