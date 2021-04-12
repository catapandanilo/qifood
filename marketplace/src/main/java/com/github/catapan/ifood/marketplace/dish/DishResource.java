package com.github.catapan.ifood.marketplace.dish;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/dishes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DishResource {

  @Inject
  PgPool pgPool;

  @GET
  public Multi<DishDTO> getDishes() {
    return Dish.findAll(pgPool);
  }
}
