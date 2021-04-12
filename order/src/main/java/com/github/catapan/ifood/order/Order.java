package com.github.catapan.ifood.order;

import com.github.catapan.ifood.order.dish.Dish;
import com.github.catapan.ifood.order.localization.Localization;
import com.github.catapan.ifood.order.restaurant.Restaurant;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import java.util.List;

@MongoEntity(collection = "orders", database = "order")
public class Order extends PanacheMongoEntity {

    public String client;

    public List<Dish> dishes;

    public Restaurant restaurant;

    public String deliveryman;

    public Localization localizationDeliveryman;
}
