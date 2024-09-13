package com.example.VirtualStore.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ConditionNotMet extends RuntimeException{
  @Setter @Getter private ArrayList<String> notSatisfiedConditions;
  public ConditionNotMet(String message, ArrayList<String> conditions) {
    super(message);
    this.notSatisfiedConditions = conditions;}
}
