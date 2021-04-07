package com.github.catapan.ifood.register.restaurant.infra;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ConstraintViolationImpl implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "Attribute's path, ex: dateBegin, people.address.number", required = false)
  private final String attribute;

  @Schema(description = "Description of the error possibly associated with the path", required = true)
  private final String message;

  private ConstraintViolationImpl(ConstraintViolation<?> violation) {
    this.message = violation.getMessage();
    this.attribute = Stream.of(violation.getPropertyPath().toString().split("\\.")).skip(2).collect(
      Collectors.joining("."));
  }

  public ConstraintViolationImpl(String attribute, String message) {
    this.attribute = attribute;
    this.message = message;
  }

  public static ConstraintViolationImpl of(ConstraintViolation<?> violation) {
    return new ConstraintViolationImpl(violation);
  }

  public static ConstraintViolationImpl of(String violation) {
    return new ConstraintViolationImpl(null, violation);
  }

  public String getMessage() {
    return message;
  }

  public String getTribute() {
    return attribute;
  }

}
