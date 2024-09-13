package com.example.VirtualStore.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  //todo format body response
  @ExceptionHandler(value = ConditionNotMet.class)
  private ResponseEntity<Object> conditionNotMetHandler(ConditionNotMet ex, WebRequest request) {
    return handleExceptionInternal(ex, "The following condition is not met: " + ex.getNotSatisfiedConditions(), new HttpHeaders(), HttpStatus.CONFLICT, request);
  }
  @ExceptionHandler(value = ResourceNotInDB.class)
  private ResponseEntity<Object> resourceNotInDBHandler(ResourceNotInDB ex, WebRequest request) {
    String message = String.format("The request needs a resource of type %1$s and %2$s:%3$s which is not present in the database",
        ex.getResourceType(), ex.getIdentifier(), ex.getValue());
    return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }
  @ExceptionHandler(value = EntityConflict.class)
  private ResponseEntity<Object>  entityConflictHandler(EntityConflict ex, WebRequest request) {
    String message = String.format("The request conflicts with the entity of type %1$s and %2$s:%3$s in the following way: %4$s",
        ex.getEntityType(), ex.getIdentifier(), ex.getValue(), ex.getMessage());
    return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }
}
