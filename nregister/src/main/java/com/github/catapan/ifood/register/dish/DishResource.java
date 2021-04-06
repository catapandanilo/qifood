package com.github.catapan.ifood.register.dish;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/dishes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DishResource {

  @GET
  public List<Dish> getAll() {
    return Dish.listAll();
  }

  @POST
  @Transactional
  public Response add(Dish restaurant) {
    restaurant.persist();
    return Response.status(Status.CREATED).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public void update(@PathParam("id") Long id, Dish dish) {
    Optional<Dish> dishOptional = Dish.findByIdOptional(id);

    if (dishOptional.isEmpty()) {
      throw new NotFoundException();
    }

    Dish dishUpdated = dishOptional.get();
    dishUpdated.name = dish.name;
    dishUpdated.persist();
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public void delete(@PathParam("id") Long id) {
    Optional<Dish> restaurantOptional = Dish.findByIdOptional(id);
    restaurantOptional.ifPresentOrElse(Dish::delete, () -> {
      throw new NotFoundException();
    });
  }
}