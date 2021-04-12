package com.github.catapan.ifood.marketplace.restaurant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.vertx.mutiny.pgclient.PgPool;

@ApplicationScoped
public class RestaurantRegistered {

  @Inject
  PgPool pgPool;

  @Incoming("restaurants")
  public void getRestaurant(String json) {
    Jsonb create = JsonbBuilder.create();
    Restaurant restaurant = create.fromJson(json, Restaurant.class);

    System.out.println("--------------------------------------------------------------------------");
    System.out.println(json);

    restaurant.persist(pgPool);
  }
}