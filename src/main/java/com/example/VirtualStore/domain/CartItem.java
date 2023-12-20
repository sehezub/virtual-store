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
  private Long quantity;
  private String description;
  private Float price;
  private String code;
}
