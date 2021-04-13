package com.github.catapan.ifood.register.restaurant.DTO;

import com.github.catapan.ifood.register.localization.DTO.LocalizationDTO;
import com.github.catapan.ifood.register.restaurant.Restaurant;
import com.github.catapan.ifood.register.restaurant.infra.DTO;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddRestaurantDTO implements DTO {

  @Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
  @NotNull
  public String cnpj;

  @Size(min = 3, max = 30)
  public String fantasyName;

  public LocalizationDTO localization;

  @Override
  public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
    constraintValidatorContext.disableDefaultConstraintViolation();
    if (Restaurant.find("cnpj", cnpj).count() > 0) {
      constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ already registered!")
        .addPropertyNode("cnpj")
        .addConstraintViolation();
      return false;
    }
    return true;
  }

}
