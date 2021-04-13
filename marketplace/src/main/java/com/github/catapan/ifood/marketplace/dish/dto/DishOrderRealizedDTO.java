package com.github.catapan.ifood.marketplace.dish.dto;

import com.github.catapan.ifood.marketplace.dish.dto.DishOrderDTO;
import com.github.catapan.ifood.marketplace.restaurant.dto.RestaurantDTO;
import java.util.List;

public class DishOrderRealizedDTO {

    public List<DishOrderDTO> dishes;

    public RestaurantDTO restaurant;

    public String client;

}
