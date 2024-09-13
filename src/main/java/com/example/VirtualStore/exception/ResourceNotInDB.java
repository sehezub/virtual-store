package com.example.VirtualStore.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ResourceNotInDB extends RuntimeException{
  @Setter @Getter private String identifier;
  @Setter @Getter private String value;
  @Setter @Getter private String resourceType;
  public ResourceNotInDB(String message, String identifier, String value, String resourceType
  ) {
    super(message);
    this.identifier = identifier;
    this.value = value;
    this.resourceType = resourceType;
  }
}
