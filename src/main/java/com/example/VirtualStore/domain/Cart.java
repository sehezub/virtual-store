package com.example.VirtualStore.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", referencedColumnName = "id")
  private List<CartItem> cartItemList = new ArrayList<>();   //TODO only one cartItem per product

  public Float getTotal() {
    return getCartItemList().stream()
        .map(item -> item.getPrice()*item.getQuantity())
        .reduce(0F, (a, b) -> a+b);
  }
  public Long quantityOf(String productCode) {
    return getCartItemList().stream().filter(item -> item.getCode().equals(productCode))
        .mapToLong(item -> item.getQuantity()).sum();
  }
  public boolean contains(String productCode) {
    return getCartItemList().stream().filter(item -> item.getCode().equals(productCode))
        .findAny().orElse(null) != null;
  }
}
