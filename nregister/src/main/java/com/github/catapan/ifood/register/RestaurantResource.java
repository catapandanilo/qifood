package com.github.catapan.ifood.register;

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

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

    @GET
    public List<Restaurant> hello() {
        return Restaurant.listAll();
    }

    @POST
    @Transactional
    public Response add(Restaurant restaurant) {
        restaurant.persist();
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, Restaurant restaurant) {
        Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);

        if(restaurantOptional.isEmpty()){
            throw new NotFoundException();
        }

        Restaurant restaurantUpdated = restaurantOptional.get();
        restaurantUpdated.name = restaurant.name;
        restaurantUpdated.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
        restaurantOptional.ifPresentOrElse(Restaurant::delete, () -> { throw new NotFoundException(); });
    }
}