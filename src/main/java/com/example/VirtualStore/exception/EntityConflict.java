package com.example.VirtualStore.exception;

import lombok.Getter;
import lombok.Setter;

public class EntityConflict extends RuntimeException{
  @Setter @Getter private String entityType;
  @Setter @Getter private String identifier;
  @Setter @Getter private String value;
  public EntityConflict(String message, String entityType, String identifier, String value) {
    super(message);
    this.entityType = entityType;
    this.identifier = identifier;
    this.value = value;
  }
}
