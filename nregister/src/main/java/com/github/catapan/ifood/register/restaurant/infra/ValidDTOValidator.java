package com.github.catapan.ifood.register.restaurant.infra;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDTOValidator implements ConstraintValidator<ValidDTO, DTO> {

  @Override
  public void initialize(ValidDTO constraintAnnotation) {
  }

  @Override
  public boolean isValid(DTO dto, ConstraintValidatorContext constraintValidatorContext) {
    return dto.isValid(constraintValidatorContext);
  }
}