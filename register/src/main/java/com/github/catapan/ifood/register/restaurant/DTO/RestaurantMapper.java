package com.github.catapan.ifood.register.restaurant.DTO;

import com.github.catapan.ifood.register.restaurant.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {

  @Mapping(target = "name", source = "fantasyName")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "dateCreation", ignore = true)
  @Mapping(target = "dateUpdate", ignore = true)
  @Mapping(target = "localization.id", ignore = true)
  public Restaurant toRestaurant(AddRestaurantDTO dto);

  @Mapping(target = "name", source = "fantasyName")
  public void toRestaurant(UpdateRestaurantDTO updateRestaurantDTO,
    @MappingTarget Restaurant restaurant);

  @Mapping(target = "fantasyName", source = "name")
  @Mapping(target = "dateCreation", dateFormat = "dd/MM/yyyy HH:mm:ss")
  public RestaurantDTO toRestaurantDTO(Restaurant restaurant);
}
