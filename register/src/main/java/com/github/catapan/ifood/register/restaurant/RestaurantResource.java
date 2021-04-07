package com.github.catapan.ifood.register.restaurant;

import com.github.catapan.ifood.register.restaurant.DTO.AddRestaurantDTO;
import com.github.catapan.ifood.register.restaurant.DTO.RestaurantDTO;
import com.github.catapan.ifood.register.restaurant.DTO.RestaurantMapper;
import com.github.catapan.ifood.register.restaurant.DTO.UpdateRestaurantDTO;
import com.github.catapan.ifood.register.restaurant.infra.ConstraintViolationResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("owner")
@Tag(name = "Restaurant")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
@SecurityRequirement(name = "ifood-oauth", scopes = {})
public class RestaurantResource {

  @Inject
  RestaurantMapper restaurantMapper;

  @GET
  public List<RestaurantDTO> getAll() {
    Stream<Restaurant> restaurants = Restaurant.streamAll();
    return restaurants.map(restaurant -> restaurantMapper.toRestaurantDTO(restaurant))
      .collect(Collectors.toList());
  }

  @POST
  @Transactional
  @APIResponse(responseCode = "201", description = "When the restaurant is successfully registered")
  @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
  public Response add(@Valid AddRestaurantDTO addRestaurantDTO) {
    Restaurant restaurant = restaurantMapper.toRestaurant(addRestaurantDTO);
    restaurant.persist();
    return Response.status(Status.CREATED).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public void update(@PathParam("id") Long id, UpdateRestaurantDTO updateRestaurantDTO) {
    Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
    if (restaurantOptional.isEmpty()) {
      throw new NotFoundException();
    }

    Restaurant restaurantUpdated = restaurantOptional.get();
    restaurantMapper.toRestaurant(updateRestaurantDTO, restaurantUpdated);
    restaurantUpdated.persist();
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public void delete(@PathParam("id") Long id) {
    Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
    restaurantOptional.ifPresentOrElse(Restaurant::delete, () -> {
      throw new NotFoundException();
    });
  }
}