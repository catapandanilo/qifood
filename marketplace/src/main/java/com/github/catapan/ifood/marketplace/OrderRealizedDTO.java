package com.github.catapan.ifood.marketplace;

import com.github.catapan.ifood.marketplace.dish.DishOrderDTO;
import com.github.catapan.ifood.marketplace.restaurant.RestaurantDTO;
import java.util.List;

public class OrderRealizedDTO {

    public List<DishOrderDTO> dishes;

    public RestaurantDTO restaurant;

    public String client;

}
