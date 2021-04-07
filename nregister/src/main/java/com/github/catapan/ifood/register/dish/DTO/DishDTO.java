package com.github.catapan.ifood.register.dish.DTO;

import com.github.catapan.ifood.register.restaurant.DTO.RestaurantDTO;
import java.math.BigDecimal;

public class DishDTO {

  public Long id;

  public String name;

  public String description;

  public RestaurantDTO restaurant;

  public BigDecimal price;
}
