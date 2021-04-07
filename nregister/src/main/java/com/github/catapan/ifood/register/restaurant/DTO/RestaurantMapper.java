package com.github.catapan.ifood.register.restaurant.DTO;

import com.github.catapan.ifood.register.restaurant.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {

  @Mapping(target = "name", source = "fantasyName")
  public Restaurant toRestaurant(AddRestaurantDTO dto);
}
