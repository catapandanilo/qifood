package com.github.catapan.ifood.register.restaurant.infra;

import javax.validation.ConstraintValidatorContext;

public interface DTO {

  default boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
    return true;
  }
}
