package com.example.VirtualStore.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Cart_Item")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long quantity; //never 0
  private String description;
  private Float price; // unit price
  private String code;

  public Float getTotal() {
    return quantity * price;
  }
}
