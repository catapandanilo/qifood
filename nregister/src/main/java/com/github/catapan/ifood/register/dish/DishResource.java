package com.github.catapan.ifood.register.dish;

import com.github.catapan.ifood.register.restaurant.Restaurant;
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
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/restaurants/{idRestaurant}/dishes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Dish")
public class DishResource {

  @GET
  public List<Restaurant> getAll(@PathParam("idRestaurant") Long idRestaurant) {
    Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    if (restaurantOptional.isEmpty()) {
      throw new NotFoundException("Restaurant not exists");
    }

    return Dish.list("restaurant", restaurantOptional.get());
  }

  @POST
  @Transactional
  public Response add(@PathParam("idRestaurant") Long idRestaurant, Dish dish) {
    Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    if (restaurantOptional.isEmpty()) {
      throw new NotFoundException("Restaurant not exists");
    }

    Dish dishUpdated = new Dish();
    dishUpdated.name = dish.name;
    dishUpdated.description = dish.description;
    dishUpdated.price = dish.price;
    dishUpdated.restaurant = restaurantOptional.get();
    dishUpdated.persist();

    return Response.status(Status.CREATED).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public void update(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id,
    Dish dish) {
    Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    if (restaurantOptional.isEmpty()) {
      throw new NotFoundException("Restaurant not exists");
    }

    Optional<Dish> dishOptional = Dish.findByIdOptional(id);
    if (dishOptional.isEmpty()) {
      throw new NotFoundException("Dish not exists");
    }

    Dish dishUpdated = dishOptional.get();
    dishUpdated.price = dish.price;
    dishUpdated.persist();
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public void delete(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id) {
    Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    if (restaurantOptional.isEmpty()) {
      throw new NotFoundException("Restaurant not exists");
    }

    Optional<Dish> dishOptional = Dish.findByIdOptional(id);
    dishOptional.ifPresentOrElse(Dish::delete, () -> {
      throw new NotFoundException();
    });
  }
}