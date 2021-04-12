package com.github.catapan.ifood.marketplace.dish;

import com.github.catapan.ifood.marketplace.restaurant.Restaurant;
import java.math.BigDecimal;

public class Dish {

    public Long id;

    public String name;

    public String description;

    public Restaurant restaurant;

    public BigDecimal price;
}
