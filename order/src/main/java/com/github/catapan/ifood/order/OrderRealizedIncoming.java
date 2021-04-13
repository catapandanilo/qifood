package com.github.catapan.ifood.order;

import com.github.catapan.ifood.order.dish.Dish;
import com.github.catapan.ifood.order.dish.DishOrderDTO;
import com.github.catapan.ifood.order.dish.DishOrderRealizedDTO;
import com.github.catapan.ifood.order.restaurant.Restaurant;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderRealizedIncoming  {

    @Inject
    ElasticSearchService elasticSearchService;

    @Incoming("orders")
    public void readOrders(DishOrderRealizedDTO dto) {
        System.out.println("-----------------");
        System.out.println(dto);

        Order order = new Order();
        order.client = dto.client;
        order.dishes = new ArrayList<>();
        dto.dishes.forEach(dish -> order.dishes.add(from(dish)));
        Restaurant restaurant = new Restaurant();
        restaurant.name = dto.restaurant.name;
        order.restaurant = restaurant;

        String json = JsonbBuilder.create().toJson(dto);
        elasticSearchService.index("orders", json);

        order.persist();
    }

    private Dish from(DishOrderDTO dish) {
        Dish newDish = new Dish();
        newDish.description = dish.description;
        newDish.name = dish.name;
        newDish.price = new Decimal128(dish.price);
        return newDish;
    }

}
