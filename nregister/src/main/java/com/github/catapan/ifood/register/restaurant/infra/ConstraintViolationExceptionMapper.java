package com.github.catapan.ifood.register.restaurant.infra;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;


public class ConstraintViolationExceptionMapper implements
  ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response.status(Status.BAD_REQUEST).entity(ConstraintViolationResponse.of(exception))
      .build();
  }
}
