package com.github.catapan.ifood.marketplace;

import com.github.catapan.ifood.marketplace.dish.Dish;
import com.github.catapan.ifood.marketplace.dish.dto.DishDTO;
import com.github.catapan.ifood.marketplace.dish.dto.DishOrderDTO;
import com.github.catapan.ifood.marketplace.dish.DishCart;
import com.github.catapan.ifood.marketplace.dish.dto.DishOrderRealizedDTO;
import com.github.catapan.ifood.marketplace.restaurant.dto.RestaurantDTO;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("cart")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartResource {

    private static final String CLIENT = "a";

    @Inject
    io.vertx.mutiny.pgclient.PgPool pgPool;

    @Inject
    @Channel("orders")
    Emitter<DishOrderRealizedDTO> emitterOrder;

    @GET
    public Uni<List<DishCart>> getCart() {
        return DishCart.findCart(pgPool, CLIENT);
    }

    @POST
    @Path("/dish/{idDish}")
    public Uni<Long> addDish(@PathParam("idDish") Long idDish) {
        //poderia retornar o pedido atual
        DishCart pc = new DishCart();
        pc.client = CLIENT;
        pc.dish = idDish;

        return DishCart.save(pgPool, CLIENT, idDish);
    }

    @POST
    @Path("/close-order")
    public Uni<Boolean> closeOrder() {
        DishOrderRealizedDTO order = new DishOrderRealizedDTO();
        String client = CLIENT;
        order.client = client;
        List<DishCart> dishCarts = DishCart.findCart(pgPool, client).await().indefinitely();
        //Utilizar mapstruts
        order.dishes = dishCarts.stream().map(pc -> from(pc)).collect(Collectors.toList());

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.name = "name restaurant";
        order.restaurant = restaurant;

        emitterOrder.send(order);

        return DishCart.delete(pgPool, client);
    }

    private DishOrderDTO from(DishCart dishCart) {
        DishDTO dto = Dish.findById(pgPool, dishCart.dish).await().indefinitely();
        return new DishOrderDTO(dto.name, dto.description, dto.price);
    }

}
