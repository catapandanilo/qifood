package com.github.catapan.ifood.register.dish.DTO;

import com.github.catapan.ifood.register.dish.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface DishMapper {

  DishDTO toDTO(Dish dish);

  Dish toDish(AddDishDTO addDishDTO);

  void toDish(UpdateDishDTO updateDishDTO, @MappingTarget Dish dish);
}